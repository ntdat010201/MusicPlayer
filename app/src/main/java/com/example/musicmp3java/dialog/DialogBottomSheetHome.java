package com.example.musicmp3java.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.BottomSheetBinding;

public class DialogBottomSheetHome {
    private static BottomSheetBinding binding;
    public static void showDialogBTHome(Context context) {
        binding = BottomSheetBinding.inflate(LayoutInflater.from(context));

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());

        binding.addListPlayer.setOnClickListener(view -> {
            Toast.makeText(context, "okkkkkk111", Toast.LENGTH_SHORT).show();
        });
        binding.favorite.setOnClickListener(view -> {
            Toast.makeText(context, "okkkkkk222", Toast.LENGTH_SHORT).show();

        });
        binding.delete.setOnClickListener(view -> {
            Toast.makeText(context, "okkkkkk333", Toast.LENGTH_SHORT).show();

        });
        binding.closeDialog.setOnClickListener(view -> {
            Toast.makeText(context, "okkkkkk444", Toast.LENGTH_SHORT).show();

        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }
}
