package com.example.musicmp3java.play;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ActivityPlayerBinding;
import com.example.musicmp3java.inter.PlayAble;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.notification.CreateNotification;
import com.example.musicmp3java.service.MusicService;
import com.example.musicmp3java.utils.Const;
import com.example.musicmp3java.utils.FileUtils;

public class PlayerActivity extends AppCompatActivity implements PlayAble {
    private ActivityPlayerBinding binding;
    private MusicManager musicManager;
    private MediaPlayer mediaPlayer;
    private MusicService service;
    private CreateNotification notification;

    public PlayerActivity() {
        musicManager = MusicManager.getInstance();
        mediaPlayer = MusicService.getInstanceMedia();
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
        notification = new CreateNotification();
        service = new MusicService();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACK_TRACK"));

    }

    private void initView() {
        upDateView();
        songNameView();
        setArtworkView();
        checkAnimation();

    }

    private void initListener() {
        isPlayOrPause();
        isNext();
        isPrevious();
        clickPlayListBtn();
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
            int progressValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(progressValue);
                binding.progressView.setText(FileUtils.getReadableTime(progressValue));
                mediaPlayer.seekTo(progressValue);
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

    private void checkAnimation() {
        if (mediaPlayer.isPlaying()) {
            binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
            binding.artworkView.startAnimation(loadRotation());
        } else {
            binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_circle, 0, 0, 0);
            binding.artworkView.clearAnimation();
        }
    }

    private Animation loadRotation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(10000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }


    public void upDateView() {
        songNameView();
        setArtworkView();
        setSeekBar();
        seekBarListener();
    }


    private void isNext() {
        binding.skipNextBtn.setOnClickListener(view -> {
            onTrackNext();
        });
    }

    private void isPrevious() {
        binding.skipPreviousBtn.setOnClickListener(view -> {
            onTrackPrevious();
        });
    }


    public void isPlayOrPause() {
        binding.playPauseBtn.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                onTrackPause();
            } else {
                onTrackPlay();
            }
        });
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            switch (action) {
                case Const.ACTION_PREV:
                    onTrackPrevious();
                    break;
                case Const.ACTION_PLAY_OR_PAUSE:
                    if (!mediaPlayer.isPlaying()) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case Const.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackNext() {
        service.onTrackNext();

        notification.createNotification(musicManager.getSongModels(),
                musicManager.getPosition(), this, R.drawable.ic_pause_circle);

        binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
        upDateView();
        binding.artworkView.startAnimation(loadRotation());
    }

    @Override
    public void onTrackPlay() {
        service.onTrackPlay();

        notification.createNotification(musicManager.getSongModels(),
                musicManager.getPosition(), this, R.drawable.ic_pause_circle);

        binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
        upDateView();
        binding.artworkView.startAnimation(loadRotation());
    }

    @Override
    public void onTrackPause() {
        service.onTrackPause();

        notification.createNotification(musicManager.getSongModels(),
                musicManager.getPosition(), this, R.drawable.ic_play_circle);

        binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_circle, 0, 0, 0);
        upDateView();
        binding.artworkView.clearAnimation();
    }

    @Override
    public void onTrackPrevious() {
        service.onTrackPrevious();

        notification.createNotification(musicManager.getSongModels(),
                musicManager.getPosition(), this, R.drawable.ic_pause_circle);

        binding.playPauseBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_circle, 0, 0, 0);
        upDateView();
        binding.artworkView.startAnimation(loadRotation());
    }

}