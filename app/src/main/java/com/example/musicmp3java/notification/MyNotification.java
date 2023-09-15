package com.example.musicmp3java.notification;

import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;

import com.example.musicmp3java.R;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.service.MusicService;

public class MyNotification {

    private MusicService musicService;
    public static final int NOTIFICATION_ID = 1;
    private Notification notification;

    @SuppressLint("MissingPermission")
    public Notification NotificationService(SongModel songModel, Context context) {
        MediaSessionCompat sessionCompat = new MediaSessionCompat(context, "tag");

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon)
                .setSubText("music studio")
                .setContentTitle(songModel.getTitle())
                .setLargeIcon(songModel.getImageSong())
                //icon play
                .addAction(R.drawable.ic_skip_previous, "Previous", null)  //0
                .addAction(R.drawable.ic_pause_circle, "Pause", null)     //1
                .addAction(R.drawable.ic_skip_next, "Next", null)         //2
                .addAction(R.drawable.ic_close, "close", null)              //3
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(sessionCompat.getSessionToken()))
                .build();
        return notification;
    }

}
