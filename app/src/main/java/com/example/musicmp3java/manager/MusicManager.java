package com.example.musicmp3java.manager;

import com.example.musicmp3java.model.SongModel;

import java.util.ArrayList;

public class MusicManager {

    private static MusicManager instance;
    private ArrayList<SongModel> songModels;
    private int position;

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
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


}
