package com.example.memotion.mypage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityWithdrawalBinding;

public class WithdrawalActivity extends AppCompatActivity {
    ActivityWithdrawalBinding withdrawalBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        withdrawalBinding = ActivityWithdrawalBinding.inflate(getLayoutInflater());
        setContentView(withdrawalBinding.getRoot());


    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
    }

    private void initActionBar() {
        withdrawalBinding.actionWithdrawal.appbarPageNameLeftTv.setText("계정 탈퇴");

        withdrawalBinding.actionWithdrawal.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
