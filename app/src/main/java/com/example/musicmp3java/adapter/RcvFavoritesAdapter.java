package com.example.musicmp3java.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ItemRcvFavoritesBinding;
import com.example.musicmp3java.model.SongModel;
import com.example.musicmp3java.utils.FileUtils;

import java.util.ArrayList;

public class RcvFavoritesAdapter extends RecyclerView.Adapter<RcvFavoritesAdapter.songFavoritesViewHolder> {
    private ArrayList<SongModel> SongModel;
    private final Context context;
    private IOnClickListF iOnClickListF;



    public RcvFavoritesAdapter(ArrayList<SongModel> SongModel, Context context) {
        this.SongModel = SongModel;
        this.context = context;
    }

    public void setiOnClickListF(IOnClickListF iOnClickListF) {
        this.iOnClickListF = iOnClickListF;
    }

    public ArrayList<SongModel> getSongModel() {
        return SongModel;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setSongModel(ArrayList<SongModel> SongModel) {
        this.SongModel = SongModel;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public songFavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new songFavoritesViewHolder(ItemRcvFavoritesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull songFavoritesViewHolder holder, int position) {
        holder.binding.titleView.setText(SongModel.get(position).getTitle());
        holder.binding.durationView.setText(FileUtils.getDuration(SongModel.get(position).getDuration()));
        holder.binding.sizeView.setText(FileUtils.getSize(SongModel.get(position).getSize()));

        Glide.with(context).load(SongModel.get(position).getImageSong()).placeholder(context.getDrawable(R.drawable.bg)).into(holder.binding.artworkView);

        holder.itemView.setOnClickListener(view -> {
            iOnClickListF.onClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return SongModel.size();
    }

    public static class songFavoritesViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvFavoritesBinding binding;

        public songFavoritesViewHolder(ItemRcvFavoritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private interface IOnClickListF {
        void onClick(int position);

    }
}
