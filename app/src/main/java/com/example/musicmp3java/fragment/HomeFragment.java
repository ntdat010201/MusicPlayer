package com.example.musicmp3java.fragment;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musicmp3java.MainActivity;
import com.example.musicmp3java.database.SongModelDatabase;
import com.example.musicmp3java.databinding.FragmentHomeBinding;
import com.example.musicmp3java.dialog.DialogBottomSheetHome;
import com.example.musicmp3java.adapter.RcvHomeAdapter;
import com.example.musicmp3java.model.SongModel;
import com.example.musicmp3java.play.PlayerActivity;
import com.example.musicmp3java.manager.MusicManager;
import com.example.musicmp3java.service.MusicService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RcvHomeAdapter.IOnClickListH {
    private FragmentHomeBinding binding;
    private RcvHomeAdapter rcvHomeAdapter;
    private MusicManager musicManager;
    private DialogBottomSheetHome dialogBottomSheetHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        initData();
        initView();
        initListener();
        return binding.getRoot();
    }

    private void initData() {
        dialogBottomSheetHome = new DialogBottomSheetHome();
    }

    private void initView() {
        fetchSongs();
        searchText();
    }

    private void initListener() {
        clickControlShowView();
    }

    private void fetchSongs() {

        ArrayList<SongModel> songModels = new ArrayList<>();
        Uri mediaStoreUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mediaStoreUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            mediaStoreUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DATA};

        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC ";

        try (Cursor cursor = requireContext().getContentResolver().query(mediaStoreUri, projection, null, null, sortOrder)) {
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int pathIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

            int i = 0;
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String path = cursor.getString(pathIdColumn);

                name = name.substring(0, name.lastIndexOf("."));
                Bitmap imageSong = getImageSong(path);

                SongModel songModel = new SongModel(i, name , imageSong, size, duration, path);
                songModels.add(songModel);
                i++;
            }

            musicManager = MusicManager.getInstance();
            musicManager.setSongModels(songModels);
            rcvHomeAdapter = new RcvHomeAdapter(musicManager.getSongModels(), requireContext());



            List<SongModel> allList = SongModelDatabase.getInstance(requireContext()).SongModelDAO().getArrSongModel();

            for(int j = 0; j < songModels.size(); j++) {
                boolean exist = false;
                for(SongModel songModel : allList) {
                    if(songModel.equals(songModels.get(j))) {
                        exist = true;
                        break;
                    }
                }
                if(!exist) {
                    SongModelDatabase.getInstance(requireContext()).SongModelDAO().insertSongModel(songModels.get(j));
                }
            }

            rcvHomeAdapter.setiOnClickListH(this);
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
        startService();
        showPlayerView();
        homeSongNameView();
    }

    @Override
    public void showDialogHome(int position) {
        dialogBottomSheetHome.showDialogBTHome((MainActivity) requireActivity(), position, rcvHomeAdapter);
    }

    private void showPlayerView() {
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }

    private void homeSongNameView() {
        binding.homeSongNameView.setSelected(true);
        binding.homeSongNameView.setText(musicManager.getSongModels().get(musicManager.getPosition()).getTitle());
    }

    private void clickControlShowView() {
        binding.homeControlWrapper.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PlayerActivity.class);
            startActivity(intent);
        });
    }

    private void startService() {
        Intent intent = new Intent(requireContext(), MusicService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(intent);
        } else {
            requireContext().startService(intent);
        }
    }

}