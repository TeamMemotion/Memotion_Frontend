package com.example.memotion.mypage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memotion.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding profileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(profileBinding.getRoot());


    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
    }

    private void initActionBar() {
        profileBinding.actionProfile.appbarPageNameLeftTv.setText("내 프로필");

        profileBinding.actionProfile.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
