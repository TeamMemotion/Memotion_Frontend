package com.example.memotion.home;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentHomeBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//zimnii
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;
    private MaterialCalendarView calendarView;
    private TextView dateText;
    String dateClicked = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        calendarView = homeBinding.calendarView;
        //dateText = homeBinding.dateText;

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            dateClicked = String.format("%d / %d / %d", date.getYear(), date.getMonth(), date.getDay());
            Log.i("TAG", dateClicked);
            //dateText.setText(dateClicked);
        });

        //날짜 누르면 사진 띄움
        SelectorDecorator selectorDecorator = new SelectorDecorator(getActivity());
        calendarView.addDecorator(selectorDecorator);

        //오늘 날짜에 빨간점 등장
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today())));

        //이모지 등장
        Drawable image1 = getResources().getDrawable(R.drawable.smile);
        Drawable image2 = getResources().getDrawable(R.drawable.sad);

        Set<CalendarDay> datesWithImages = new HashSet<>();
        datesWithImages.add(CalendarDay.from(2023, 8, 2));
        datesWithImages.add(CalendarDay.from(2023, 8, 3));

        ImageDecorator imageDecorator = new ImageDecorator(image1, datesWithImages);
        calendarView.addDecorator(imageDecorator);
        //토큰 확인
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        Log.d("home_accessToken::", sharedPreferences.getString("accessToken", ""));
        Log.d("home_refreshToken::", sharedPreferences.getString("refreshToken", ""));

        return homeBinding.getRoot();
    }
}