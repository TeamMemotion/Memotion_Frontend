package com.example.memotion.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityMydiaryBinding;

public class MyDiaryActivity extends AppCompatActivity {
    ActivityMydiaryBinding mydiaryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydiaryBinding = ActivityMydiaryBinding.inflate(getLayoutInflater());
        setContentView(mydiaryBinding.getRoot());


        // 스피너
        setupSpinnerText();
        setupSpinnerHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
    }

    private void initActionBar() {
        mydiaryBinding.actionMydiary.appbarPageNameLeftTv.setText("나의 다이어리");

        mydiaryBinding.actionMydiary.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupSpinnerText() {

    }

    private void setupSpinnerHandler() {

    }
}
