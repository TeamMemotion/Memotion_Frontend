package com.example.memotion.route;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentRouteBinding;
import com.example.memotion.local.LocalguideActivity;

public class RouteFragment extends Fragment {

    FragmentRouteBinding routeBinding;
    private static String TAG = "RouteFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routeBinding = FragmentRouteBinding.inflate(inflater, container, false);

        routeBinding.btnToLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LocalguideActivity.class);
                startActivity(intent);
            }
        });
        return routeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
