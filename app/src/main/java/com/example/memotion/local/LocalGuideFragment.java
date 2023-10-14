package com.example.memotion.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentLocalguideBinding;

public class LocalGuideFragment extends Fragment {

    FragmentLocalguideBinding localguideBinding;
    private ImageView heartIcon;
    private ImageView noHeartIcon;
    private boolean isHeartVisible = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        localguideBinding = FragmentLocalguideBinding.inflate(inflater, container, false);

        heartIcon = getView().findViewById(R.id.hearticon);
        noHeartIcon = getView().findViewById(R.id.nohearticon);

        heartIcon.setVisibility(View.INVISIBLE);
        noHeartIcon.setVisibility(View.VISIBLE);

        heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHeartVisible) {
                    heartIcon.setVisibility(View.VISIBLE);
                    noHeartIcon.setVisibility(View.INVISIBLE);
                    isHeartVisible = false;
                }
            }
        });

        noHeartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHeartVisible) {
                    noHeartIcon.setVisibility(View.VISIBLE);
                    heartIcon.setVisibility(View.INVISIBLE);
                    isHeartVisible = true;
                }
            }
        });

        return localguideBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}