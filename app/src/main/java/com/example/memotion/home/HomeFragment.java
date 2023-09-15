package com.example.memotion.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentHomeBinding;
import com.example.memotion.diary.DiaryActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
//zimnii
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HomeFragment extends Fragment {
    FragmentHomeBinding homeBinding;
    private MaterialCalendarView calendarView;
    String dateClicked = null;

    CalendarDay today = null;

    int SUBACTIITY_REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        calendarView = homeBinding.calendarView;
        //dateText = homeBinding.dateText;

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });

        // 달 별 이동
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                // date 객체에서 현재 표시된 월을 가져옵니다.
                int year = date.getYear();
                int month = date.getMonth(); // 0부터 시작하므로 1을 더해야 합니다.

                // year와 month를 사용하여 현재 표시된 월을 알 수 있습니다.
                String currentMonth = year + "-" + (month + 1);

                Log.d("Calendar", "현재 월: " + currentMonth);
            }
        });

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            dateClicked = String.format("%d.%d.%d", date.getYear(), date.getMonth(), date.getDay());
            Log.i("TAG", dateClicked);

            homeBinding.selectedDate.setText(dateClicked);
            homeBinding.dateStart.setText(String.format("%d",date.getDay()));
            homeBinding.dateEnd.setText(String.format("%d",date.getDay()));

            // 창 닫기
            homeBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeBinding.timeLine.setVisibility(View.GONE);
                }
            });

            // 장소 등록 추가 버튼
            homeBinding.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DiaryActivity.class);
                    intent.putExtra("date", dateClicked);
                    startActivityForResult(intent, SUBACTIITY_REQUEST_CODE);
                    getActivity().overridePendingTransition(0, 0);
                }
            });

            homeBinding.timeLine.setVisibility(View.VISIBLE);
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