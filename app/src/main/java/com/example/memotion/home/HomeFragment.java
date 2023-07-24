package com.example.memotion.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return homeBinding.getRoot();
    }
}
