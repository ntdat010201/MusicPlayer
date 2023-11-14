package com.example.musicmp3java.notification;

import static com.example.musicmp3java.notification.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicmp3java.R;
import com.example.musicmp3java.broadcastReceiver.MyBroadcastReceiver;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.utils.Const;

import java.util.ArrayList;

public class CreateNotification {
    public static final int NOTIFICATION_ID = 1;
    public Notification notification;

    @SuppressLint("MissingPermission")
    public Notification createNotification(ArrayList<SongModel> songModels, int pos, Context context, int playBottom) {

        // Previous
        PendingIntent pendingIntentPrevious;
        Intent intentPrevious = new Intent(context, MyBroadcastReceiver.class)
                .setAction(Const.ACTION_PREV);
        pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
        int drw_previous = R.drawable.ic_skip_previous;


        // PlayOrPause
        Intent intentPlayOrPause = new Intent(context, MyBroadcastReceiver.class)
                .setAction(Const.ACTION_PLAY_OR_PAUSE);
        PendingIntent pendingIntentPlayOrPause = PendingIntent.getBroadcast(context, 0,
                intentPlayOrPause, PendingIntent.FLAG_UPDATE_CURRENT);

        // Next
        PendingIntent pendingIntentNext;

            Intent intentNext = new Intent(context, MyBroadcastReceiver.class)
                    .setAction(Const.ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            int drw_next = R.drawable.ic_skip_next;


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon)
                .setSubText("music studio")
                .setContentTitle(songModels.get(pos).getTitle())
                .setLargeIcon(songModels.get(pos).getImageSong())
                //icon play
                .addAction(drw_previous, "Previous", pendingIntentPrevious)        //0
                .addAction(playBottom, "PlayOrPause", pendingIntentPlayOrPause)               //1
                .addAction(drw_next, "Next", pendingIntentNext)                //2
//                .addAction(R.drawable.ic_close, "close", intentClose)                //3

                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .build();
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        return notification;
    }
}

