package com.example.memotion.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentDiaryBinding;

public class DiaryFragment  extends Fragment {

    FragmentDiaryBinding diaryBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        diaryBinding = FragmentDiaryBinding.inflate(inflater, container, false);

        return diaryBinding.getRoot();
    }
}
