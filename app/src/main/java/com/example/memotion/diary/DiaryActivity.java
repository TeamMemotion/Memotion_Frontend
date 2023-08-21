package com.example.memotion.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityDiaryBinding;

public class DiaryActivity extends AppCompatActivity {

    ActivityDiaryBinding diaryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryBinding = ActivityDiaryBinding.inflate(getLayoutInflater());
        setContentView(diaryBinding.getRoot());

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        diaryBinding.selectedDate.setText(date);

        // 창 닫기
        diaryBinding.include.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 다이어리 추가 다이어로그로 이동
        diaryBinding.addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceAddDialog dialog = new PlaceAddDialog(DiaryActivity.this);
                dialog.start();
            }
        });

        // 작성완료
        diaryBinding.btnDiarySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
