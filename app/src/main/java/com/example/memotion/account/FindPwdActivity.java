package com.example.memotion.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.ActivityFindPasswordBinding;


public class FindPwdActivity extends AppCompatActivity {

    ActivityFindPasswordBinding findPwdBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findPwdBinding = ActivityFindPasswordBinding.inflate(getLayoutInflater());
        setContentView(findPwdBinding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(findPwdBinding.containerFragment.getId(), new FindPwdFragment()).commit();
    }

//    public void replaceFragment(int index) {
//        if(index == 1) {
//            getSupportFragmentManager().beginTransaction().replace(findPwdBinding.containerFragment.getId(), new FindPwdOkFragment()).commit();
//        }
//    }
}
