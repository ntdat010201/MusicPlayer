package com.example.musicmp3java.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case "PREVIOUS" :
                break;
                case "PAUSE" :
                break;
                case "PLAY" :
                break;
                case "NEXT" :
                break;


        }

    }
}
