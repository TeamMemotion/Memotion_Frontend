package com.example.memotion.local;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivitySearchLocalguideBinding;

public class LocalguideActivity extends AppCompatActivity {
    ActivitySearchLocalguideBinding searchLocalguideBinding;
    private static String TAG = "SearchLocalGuide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchLocalguideBinding = ActivitySearchLocalguideBinding.inflate(getLayoutInflater());
        setContentView(searchLocalguideBinding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
