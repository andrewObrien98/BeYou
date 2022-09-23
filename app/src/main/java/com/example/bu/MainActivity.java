package com.example.bu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bu.databinding.ActivityMainBinding;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //MobileAds.initialize(this);
    }
}