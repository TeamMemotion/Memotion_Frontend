package com.example.memotion.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {

    FragmentSearchBinding searchBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);

        return searchBinding.getRoot();
    }
}
