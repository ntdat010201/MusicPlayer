package com.example.musicmp3java.service;

import static com.example.musicmp3java.notification.MyNotification.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.musicmp3java.fragment.play.PlayerActivity;
import com.example.musicmp3java.utils.Const;
import com.example.musicmp3java.broadcastReceiver.MyBroadcastReceiver;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.MyNotification;

public class MusicService extends Service {
    private MusicManager musicManager;
    private MyNotification myNotification;
    private MyBroadcastReceiver broadcastReceiver;
    private PlayerActivity playerActivity;
    private MediaPlayer mediaPlayer;

    public MusicService() {
        musicManager = MusicManager.getInstance();
        mediaPlayer = MusicManager.getInstanceMedia();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playerActivity = new PlayerActivity();
        myNotification = new MyNotification();
        broadcastReceiver = new MyBroadcastReceiver(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver();
        startForeground(NOTIFICATION_ID, myNotification.NotificationService(musicManager.getSongModels().get(musicManager.getPosition()), this));
        return START_NOT_STICKY;
    }

    public void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_NEXT);
        filter.addAction(Const.ACTION_PAUSE);
        filter.addAction(Const.ACTION_PLAY);
        filter.addAction(Const.ACTION_PREV);
        filter.addAction(Const.ACTION_CLOSE);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void next() {
        musicManager.next();
    }

    public void pause() {
        musicManager.pause();

    }
    public void previous() {
        musicManager.previous();
    }

    public void play() {
        musicManager.play();
    }

    public void close() {
        onDestroy();
    }

}
