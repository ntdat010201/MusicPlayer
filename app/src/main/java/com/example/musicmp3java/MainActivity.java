package com.example.musicmp3java;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicmp3java.databinding.ActivityMainBinding;
import com.example.musicmp3java.fragment.FavoritesFragment;
import com.example.musicmp3java.fragment.HomeFragment;
import com.example.musicmp3java.fragment.IndividualFragment;
import com.example.musicmp3java.viewpager.adapter.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyViewPagerAdapter myViewPagerAdapter;
    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private IndividualFragment individualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initData();
        initView();
        initListener();
    }

    private void initData() {
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        individualFragment = new IndividualFragment();
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        myViewPagerAdapter.setFragments(homeFragment, favoritesFragment, individualFragment);
    }

    private void initView() {
        tabLayoutMediator();
    }

    private void initListener() {

    }

    @SuppressLint("ResourceType")
    private void tabLayoutMediator() {
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(myViewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_home);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_insert_invitation);
                    break;

            }
        }).attach();
        binding.viewPager.setCurrentItem(0);
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public FavoritesFragment getFavoritesFragment() {
        return favoritesFragment;
    }

    public IndividualFragment getIndividualFragment() {
        return individualFragment;
    }
}