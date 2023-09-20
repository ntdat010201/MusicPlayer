package com.example.musicmp3java.fragment.favorites.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ItemRcvFavoritesBinding;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.utils.FileUtils;

import java.util.ArrayList;

public class RcvFavoritesAdapter extends RecyclerView.Adapter<RcvFavoritesAdapter.songFavoritesViewHolder> implements Filterable {
    private ArrayList<SongModel> songModels;
    private Context context;
    private ArrayList<SongModel> songModelFilter;


    public RcvFavoritesAdapter(ArrayList<SongModel> songModels, Context context, ArrayList<SongModel> songModelFilter) {
        this.songModels = songModels;
        this.context = context;
        this.songModelFilter = songModelFilter;
    }

    public ArrayList<SongModel> getSongModels() {
        return songModels;
    }

    public void setSongModels(ArrayList<SongModel> songModels) {
        this.songModels = songModels;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public songFavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new songFavoritesViewHolder(ItemRcvFavoritesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull songFavoritesViewHolder holder, int position) {
        holder.binding.titleView.setText(songModelFilter.get(position).getTitle());
        holder.binding.durationView.setText(FileUtils.getDuration(songModelFilter.get(position).getDuration()));
        holder.binding.sizeView.setText(FileUtils.getSize(songModelFilter.get(position).getSize()));

        Glide.with(context).load(songModelFilter.get(position).getImageSong())
                .placeholder(context.getDrawable(R.drawable.bg)).into(holder.binding.artworkView);
        holder.itemView.setOnClickListener(view -> {

        });

    }

    @Override
    public int getItemCount() {
        return songModelFilter.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    songModelFilter = songModels;
                } else {
                    ArrayList<SongModel> filteredList = new ArrayList<>();
                    for (SongModel row : songModels) {
                        if (row.getTitle().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    songModelFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = songModelFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                songModelFilter = (ArrayList<SongModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public static class songFavoritesViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvFavoritesBinding binding;

        public songFavoritesViewHolder(ItemRcvFavoritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
