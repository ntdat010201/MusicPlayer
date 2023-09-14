package com.example.musicmp3java;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicmp3java.databinding.ActivityMainBinding;
import com.example.musicmp3java.viewpager.adapter.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyViewPagerAdapter myViewPagerAdapter;

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
        myViewPagerAdapter = new MyViewPagerAdapter(this);

    }

    private void initView() {
        tabLayoutMediator();
    }

    private void initListener() {

    }

    private void tabLayoutMediator() {
        binding.viewPager.setAdapter(myViewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setText("Home");
                    break;
                case 1:
                    tab.setText("Favorites");
                    break;
                case 2:
                    tab.setText("Individual");
                    break;

            }
        }).attach();
        binding.viewPager.setCurrentItem(0);
    }

}