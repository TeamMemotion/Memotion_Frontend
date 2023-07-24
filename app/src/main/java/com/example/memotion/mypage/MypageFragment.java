package com.example.memotion.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentMypageBinding;

public class MypageFragment extends Fragment {

    FragmentMypageBinding mypageBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mypageBinding = FragmentMypageBinding.inflate(inflater, container, false);

        return mypageBinding.getRoot();
    }
}
