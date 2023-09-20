package com.example.musicmp3java.manager;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.fragment.play.PlayerActivity;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MusicManager {

    private static MusicManager instance;
    private ArrayList<SongModel> songModels;
    private int position;
    private static MediaPlayer mediaPlayer;
    private FileInputStream fileInputStream;

    private MusicManager() {
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public static MediaPlayer getInstanceMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public ArrayList<SongModel> getSongModels() {
        return songModels;
    }
    public void setSongModels(ArrayList<SongModel> songModels) {
        this.songModels = songModels;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicManager.mediaPlayer = mediaPlayer;
    }

    public void play() {
        try {
            fileInputStream = new FileInputStream(songModels.get(position).getPath());
            mediaPlayer.setDataSource(fileInputStream.getFD());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void previous() {
        int pos = position - 1;
        if (pos < 0) {
            pos = songModels.size() - 1;
            setPosition(pos);
        } else {
            setPosition(pos);
        }
        try {
            fileInputStream = new FileInputStream(songModels.get(position).getPath());
            mediaPlayer.setDataSource(fileInputStream.getFD());
            mediaPlayer.prepare();
        } catch (Exception e) {
        }
    }

    public void next() {
        int pos = position + 1;
        if (pos > songModels.size()-1) {
            setPosition(0);
        } else {
            setPosition(pos);
        }
        try {
            fileInputStream = new FileInputStream(songModels.get(position).getPath());
            mediaPlayer.setDataSource(fileInputStream.getFD());
            mediaPlayer.prepare();
        } catch (Exception e) {
        }
    }


}
