package com.example.musicmp3java.service;

import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musicmp3java.R;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.MyNotification;

public class MusicService extends Service {
    public static final int NOTIFICATION_ID = 1;
    private MusicManager musicManager;

    private MyNotification myNotification;

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
        MediaSessionCompat sessionCompat = new MediaSessionCompat(this, "tag");
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon)
                .setSubText("music studio")
                .setContentTitle(musicManager.getSongModels().get(musicManager.getPosition()).getTitle())
                .setLargeIcon(musicManager.getSongModels().get(musicManager.getPosition()).getImageSong())
                //icon play
                .addAction(R.drawable.ic_skip_previous, "Previous",null) //0
                .addAction(R.drawable.ic_pause_circle, "Pause", null)     //1
                .addAction(R.drawable.ic_skip_next, "Next", null)         //2
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(sessionCompat.getSessionToken()))
                .build();
        startForeground(NOTIFICATION_ID,notification);
        //startForeground(NOTIFICATION_ID, myNotification.NotificationService(musicManager.getSongModels().get(musicManager.getPosition()), this));
        return START_NOT_STICKY;
    }



}
