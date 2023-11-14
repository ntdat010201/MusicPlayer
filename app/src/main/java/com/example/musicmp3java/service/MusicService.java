package com.example.musicmp3java.service;

import static com.example.musicmp3java.notification.CreateNotification.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.musicmp3java.R;
import com.example.musicmp3java.inter.PlayAble;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.CreateNotification;
import com.example.musicmp3java.utils.Const;

public class MusicService extends Service implements PlayAble {
    private final MusicManager musicManager;
    private CreateNotification notification;
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstanceMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicService.mediaPlayer = mediaPlayer;
    }

    public MusicService() {
        musicManager = MusicManager.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        notification = new CreateNotification();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACK_TRACK"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mediaPlayer == null) {
            starMusic();
        } else {
            mediaPlayer.reset();
            starMusic();
        }
        startForeground(NOTIFICATION_ID, notification.createNotification(musicManager.getSongModels(), musicManager.getPosition(), this, R.drawable.ic_pause_circle));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void starMusic() {
        try {
            mediaPlayer.setDataSource(musicManager.getSongModels().get(musicManager.getPosition()).getPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            switch (action) {
                case Const.ACTION_PREV:
                    onTrackPrevious();
                    break;
                case Const.ACTION_PLAY_OR_PAUSE:
                    if (mediaPlayer.isPlaying()) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case Const.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {
        mediaPlayer.reset();
        int pos = musicManager.getPosition() - 1;
        if (pos < 0) {
            pos = musicManager.getSongModels().size() - 1;
            musicManager.setPosition(pos);
        } else {
            musicManager.setPosition(pos);
        }
        try {
            mediaPlayer.setDataSource(musicManager.getSongModels().get(musicManager.getPosition()).getPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (Exception e) {
        }

    }

    @Override
    public void onTrackPlay() {
        mediaPlayer.start();
    }

    @Override
    public void onTrackPause() {
        mediaPlayer.pause();
    }

    @Override
    public void onTrackNext() {
        mediaPlayer.reset();
        int pos = musicManager.getPosition() + 1;
        if (pos > musicManager.getSongModels().size() - 1) {
            musicManager.setPosition(0);
        } else {
            musicManager.setPosition(pos);
        }
        try {
            mediaPlayer.setDataSource(musicManager.getSongModels().get(musicManager.getPosition()).getPath());
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (Exception e) {
        }
    }

}
