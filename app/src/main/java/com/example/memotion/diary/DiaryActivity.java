package com.example.memotion.diary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityDiaryBinding;
import com.example.memotion.diary.get.content.GetContentResponse;
import com.example.memotion.diary.get.content.GetContentResult;
import com.example.memotion.diary.get.content.GetContentService;
import com.example.memotion.diary.post.content.PostContentRequest;
import com.example.memotion.diary.post.content.PostContentResult;
import com.example.memotion.diary.post.content.PostContentService;
import com.example.memotion.diary.post.emotion.PostEmotionRequest;
import com.example.memotion.diary.post.emotion.PostEmotionService;

public class DiaryActivity extends AppCompatActivity implements PostContentResult, GetContentResult {
    private static String TAG = "DiaryActivity";
    ActivityDiaryBinding diaryBinding;
    public static String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryBinding = ActivityDiaryBinding.inflate(getLayoutInflater());
        setContentView(diaryBinding.getRoot());

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        diaryBinding.selectedDate.setText(date);

        // 오늘의 다이어리 조회
        todayDiary();

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

        // 다이어리 내용 작성완료
        diaryBinding.btnDiarySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText diaryTitle = diaryBinding.diaryTitle;
                EditText diaryContent = diaryBinding.diaryContent;
                postContent(diaryTitle.getText().toString(), diaryContent.getText().toString());
            }
        });
    }

    // 다이어리 내용 저장 API 호출
    private void postContent(String diaryTitle, String diaryContent) {
        Log.d(TAG, "API 호출");
        PostContentService postContentService = new PostContentService();
        postContentService.setPostContentResult(this);
        postContentService.postContent(new PostContentRequest(diaryTitle, diaryContent, date));
    }
    
    @Override
    public void postContentSuccess(int code, Long result) {
        Log.d(TAG, "다이어리 저장 성공");
    }

    @Override
    public void postContentFailure(int code, String message) {
        Log.d(TAG, "다이어리 저장 실패");
    }
    
    // 오늘의 루트 클릭해서 장소 등록 후 실행될 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // PlaceAddDialog에서 전달한 데이터를 확인
            int diaryId = data.getIntExtra("diaryId", -1);

            // 데이터를 사용하여 화면을 업데이트하거나 필요한 작업을 수행
            if (diaryId != -1) {
                // diaryId가 -1이 아니면 아래 내용 함수화하기
                // 1. 오늘의 루트 전체 가져와서 화면에 띄우기
                // 2. 오늘의 다이어리 가져와서 화면에 띄우기
                todayDiary();
            }
        }
    }

    // 오늘의 루트 전체 조회 후 화면에 출력 (다이어리 장소 전체)
    protected void todayRouteList() {

    }

    // 오늘의 다이어리 조회
    protected void todayDiary() {
        GetContentService getContentService = new GetContentService();
        getContentService.setGetContentResult(this);
        getContentService.getContent(date);
    }

    // 오늘의 다이어리 조회 성공 -> 다이어리 내용 화면에 출력
    @Override
    public void getContentSuccess(int code, GetContentResponse.Result result) {
        Log.d(TAG, "오늘의 다이어리 조회 성공");
        EditText diaryTitle = diaryBinding.diaryTitle;
        EditText diaryContent = diaryBinding.diaryContent;

        // 다이어리 내용 화면에 출력
        diaryTitle.setText(result.getTitle());
        diaryContent.setText(result.getContent());
    }

    // 오늘의 다이어리 조회 실패
    @Override
    public void getContentFailure(int code, String message) {
        Log.d(TAG, "오늘의 다이어리 조회 실패");
        Toast.makeText(this, "오늘의 다이어리 조회 실패", Toast.LENGTH_SHORT).show();
    }
}
