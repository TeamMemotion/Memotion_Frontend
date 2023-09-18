package com.example.memotion.diary;

import static com.example.memotion.MainActivity.context;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityDiaryBinding;
import com.example.memotion.diary.get.content.GetContentResponse;
import com.example.memotion.diary.get.content.GetContentResult;
import com.example.memotion.diary.get.content.GetContentService;
import com.example.memotion.diary.get.emotion.GetEmotionResponse;
import com.example.memotion.diary.get.emotion.GetEmotionResult;
import com.example.memotion.diary.get.emotion.GetEmotionService;
import com.example.memotion.diary.patch.emotion.PatchEmotionResponse;
import com.example.memotion.diary.patch.emotion.PatchEmotionResult;
import com.example.memotion.diary.post.content.PostContentRequest;
import com.example.memotion.diary.post.content.PostContentResult;
import com.example.memotion.diary.post.content.PostContentService;
import com.example.memotion.diary.post.emotion.PostEmotionRequest;
import com.example.memotion.diary.post.emotion.PostEmotionService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DiaryActivity extends AppCompatActivity implements PostContentResult, GetContentResult, GetEmotionResult {
    private static String TAG = "DiaryActivity";
    private static int REQUEST_ADD_DIARY = 100;
    private static int REQUEST_EDIT_DIARY = 200;
    private ActivityDiaryBinding diaryBinding;
    public static String date;
    private RecyclerView recyclerView;
    private DiaryRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryBinding = ActivityDiaryBinding.inflate(getLayoutInflater());
        setContentView(diaryBinding.getRoot());

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        diaryBinding.selectedDate.setText(date);

        recyclerView = diaryBinding.reviewList;
        adapter = new DiaryRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 오늘의 루트 전체 조회
        todayRouteList();

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
                String diaryTitle = diaryBinding.diaryTitle.getText().toString();
                String diaryContent = diaryBinding.diaryContent.getText().toString();

                if(diaryTitle.equals("") || diaryContent.equals(""))
                    Toast.makeText(getApplicationContext(), "다이어리 제목과 내용을 입력하세요", Toast.LENGTH_SHORT).show();
                else
                    postContent(diaryTitle, diaryContent);
            }
        });
    }

//    public void onRecyclerItemClick(DiaryItem item) {
//        // DiaryActivity에서 PlaceEditDialog 호출
//        Intent intent = new Intent(DiaryActivity.this, PlaceEditDialog.class);
//        //intent.putExtra("diaryItem", item); // 선택한 아이템 데이터 전달
//        PlaceEditDialog.item = item;
//        PlaceEditDialog.context = context;
//        startActivityForResult(intent, REQUEST_EDIT_DIARY);
//    }

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
        Log.d(TAG, "Dialog -> DiaryActivity로 이동");

        if ((requestCode ==  REQUEST_ADD_DIARY || requestCode == REQUEST_EDIT_DIARY) && resultCode == RESULT_OK) {
            // PlaceAddDialog에서 전달한 데이터를 확인
            int diaryId = data.getIntExtra("diaryId", -1);

            // 데이터를 사용하여 화면을 업데이트하거나 필요한 작업을 수행
            if (diaryId != -1) {
                todayRouteList();
                todayDiary();
                adapter.notifyDataSetChanged();
            }
        }
    }

    // 오늘의 루트 전체 조회 (다이어리 장소 전체)
    protected void todayRouteList() {
        GetEmotionService getEmotionService = new GetEmotionService();
        getEmotionService.setGetEmotionResult(this);
        getEmotionService.getEmotion(date);
    }

    // 오늘의 루트 전체 조회 성공 -> 다이어리 장소 전체 화면에 출력
    @Override
    public void getEmotionSuccess(int code, ArrayList<GetEmotionResponse.Result> result) {
        Log.d(TAG, "오늘의 루트 전체 조회 성공");

        // TO DO: 반복문 돌려서 하나씩 꺼내 화면에 출력
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ArrayList<DiaryItem> diaryList = (ArrayList<DiaryItem>) result.stream()
                    .map(r -> new DiaryItem(r.getDiaryId(), r.getLatitude(), r.getLongitude(), r.getEmotion(), r.getKeyWord()))
                    .collect(Collectors.toList());
            adapter.setDiaryList(diaryList);
        }
    }

    // 오늘의 루트 전체 조회 실패
    @Override
    public void getEmotionFailure(int code, String message) {
        Log.d(TAG, "오늘의 루트 전체 조회 실패");
        Toast.makeText(this, "오늘의 루트 전체 조회 실패", Toast.LENGTH_SHORT).show();
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
