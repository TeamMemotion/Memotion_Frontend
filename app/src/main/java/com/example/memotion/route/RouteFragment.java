package com.example.memotion.route;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentRouteBinding;

public class RouteFragment extends Fragment {

    FragmentRouteBinding routeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routeBinding = FragmentRouteBinding.inflate(inflater, container, false);

        return routeBinding.getRoot();
    }
}
