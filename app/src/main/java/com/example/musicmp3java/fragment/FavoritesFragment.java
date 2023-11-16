package com.example.musicmp3java.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musicmp3java.database.SongModelDatabase;
import com.example.musicmp3java.databinding.FragmentFavoritesBinding;
import com.example.musicmp3java.adapter.RcvFavoritesAdapter;
import com.example.musicmp3java.model.SongModel;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private RcvFavoritesAdapter rcvFavoritesAdapter;
    private ArrayList<SongModel> mSongModel;

    public FavoritesFragment() {}

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


}