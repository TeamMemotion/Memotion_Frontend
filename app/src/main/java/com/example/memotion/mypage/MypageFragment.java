package com.example.memotion.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.memotion.account.login.LoginActivity;
import com.example.memotion.databinding.FragmentMypageBinding;
import com.example.memotion.mypage.get.profile.GetProfileResponse;
import com.example.memotion.mypage.get.profile.GetProfileResult;
import com.example.memotion.mypage.get.profile.GetProfileService;

public class MypageFragment extends Fragment implements GetProfileResult {

    FragmentMypageBinding mypageBinding;
    private static String TAG = "MypageFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mypageBinding = FragmentMypageBinding.inflate(inflater, container, false);

        mypageBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mypageBinding.myDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(getContext(), MyDiaryActivity.class);
                 startActivity(intent);
            }
        });

        return mypageBinding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {
        GetProfileService getProfileService = new GetProfileService();
        getProfileService.setGetProfileResult(this);
        getProfileService.getProfile();
    }

    @Override
    public void getProfileSuccess(int code, GetProfileResponse.Result result) {
        Log.d(TAG, "프로필 조회 성공");
        mypageBinding.tvName.setText(result.getUsername());

        if(result.getImage() != null) {
            Glide.with(this)
                    .load(result.getImage())
                    .apply(RequestOptions.circleCropTransform().centerCrop())
                    .into(mypageBinding.profileImage);
        }
    }

    @Override
    public void getProfileFailure(int code, String message) {
        Log.d(TAG, "프로필 조회 실패");
    }
}
