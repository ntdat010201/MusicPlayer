package com.example.musicmp3java.fragment.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.musicmp3java.fragment.play.PlayerActivity;
import com.example.musicmp3java.databinding.FragmentHomeBinding;
import com.example.musicmp3java.fragment.home.adapter.RcvHomeAdapter;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.service.MusicService;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements RcvHomeAdapter.IOnClickList {
    private FragmentHomeBinding binding;
    ActivityResultLauncher<String> storagePermissionLauncher;
    final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private RcvHomeAdapter rcvHomeAdapter;
    private MusicManager musicManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        initData();
        initView();
        initListener();
        return binding.getRoot();
    }

    private void initData() {
    }
    private void initView() {
        checkPermission();
        searchText();
    }

    private void initListener() {
        //clickControlShowView();
    }

    private void fetchSongs() {

        //define a list to cary  songs
        ArrayList<SongModel> songModels = new ArrayList<>();
        Uri mediaStoreUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mediaStoreUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            mediaStoreUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        //define projection
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DATA };

        // order
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC ";

        //get the songs
        try (Cursor cursor = requireContext().getContentResolver().query(mediaStoreUri, projection, null, null, sortOrder)) {
            //cache cursor indices
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int pathIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);


            //clear the previous loaded before adding loading again
            while (cursor.moveToNext()) {
                //get the value of a  column for a given audio file
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String path = cursor.getString(pathIdColumn);


                //song uri
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                //remove . mp3 extension from the song's name
                name = name.substring(0, name.lastIndexOf("."));
                // image song
                Bitmap imageSong = getImageSong(path);

                //song
                SongModel songModel = new SongModel(name, uri, imageSong, size, duration, path);
                //add song item to song list
                songModels.add(songModel);
            }
            //display songs
            musicManager = MusicManager.getInstance();
            musicManager.setSongModels(songModels);

            rcvHomeAdapter = new RcvHomeAdapter(musicManager.getSongModels(), requireContext());
            rcvHomeAdapter.setIOnClickList(this);
            showSongs(musicManager.getSongModels());
        }
    }

    private void showSongs(ArrayList<SongModel> songModels) {

        if (songModels.size() == 0) {
            Toast.makeText(requireContext(), "No song", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.edtSearchHome.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rcvHomeAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.RecyclerViewHome.setAdapter(rcvHomeAdapter);
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

    private void userResponses() {

        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
            fetchSongs();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(permission)) {
                //show an UI to user explaining why we need this permission
                //use alert dialog

                new AlertDialog.Builder(requireContext()).setTitle("Requesting permission").setMessage("Allow us to fetch songs on your device ").setPositiveButton("Allow", (dialogInterface, i) -> {
                    //request permission
                    storagePermissionLauncher.launch(permission);
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                    Toast.makeText(requireContext(), "you denied us to show songs", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }).show();
            }
        } else {
            Toast.makeText(requireContext(), "you denied us to show songs", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission() {
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
            if (granted) {
                //fetch songs
                fetchSongs();

            } else {
                userResponses();
            }
        });
        storagePermissionLauncher.launch(permission);
    }

    private void searchText() {
        binding.icSearchHome.setOnClickListener(view -> {
            binding.icSearchHome.setVisibility(View.INVISIBLE);
            binding.icCloseHome.setVisibility(View.VISIBLE);
            binding.edtSearchHome.setVisibility(View.VISIBLE);

            binding.edtSearchHome.requestFocus();
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        });

        binding.icCloseHome.setOnClickListener(view -> {
            binding.icSearchHome.setVisibility(View.VISIBLE);
            binding.icCloseHome.setVisibility(View.INVISIBLE);
            binding.edtSearchHome.setVisibility(View.INVISIBLE);

            binding.edtSearchHome.clearFocus();
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edtSearchHome.getWindowToken(), 0);
            binding.edtSearchHome.setText(null);
        });


    }

    @Override
    public void onClick(int position) {
        musicManager.setPosition(position);
        showPlayerView();
        homeSongNameView();
    }


    private void showPlayerView() {
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);

    }

    private void homeSongNameView(){
        if (musicManager.getPosition() == 0){
            binding.homeSongNameView.setSelected(true);
            binding.homeSongNameView.setText(musicManager.getSongModels().get(musicManager.getPosition()).getTitle());
        }

    }
    private void clickControlShowView(){
        binding.homeControlWrapper.setOnClickListener(view -> {
            if (binding.layoutHome.activityPlayer.getVisibility() != View.VISIBLE){
                binding.layoutHome.activityPlayer.setVisibility(View.VISIBLE);
            }
        });
    }
}