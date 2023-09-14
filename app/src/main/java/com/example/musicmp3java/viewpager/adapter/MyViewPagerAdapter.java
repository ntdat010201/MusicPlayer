package com.example.musicmp3java.viewpager.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicmp3java.fragment.favorites.FavoritesFragment;
import com.example.musicmp3java.fragment.home.HomeFragment;
import com.example.musicmp3java.fragment.individual.IndividualFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoritesFragment();
            default:
                return new IndividualFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
