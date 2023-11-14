package com.example.musicmp3java.fragment.favorites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musicmp3java.database.SongModelDatabase;
import com.example.musicmp3java.databinding.FragmentFavoritesBinding;
import com.example.musicmp3java.dialog.DialogBottomSheetHome;
import com.example.musicmp3java.fragment.favorites.adapter.RcvFavoritesAdapter;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.manager.MusicManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private MusicManager musicManager;
    private int position;

    private RcvFavoritesAdapter rcvFavoritesAdapter;
    private ArrayList<SongModel> mSongModel;

    private DialogBottomSheetHome dialogBottomSheetHome;

    public FavoritesFragment() {
        musicManager = MusicManager.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        initData();
        initView();
        initListener();
        return binding.getRoot();
    }

    private void initData() {
        mSongModel = new ArrayList<>();
    }
    @Override
    public void onResume() {
        super.onResume();
        while (mSongModel == null) {
            mSongModel = (ArrayList<SongModel>) SongModelDatabase.getInstance(requireContext())
                    .SongModelDAO().getArrSongModelFavorite(true);
        }
        Log.d("DAT", "onResume: "+  ((ArrayList<SongModel>) SongModelDatabase.getInstance(requireContext()).SongModelDAO().getArrSongModelFavorite(true)).size());
        Log.d("DAT", "mSongModel: " + mSongModel.size());
        rcvFavoritesAdapter = new RcvFavoritesAdapter((ArrayList<SongModel>) SongModelDatabase.getInstance(requireContext())
                .SongModelDAO().getArrSongModelFavorite(true), requireContext());
        binding.RecyclerViewFavorites.setAdapter(rcvFavoritesAdapter);
    }

    private void initView() {
    }

    private void initListener() {

    }

    public void addSongModel(SongModel songModel) {
        songModel.favorite = !songModel.favorite;
        Log.d("DAT", "addSongModel: " + songModel.id);
        SongModelDatabase.getInstance(requireContext()).SongModelDAO().insertSongModel(songModel);
        Log.d("DAT", "addSongModel: " + songModel.favorite);
    }
    private Bitmap getImageSong(String path) {
        Bitmap art = null;
        byte[] rawArt = null;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            File file = new File(path);
            if (file.length() > 0 && file.exists()) {
                mmr.setDataSource(requireContext(), Uri.parse(path));
                try {
                    rawArt = mmr.getEmbeddedPicture();
                } catch (Exception e) {
                }
            }
            if (rawArt != null) {
                try {
                    art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, new BitmapFactory.Options());
                } catch (OutOfMemoryError e) {
                    art = null;
                }
            }
        } catch (Exception e) {
        }
        return art;
    }


}