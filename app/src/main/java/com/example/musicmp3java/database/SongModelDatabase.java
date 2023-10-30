package com.example.musicmp3java.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.musicmp3java.fragment.home.model.SongModel;


@Database(entities = {SongModel.class},version = 1)
public abstract class SongModelDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "SongModel.db";

    private static SongModelDatabase instance;

    public static synchronized SongModelDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,SongModelDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract SongModelDAO SongModelDAO();

}
