package com.example.memotion.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivitySearchPasswordBinding;


public class SearchPwdActivity extends AppCompatActivity {
    ActivitySearchPasswordBinding activitySearchPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchPasswordBinding = ActivitySearchPasswordBinding.inflate(getLayoutInflater());
        setContentView(activitySearchPasswordBinding.getRoot());
    }
}
