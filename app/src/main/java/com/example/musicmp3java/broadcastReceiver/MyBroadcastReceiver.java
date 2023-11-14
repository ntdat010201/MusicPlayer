package com.example.musicmp3java.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.service.MusicService;
import com.example.musicmp3java.utils.Const;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("TRACK_TRACK")
                .putExtra("actionname",intent.getAction()));

    }

}
