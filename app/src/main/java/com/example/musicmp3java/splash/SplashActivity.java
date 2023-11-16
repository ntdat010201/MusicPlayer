package com.example.musicmp3java.splash;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.musicmp3java.MainActivity;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    ActivityResultLauncher<String> storagePermissionLauncher;
    final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initData();
        initView();
        initListener();
    }
    private void initData() {
        handler = new Handler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        checkPermission();
    }

    private void initListener() {
    }

    private void splashMedia(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },1000);
    }

    private void userResponses() {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            splashMedia();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permission)) {
                new AlertDialog.Builder(this).setTitle("Requesting permission").setMessage("Allow us to fetch songs on your device ").setPositiveButton("Allow", (dialogInterface, i) -> {
                    storagePermissionLauncher.launch(permission);
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                    Toast.makeText(this, "you denied us to show songs", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }).show();
            }
        } else {
            Toast.makeText(this, "you denied us to show songs", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkPermission() {
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (granted) {
                splashMedia();
            } else {
                userResponses();
            }
        });
        storagePermissionLauncher.launch(permission);
    }
}