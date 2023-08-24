package com.example.memotion.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.account.login.LoginActivity;
import com.example.memotion.databinding.FragmentMypageBinding;

public class MypageFragment extends Fragment {

    FragmentMypageBinding mypageBinding;

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
                // Intent intent = new Intent(getContext(), )
            }
        });

        return mypageBinding.getRoot();
    }
}
