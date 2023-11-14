package com.example.musicmp3java.viewpager.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicmp3java.fragment.favorites.FavoritesFragment;
import com.example.musicmp3java.fragment.home.HomeFragment;
import com.example.musicmp3java.fragment.individual.IndividualFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private IndividualFragment individualFragment;

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Log.d("DAT", "createFragment: " + "1");
                return homeFragment;
            case 1:
                Log.d("DAT", "createFragment: " + "2");
                return favoritesFragment;
            default:
                Log.d("DAT", "createFragment: " + "3");
                return individualFragment;
        }
    }

    public void setFragments(HomeFragment homeFragment, FavoritesFragment favoritesFragment, IndividualFragment individualFragment) {
        this.homeFragment = homeFragment;
        this.favoritesFragment = favoritesFragment;
        this.individualFragment = individualFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
