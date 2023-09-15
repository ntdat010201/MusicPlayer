package com.example.musicmp3java.service;

import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;
import static com.example.musicmp3java.notification.MyNotification.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musicmp3java.R;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.MyNotification;

public class MusicService extends Service {
    private MusicManager musicManager;

    private MyNotification myNotification = new MyNotification();

    public MusicService() {
        musicManager = MusicManager.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(NOTIFICATION_ID, myNotification.NotificationService(musicManager.getSongModels().get(musicManager.getPosition()), this));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}