package com.example.memotion.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityMydiaryBinding;
import com.example.memotion.diary.DiaryActivity;
import com.example.memotion.mypage.get.mydiary.GetDiaryEmotionResponse;
import com.example.memotion.mypage.get.mydiary.GetDiaryEmotionResult;
import com.example.memotion.mypage.get.mydiary.GetDiaryEmotionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDiaryActivity extends AppCompatActivity implements GetDiaryEmotionResult {
    ActivityMydiaryBinding mydiaryBinding;
    String emotion = "happy";
    private static String TAG = "MyDiaryActivity";
    private MyDiaryAdapter myDiaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydiaryBinding = ActivityMydiaryBinding.inflate(getLayoutInflater());
        setContentView(mydiaryBinding.getRoot());

        myDiaryAdapter = new MyDiaryAdapter(this);
        mydiaryBinding.btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyBig();
                smileSmall();
                notBadSmall();
                sadSmall();
                upsetSmall();
                emotion = "happy";
                diaryEmotion();
            }
        });

        mydiaryBinding.btnSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happySmall();
                smileBig();
                notBadSmall();
                sadSmall();
                upsetSmall();
                emotion = "smile";
                diaryEmotion();
            }
        });

        mydiaryBinding.btnNotbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happySmall();
                smileSmall();
                notBadBig();
                sadSmall();
                upsetSmall();
                emotion = "not bad";
                diaryEmotion();
            }
        });


        mydiaryBinding.btnSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happySmall();
                smileSmall();
                notBadSmall();
                sadBig();
                upsetSmall();
                emotion = "sad";
                diaryEmotion();
            }
        });

        mydiaryBinding.btnUpset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happySmall();
                smileSmall();
                notBadSmall();
                sadSmall();
                upsetBig();
                emotion = "upset";
                diaryEmotion();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
        diaryEmotion();
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

    // 감정별 다이어리 조회
    private void diaryEmotion() {
        GetDiaryEmotionService getDiaryEmotionService = new GetDiaryEmotionService();
        getDiaryEmotionService.setGetDiaryEmotionResult(this);
        getDiaryEmotionService.getDiaryEmotion(emotion);
    }

    @Override
    public void getDiaryEmotionSuccess(int code, ArrayList<GetDiaryEmotionResponse.Result> result) {
        Log.d(TAG, "감정별 다이어리 조회 성공");
        initRecycler(result);
    }

    private void initRecycler(ArrayList<GetDiaryEmotionResponse.Result> result) {
        myDiaryAdapter.setMyDiaryList(result);
        mydiaryBinding.myDiaryList.setAdapter(myDiaryAdapter);
        mydiaryBinding.myDiaryList.setLayoutManager(new LinearLayoutManager(this));

        myDiaryAdapter.setItemClickListener(new MyDiaryAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(GetDiaryEmotionResponse.Result result) {
               Intent intent = new Intent(MyDiaryActivity.this, DiaryActivity.class);
               intent.putExtra("date", result.getCreatedDate());
               startActivity(intent);
               overridePendingTransition(0, 0);
           }
       });
    }

    @Override
    public void getDiaryEmotionFailure(int code, String message) {
        Log.d(TAG, "감정별 다이어리 조회 실패");
    }

    // 사진 크기 조정
    private void happySmall() {
        ViewGroup.LayoutParams happyParams = mydiaryBinding.btnHappy.getLayoutParams();
        happyParams.width = (int) getResources().getDimension(R.dimen.button_width_60sp);
        happyParams.height = (int) getResources().getDimension(R.dimen.button_height_60sp);
        mydiaryBinding.btnHappy.setLayoutParams(happyParams);
    }

    private void happyBig() {
        ViewGroup.LayoutParams happyParams = mydiaryBinding.btnHappy.getLayoutParams();
        happyParams.width = (int) getResources().getDimension(R.dimen.button_width_80sp);
        happyParams.height = (int) getResources().getDimension(R.dimen.button_height_80sp);
        mydiaryBinding.btnHappy.setLayoutParams(happyParams);
    }

    private void smileSmall() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnSmile.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_60sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_60sp);
        mydiaryBinding.btnSmile.setLayoutParams(smileParams);
    }

    private void smileBig() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnSmile.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_80sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_80sp);
        mydiaryBinding.btnSmile.setLayoutParams(smileParams);
    }

    private void notBadSmall() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnNotbad.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_60sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_60sp);
        mydiaryBinding.btnNotbad.setLayoutParams(smileParams);
    }

    private void notBadBig() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnNotbad.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_80sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_80sp);
        mydiaryBinding.btnNotbad.setLayoutParams(smileParams);
    }

    private void sadSmall() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnSad.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_60sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_60sp);
        mydiaryBinding.btnSad.setLayoutParams(smileParams);
    }

    private void sadBig() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnSad.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_80sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_80sp);
        mydiaryBinding.btnSad.setLayoutParams(smileParams);
    }

    private void upsetSmall() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnUpset.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_60sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_60sp);
        mydiaryBinding.btnUpset.setLayoutParams(smileParams);
    }

    private void upsetBig() {
        ViewGroup.LayoutParams smileParams = mydiaryBinding.btnUpset.getLayoutParams();
        smileParams.width = (int) getResources().getDimension(R.dimen.button_width_80sp);
        smileParams.height = (int) getResources().getDimension(R.dimen.button_height_80sp);
        mydiaryBinding.btnUpset.setLayoutParams(smileParams);
    }
}
