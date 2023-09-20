package com.example.musicmp3java.fragment.favorites;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.musicmp3java.databinding.FragmentFavoritesBinding;
import com.example.musicmp3java.fragment.favorites.adapter.RcvFavoritesAdapter;
import com.example.musicmp3java.manager.MusicManager;


public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private RcvFavoritesAdapter rcvFavoritesAdapter;
    private MusicManager musicManager;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        initData();
        initView();
        initListener();
        return binding.getRoot();
    }

    private void initData() {
        musicManager = MusicManager.getInstance();
        mediaPlayer = MusicManager.getInstanceMedia();
    }

    private void initView() {

    }

    private void initListener() {

    }
}