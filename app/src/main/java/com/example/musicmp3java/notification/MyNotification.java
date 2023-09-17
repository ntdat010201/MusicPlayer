package com.example.musicmp3java.notification;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.musicmp3java.Const;
import com.example.musicmp3java.R;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.service.MusicService;

public class MyNotification {

    private MusicService musicService;
    public static final int NOTIFICATION_ID = 1;
    private Notification notification;

    public Notification notification() {
        Log.d("DAT", "notification: " + notification);
        return notification;

    }

    @SuppressLint("MissingPermission")
    public Notification NotificationService(SongModel songModel, Context context) {

        PendingIntent intentPrev = PendingIntent.getBroadcast(context, 1, new Intent(Const.ACTION_PREV), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent intentNext = PendingIntent.getBroadcast(context, 2, new Intent(Const.ACTION_NEXT), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent intentPause = PendingIntent.getBroadcast(context, 3, new Intent(Const.ACTION_PAUSE), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        MediaSessionCompat sessionCompat = new MediaSessionCompat(context, "tag");
        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon)
                .setSubText("music studio")
                .setContentTitle(songModel.getTitle())
                .setLargeIcon(songModel.getImageSong())
                //icon play
                .addAction(R.drawable.ic_skip_previous, "Previous", intentPrev)  //0
                .addAction(R.drawable.ic_pause_circle, "Pause", intentPause)     //1
                .addAction(R.drawable.ic_skip_next, "Next", intentNext)         //2
                .addAction(R.drawable.ic_close, "close", null)              //3
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(sessionCompat.getSessionToken()))
                .build();
        manager.notify(NOTIFICATION_ID, notification);
        return notification;

    }

}
