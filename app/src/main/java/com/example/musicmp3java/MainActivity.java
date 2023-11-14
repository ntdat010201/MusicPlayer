package com.example.musicmp3java;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicmp3java.databinding.ActivityMainBinding;
import com.example.musicmp3java.fragment.favorites.FavoritesFragment;
import com.example.musicmp3java.fragment.home.HomeFragment;
import com.example.musicmp3java.fragment.individual.IndividualFragment;
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

    private void tabLayoutMediator() {
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(myViewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_small_icon);
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_small_icon);
                    tab.setText("Favorites");
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_small_icon);
                    tab.setText("Individual");
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