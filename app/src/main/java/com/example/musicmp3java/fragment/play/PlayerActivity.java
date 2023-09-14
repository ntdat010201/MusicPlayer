package com.example.musicmp3java.fragment.play;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ActivityPlayerBinding;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.service.MusicService;
import com.example.musicmp3java.utils.FileUtils;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding binding;
    private boolean isServiceConnection;
    private MusicManager musicManager;
    private MediaPlayer mediaPlayer;


    public PlayerActivity() {
        musicManager = MusicManager.getInstance();
        mediaPlayer = MusicManager.getInstanceMedia();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initData();
        initView();
        initListener();
    }

    private void initData() {
    }

    private void initView() {
        startService();
        showLayoutPlay();
        songNameView();
        setArtworkView();
    }

    private void initListener() {
        playMusic();
        isPlayOrPause();
        isNext();
        isPrevious();
        clickPlayListBtn();
    }

    private void showLayoutPlay() {
        binding.activityPlayer.setVisibility(View.VISIBLE);
    }

    private void setSeekBar() {
        binding.progressView.setText(FileUtils.getReadableTime(mediaPlayer.getCurrentPosition()));
        binding.durationView.setText(FileUtils.getReadableTime(mediaPlayer.getDuration()));
        binding.seekbar.setProgress(mediaPlayer.getCurrentPosition());
        binding.seekbar.setMax(mediaPlayer.getDuration());
        updatePlayerPositionProgress();
    }

    private void seekBarListener() {
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //            int progressValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                progressValue = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(binding.seekbar.getProgress());

//                seekBar.setProgress(progressValue);
//                binding.progressView.setText(FileUtils.getReadableTime(progressValue));
//                mediaPlayer.seekTo(progressValue);
            }
        });
    }

    private void updatePlayerPositionProgress() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mediaPlayer.isPlaying()) {
                binding.progressView.setText(FileUtils.getReadableTime(mediaPlayer.getCurrentPosition()));
                binding.seekbar.setProgress(mediaPlayer.getCurrentPosition());
            }
            updatePlayerPositionProgress();
        }, 100);
    }

    private void clickPlayListBtn() {
        binding.playlistBtn.setOnClickListener(view -> {
            if (binding.activityPlayer.getVisibility() == View.VISIBLE) {
                binding.activityPlayer.setVisibility(View.GONE);
            }
            onBackPressed();
        });
    }

    private void songNameView() {
        binding.songNameView.setSelected(true);
        binding.songNameView.setText(musicManager.getSongModels().get(musicManager.getPosition()).getTitle());
    }

    private void setArtworkView() {
        Glide.with(this).load(musicManager.getSongModels().get(musicManager.getPosition()).getImageSong()).placeholder(R.drawable.bg).into(binding.artworkView);
        binding.artworkView.setAnimation(loadRotation());
    }

    private void playMusic() {
        musicManager.play();
        setSeekBar();
        seekBarListener();
        binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
        binding.artworkView.startAnimation(loadRotation());
    }

    private void isPlayOrPause() {
        binding.playPauseBtn.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
                binding.artworkView.startAnimation(loadRotation());
            } else {
                musicManager.pause();
                binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_circle, 0, 0, 0);
                binding.artworkView.clearAnimation();
            }
        });

    }

    private void isNext() {
        binding.skipNextBtn.setOnClickListener(view -> {
            musicManager.next();
            binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
            binding.artworkView.startAnimation(loadRotation());
        });
    }

    private void isPrevious() {
        binding.skipPreviousBtn.setOnClickListener(view -> {
            musicManager.previous();
            binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
            binding.artworkView.startAnimation(loadRotation());
        });
    }

    private Animation loadRotation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(10000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

    private void startService() {
        Intent intent = new Intent(this, MusicService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent);
        } else {
            this.startService(intent);
        }
    }


}