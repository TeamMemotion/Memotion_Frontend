package com.example.memotion.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.memotion.databinding.FragmentHomeBinding;
//zimnii
import android.widget.Toast;

public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;
    private CalendarView calendarView;
    private TextView dateText;
    String dateClicked = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        calendarView = homeBinding.calendarView;
        dateText = homeBinding.dateText;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dateClicked = String.format("%d / %d / %d", year, month + 1, dayOfMonth);
                Log.i("TAG", dateClicked);
                dateText.setText(dateClicked);
            }
        });

        //토큰 확인
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        Log.d("home_accessToken::", sharedPreferences.getString("accessToken", ""));
        Log.d("home_refreshToken::", sharedPreferences.getString("refreshToken", ""));

        return homeBinding.getRoot();
    }
}
