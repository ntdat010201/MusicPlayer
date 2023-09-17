package com.example.musicmp3java.fragment.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicmp3java.R;
import com.example.musicmp3java.databinding.ItemRcvHomeBinding;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.utils.FileUtils;

import java.util.ArrayList;

public class RcvHomeAdapter extends RecyclerView.Adapter<RcvHomeAdapter.SongHomeViewHolder> implements Filterable {
    private IOnClickList iOnClickList;

    private Context context;

    private ArrayList<SongModel> songModels;
    private int position;
    private ArrayList<SongModel> songModelFilter;

    public RcvHomeAdapter(ArrayList<SongModel> songModels, Context context) {
        this.songModels = songModels;
        this.songModelFilter = songModels;
        this.context = context;
    }

    public ArrayList<SongModel> getSongModels() {
        return songModels;
    }

    public void setSongModels(ArrayList<SongModel> songModels) {
        this.songModels = songModels;
    }


    @NonNull
    @Override
    public SongHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongHomeViewHolder(ItemRcvHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull SongHomeViewHolder holder, int position) {
        holder.binding.titleView.setText(songModelFilter.get(position).getTitle());
        holder.binding.durationView.setText(FileUtils.getDuration(songModelFilter.get(position).getDuration()));
        holder.binding.sizeView.setText(FileUtils.getSize(songModelFilter.get(position).getSize()));

        Glide.with(context).load(songModelFilter.get(position).getImageSong())
                .placeholder(context.getDrawable(R.drawable.bg)).into(holder.binding.artworkView);

        holder.binding.moreVert.setOnClickListener(view1 -> {
            iOnClickList.showDialogHome();
            Log.d("DAT", "onBindViewHolder: " + position);
        });


        holder.itemView.setOnClickListener(view -> {
            iOnClickList.onClick(holder.getAdapterPosition());


        });
    }

    @Override
    public int getItemCount() {
        return songModelFilter.size();
    }

    @Override
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

    public void setIOnClickList(IOnClickList iOnClickList) {
        this.iOnClickList = iOnClickList;
    }

    public static class SongHomeViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvHomeBinding binding;

        public SongHomeViewHolder(ItemRcvHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface IOnClickList {
        void onClick(int position);
        void showDialogHome();
    }
}
