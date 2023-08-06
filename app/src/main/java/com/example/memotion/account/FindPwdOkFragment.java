package com.example.memotion.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.MainActivity;
import com.example.memotion.account.login.LoginActivity;
import com.example.memotion.databinding.FragmentFindPwdOkBinding;
import com.example.memotion.databinding.FragmentSearchBinding;

public class FindPwdOkFragment extends Fragment {
    private String email;

    FragmentFindPwdOkBinding findPwdOkBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        findPwdOkBinding = FragmentFindPwdOkBinding.inflate(inflater, container, false);

        Log.d("email: ", getArguments().getString("email"));
        email = getArguments().getString("email");
        findPwdOkBinding.etInputEmail.setText(email);


        findPwdOkBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return findPwdOkBinding.getRoot();
    }
}
