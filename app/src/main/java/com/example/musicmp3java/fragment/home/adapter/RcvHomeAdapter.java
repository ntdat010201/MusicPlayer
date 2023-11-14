package com.example.musicmp3java.fragment.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private IOnClickListH iOnClickListH;
    private Context context;
    private ArrayList<SongModel> songModels;
    private ArrayList<SongModel> SongModelilter;

    public RcvHomeAdapter(ArrayList<SongModel> songModels, Context context) {
        this.songModels = songModels;
        this.SongModelilter = songModels;
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
        holder.binding.titleView.setText(SongModelilter.get(position).getTitle());
        holder.binding.durationView.setText(FileUtils.getDuration(SongModelilter.get(position).getDuration()));
        holder.binding.sizeView.setText(FileUtils.getSize(SongModelilter.get(position).getSize()));

        Glide.with(context).load(SongModelilter.get(position).getImageSong())
                .placeholder(context.getDrawable(R.drawable.bg)).into(holder.binding.artworkView);

        holder.binding.moreVert.setOnClickListener(view -> {
            iOnClickListH.showDialogHome(holder.getAdapterPosition());

        });

        holder.itemView.setOnClickListener(view -> {
            iOnClickListH.onClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (SongModelilter != null){
            return SongModelilter.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    SongModelilter = songModels;
                } else {
                    ArrayList<SongModel> filteredList = new ArrayList<>();
                    for (SongModel row : songModels) {
                        if (row.getTitle().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    SongModelilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = SongModelilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                SongModelilter = (ArrayList<SongModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void setiOnClickListH(IOnClickListH iOnClickListH) {
        this.iOnClickListH = iOnClickListH;
    }

    public static class SongHomeViewHolder extends RecyclerView.ViewHolder {
        private ItemRcvHomeBinding binding;

        public SongHomeViewHolder(ItemRcvHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface IOnClickListH {
        void onClick(int position);
        void showDialogHome(int position);
    }
}
