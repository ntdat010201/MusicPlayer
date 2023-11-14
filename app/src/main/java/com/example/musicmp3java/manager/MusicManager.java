package com.example.musicmp3java.manager;

import com.example.musicmp3java.fragment.home.model.SongModel;

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



//    public void previous() {
//        int pos = position - 1;
//        if (pos < 0) {
//            pos = songModels.size() - 1;
//            setPosition(pos);
//        } else {
//            setPosition(pos);
//        }
//        try {
//            mediaPlayer.setDataSource(songModels.get(position).getPath());
//            mediaPlayer.prepare();
//        } catch (Exception e) {
//        }
//    }

//    public void next() {
//        int pos = position + 1;
//        if (pos > songModels.size()-1) {
//            setPosition(0);
//        } else {
//            setPosition(pos);
//        }
//        try {
//            mediaPlayer.setDataSource(songModels.get(position).getPath());
//            mediaPlayer.prepare();
//        } catch (Exception e) {
//        }
//    }


}
