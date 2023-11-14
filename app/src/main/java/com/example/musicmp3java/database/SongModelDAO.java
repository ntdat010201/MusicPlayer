package com.example.musicmp3java.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.musicmp3java.fragment.home.model.SongModel;

import java.util.List;

@Dao
public interface SongModelDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongModel(SongModel SongModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListSongModel(List<SongModel> SongModels);

    @Query("SELECT * FROM song")
    List<SongModel> getArrSongModel();

    @Query("SELECT * FROM song WHERE favorite = :value")
    List<SongModel> getArrSongModelFavorite(boolean value);
}
