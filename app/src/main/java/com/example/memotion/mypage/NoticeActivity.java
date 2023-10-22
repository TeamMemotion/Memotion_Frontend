package com.example.memotion.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.databinding.ActivityNoticeBinding;
import com.example.memotion.mypage.get.notice.all.NoticeAllGetResponse;
import com.example.memotion.mypage.get.notice.all.NoticeAllGetResult;
import com.example.memotion.mypage.get.notice.all.NoticeAllGetService;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity implements NoticeAllGetResult {
    ActivityNoticeBinding noticeBinding;
    private NoticeAdapter noticeAdapter;
    private static String TAG = "NoticeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeBinding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(noticeBinding.getRoot());

        noticeAdapter = new NoticeAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
        noticeAll();
    }

    private void initActionBar() {
        noticeBinding.actionNotice.appbarPageNameLeftTv.setText("공지사항");

        noticeBinding.actionNotice.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 공지사항 전체 조회
    private void noticeAll() {
        NoticeAllGetService noticeAllGetService = new NoticeAllGetService();
        noticeAllGetService.setNoticeAllGetResult(this);
        noticeAllGetService.getNoticeAll();
    }

    @Override
    public void getNoticeAllSuccess(int code, ArrayList<NoticeAllGetResponse.Result> result) {
        Log.d(TAG, "공지사항 조회 성공");
        initRecycler(result);

        for (int i=0; i < result.size() - 1; i++) {
            Log.d(TAG, "notice id: " + result.get(i).getNoticeId());
            Log.d(TAG, "notice title: " + result.get(i).getName());
            Log.d(TAG, "notice date: " + result.get(i).getCreatedAt());
        }
    }

    @Override
    public void getNoticeAllFailure(int code, String message) {
        Log.d(TAG, "공지사항 조회 실패");
    }

    private void initRecycler(ArrayList<NoticeAllGetResponse.Result> result) {
        noticeAdapter.setNoticeAllList(result);
        noticeBinding.rvNotice.setAdapter(noticeAdapter);
        noticeBinding.rvNotice.setLayoutManager(new LinearLayoutManager(this));
        // 구분선
        noticeBinding.rvNotice.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }
}
