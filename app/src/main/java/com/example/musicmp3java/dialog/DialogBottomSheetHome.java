package com.example.musicmp3java.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.musicmp3java.MainActivity;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.BottomSheetBinding;
import com.example.musicmp3java.adapter.RcvHomeAdapter;
import com.example.musicmp3java.manager.MusicManager;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class DialogBottomSheetHome {
    @SuppressLint("StaticFieldLeak")
    private BottomSheetBinding binding;
    private Dialog dialog;
    private MusicManager musicManager;

    public void showDialogBTHome(MainActivity activity, int position,RcvHomeAdapter adapter) {
        musicManager = MusicManager.getInstance();
        binding = BottomSheetBinding.inflate(LayoutInflater.from(activity));

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());

        binding.addListPlayer.setOnClickListener(view -> {
            shareFile(activity,position);
            dialog.cancel();
        });
        binding.favorite.setOnClickListener(view -> {
            activity.getFavoritesFragment().addSongModel(musicManager.getSongModels().get(position));
            dialog.cancel();

        });
        binding.delete.setOnClickListener(view -> {
            deleteFile(activity,position,adapter,view);
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

    private void shareFile(Context context, int position){
        Uri uri = Uri.parse(musicManager.getSongModels().get(position).getPath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("music/*");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(Intent.createChooser(intent,"share"));
        Toast.makeText(context, "loading...", Toast.LENGTH_SHORT).show();
    }

    private void deleteFile(Context context,int position,RcvHomeAdapter adapter,View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("delete")
                .setMessage(musicManager.getSongModels().get(position).getTitle())
                .setNegativeButton("cancel", (dialogInterface, i) -> {
                    //
                }).setPositiveButton("ok", (dialogInterface, i) -> {
                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            Long.parseLong(String.valueOf(musicManager.getSongModels().get(position).id)));

                    File file = new File(musicManager.getSongModels().get(position).getPath());
                    boolean deleted = file.delete();
                    if (deleted){
                        context.getApplicationContext().getContentResolver().delete(contentUri,null,null);
                        musicManager.getSongModels().remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position,musicManager.getSongModels().size());
                        Snackbar.make(view, "đã xóa",Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(view, "không thể xóa",Snackbar.LENGTH_SHORT).show();
                    }
                }).show();
        dialog.cancel();
    }


}
