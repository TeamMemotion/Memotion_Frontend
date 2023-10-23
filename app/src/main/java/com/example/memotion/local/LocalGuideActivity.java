package com.example.memotion.local;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityLocalGuideBinding;


public class LocalGuideActivity extends AppCompatActivity {
    ActivityLocalGuideBinding localGuideBinding;
    private static String TAG = "LocalGuideActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localGuideBinding = ActivityLocalGuideBinding.inflate(getLayoutInflater());
        setContentView(localGuideBinding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
