package com.example.memotion.mypage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityNoticeBinding;

public class NoticeActivity extends AppCompatActivity {
    ActivityNoticeBinding noticeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeBinding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(noticeBinding.getRoot());


    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
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
}
