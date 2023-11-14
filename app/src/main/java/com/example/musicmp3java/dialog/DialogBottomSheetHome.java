package com.example.musicmp3java.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;

import com.example.musicmp3java.MainActivity;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.BottomSheetBinding;
import com.example.musicmp3java.fragment.favorites.FavoritesFragment;
import com.example.musicmp3java.fragment.home.adapter.RcvHomeAdapter;
import com.example.musicmp3java.inter.InTerDialogH;
import com.example.musicmp3java.manager.MusicManager;

public class DialogBottomSheetHome {
    @SuppressLint("StaticFieldLeak")
    private BottomSheetBinding binding;
    private Dialog dialog;
    private MusicManager musicManager;

    public void showDialogBTHome(MainActivity activity, int position, RcvHomeAdapter rcvHomeAdapter) {
        musicManager = MusicManager.getInstance();
        binding = BottomSheetBinding.inflate(LayoutInflater.from(activity));

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());

        binding.addListPlayer.setOnClickListener(view -> {

            dialog.cancel();
        });
        binding.favorite.setOnClickListener(view -> {
            activity.getFavoritesFragment().addSongModel(musicManager.getSongModels().get(position));
            dialog.cancel();

        });
        binding.delete.setOnClickListener(view -> {
            musicManager.getSongModels().remove(position);
            rcvHomeAdapter.notifyItemRemoved(position);
            dialog.cancel();
        });
        binding.closeDialog.setOnClickListener(view -> {
            dialog.cancel();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
