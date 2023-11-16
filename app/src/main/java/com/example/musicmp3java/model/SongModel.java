package com.example.musicmp3java.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class SongModel {
    @PrimaryKey
    public int id;
    public String title;
    @Ignore
    public Bitmap imageSong;
    public int size;
    public int duration;

    public String path;

    public boolean favorite = false;

    public SongModel(int id ,String title , int size, int duration, String path) {
        this.id =id;
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.path = path;
    }

    public SongModel(int id, String title, Bitmap imageSong, int size, int duration, String path) {
        this.id =id;
        this.title = title;
        this.imageSong = imageSong;
        this.size = size;
        this.duration = duration;
        this.path = path;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImageSong() {
        return imageSong;
    }

    public void setImageSong(Bitmap imageSong) {
        this.imageSong = imageSong;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        SongModel songModel = (SongModel) obj;
        if(this.title != songModel.title) {
            return false;
        }
        if (this.size != songModel.size){
            return false;
        }
        if (this.duration != songModel.duration){
            return false;
        }
        if (this.path != songModel.path){
            return false;
        }
        if (this.imageSong != songModel.imageSong){
            return false;
        }

        return true;
    }
}
