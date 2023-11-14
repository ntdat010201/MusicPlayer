package com.example.musicmp3java.fragment.favorites.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicmp3java.databinding.ItemRcvFavoritesBinding;
import com.example.musicmp3java.fragment.home.model.SongModel;
import com.example.musicmp3java.play.PlayerActivity;
import com.example.musicmp3java.utils.FileUtils;

import java.util.ArrayList;

public class RcvFavoritesAdapter extends RecyclerView.Adapter<RcvFavoritesAdapter.songFavoritesViewHolder> /*implements Filterable*/ {
    private ArrayList<SongModel> SongModel;
    private Context context;
    //    private ArrayList<SongModel> SongModelFilter;
    private IOnClickListF iOnClickListF;



    public RcvFavoritesAdapter(ArrayList<SongModel> SongModel, Context context) {
        this.SongModel = SongModel;
        this.context = context;
//        this.SongModelFilter = SongModel;
    }

    public void setiOnClickListF(IOnClickListF iOnClickListF) {
        this.iOnClickListF = iOnClickListF;
    }

    public ArrayList<SongModel> getSongModel() {
        return SongModel;
    }


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

        //Glide.with(context).load(SongModelFilter.get(position).getImageSong()).placeholder(context.getDrawable(R.drawable.bg)).into(holder.binding.artworkView);

        holder.itemView.setOnClickListener(view -> {
            iOnClickListF.onClick(holder.getAdapterPosition());

        });
    }

    @Override
    public int getItemCount() {
        return SongModel.size();
    }

//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    SongModelFilter = SongModel;
//                } else {
//                    ArrayList<SongModel> filteredList = new ArrayList<>();
//                    for (SongModel row : SongModel) {
//                        if (row.getTitle().toLowerCase().startsWith(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    SongModelFilter = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = SongModelFilter;
//                return filterResults;
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                SongModelFilter = (ArrayList<SongModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//
//    }

    public static class songFavoritesViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvFavoritesBinding binding;

        public songFavoritesViewHolder(ItemRcvFavoritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private interface IOnClickListF {
        void onClick(int position);

    }
}
