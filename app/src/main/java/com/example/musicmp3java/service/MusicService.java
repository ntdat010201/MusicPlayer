package com.example.musicmp3java.service;

import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;
import static com.example.musicmp3java.notification.MyNotification.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musicmp3java.Const;
import com.example.musicmp3java.R;
import com.example.musicmp3java.broadcastReceiver.MyBroadcastReceiver;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.MyNotification;

public class MusicService extends Service {
    private MusicManager musicManager;

    private MyNotification myNotification;

    private MyBroadcastReceiver broadcastReceiver;

    public MusicService() {
        Log.d("DAT", "MusicService: ");
        musicManager = MusicManager.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myNotification = new MyNotification();
        broadcastReceiver = new MyBroadcastReceiver(this);

        registerReceiver();
        startForeground(NOTIFICATION_ID, myNotification.NotificationService(musicManager.getSongModels().get(musicManager.getPosition()), this));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
