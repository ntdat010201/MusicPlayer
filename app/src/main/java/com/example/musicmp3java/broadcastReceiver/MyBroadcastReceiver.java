package com.example.musicmp3java.broadcastReceiver;

import static com.example.musicmp3java.Const.ACTION_CLOSE;
import static com.example.musicmp3java.Const.ACTION_NEXT;
import static com.example.musicmp3java.Const.ACTION_PAUSE;
import static com.example.musicmp3java.Const.ACTION_PLAY;
import static com.example.musicmp3java.Const.ACTION_PREV;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.musicmp3java.service.MusicService;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private MusicService service;

    public MyBroadcastReceiver(MusicService musicService) {
        this.service = musicService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DAT", "onReceive: ");
        switch (intent.getAction()) {
            case ACTION_PREV:
                service.previous();
                break;
            case ACTION_NEXT:
                service.next();
                break;
            case ACTION_PAUSE:
                service.pause();
                break;
            case ACTION_PLAY:
                service.play();
                break;
            case ACTION_CLOSE:
                service.close();
                break;
        }


    }
}
