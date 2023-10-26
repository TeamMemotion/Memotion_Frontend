package com.example.memotion.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.databinding.FragmentHomeBinding;
import com.example.memotion.diary.DiaryActivity;
import com.example.memotion.diary.DiaryItem;
import com.example.memotion.diary.PlaceAddDialog;
import com.example.memotion.diary.get.emotion.GetEmotionResponse;
import com.example.memotion.diary.get.emotion.GetEmotionResult;
import com.example.memotion.diary.get.emotion.GetEmotionService;
import com.example.memotion.home.get.emotions.GetEmotionsResponse;
import com.example.memotion.home.get.emotions.GetEmotionsResult;
import com.example.memotion.home.get.emotions.GetEmotionsService;
import com.google.android.gms.maps.model.LatLng;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
//zimnii

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements GetEmotionsResult, GetEmotionResult {
    FragmentHomeBinding homeBinding;
    private MaterialCalendarView calendarView;
    private String dateClicked = null;
    public static String dateFormat = null;
    private static String TAG = "HomeFragment";
    private String selectedDate = null;
    private Context context;
    int SUBACTIITY_REQUEST_CODE = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        calendarView = homeBinding.calendarView;
        //dateText = homeBinding.dateText;

        // 원하는 형식으로 날짜 포맷팅
        LocalDate today = LocalDate.now();
        DateTimeFormatter todayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(todayFormat);
        Log.d("오늘 날짜 :: ", formattedDate);

        dateFormat = formattedDate.substring(0, 7);
        getEmotions();

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                org.threeten.bp.LocalDate inputText = day.getDate();
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
                String currentMonth = (month < 10  ? year + "-" + "0" + month : year + "-" + month);
                Log.d("Calendar", "현재 월: " + currentMonth);

                dateFormat = currentMonth;
                getEmotions();
            }
        });

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            dateClicked = String.format("%d.%d.%d", date.getYear(), date.getMonth(), date.getDay());

            // 달, 월 10 미만이면 0을 붙여주기
            String year = String.format("%d", date.getYear());
            String month = (date.getMonth() < 10 ? String.format("-0%d", date.getMonth()) : String.format("-%d", date.getMonth()));
            String day = (date.getDay() < 10 ? String.format("-0%d", date.getDay()) : String.format("-%d", date.getDay()));

            dateFormat = year + month + day;
            selectedDate = year + month + day;
            Log.i("TAG", dateClicked);

            homeBinding.tvStart.setText("");
            homeBinding.tvEnd.setText("");
            todayRouteList();

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
                    intent.putExtra("date", dateFormat);
                    startActivityForResult(intent, SUBACTIITY_REQUEST_CODE);
                    getActivity().overridePendingTransition(0, 0);
                }
            });

            homeBinding.timeLine.setVisibility(View.VISIBLE);
        });

        //날짜 누르면 사진 띄움
//        SelectorDecorator selectorDecorator = new SelectorDecorator(getActivity());
//        calendarView.addDecorator(selectorDecorator);

        // 오늘 날짜에 빨간점 등장
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today())));

        //토큰 확인
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        Log.d("home_accessToken::", sharedPreferences.getString("accessToken", ""));
        Log.d("home_refreshToken::", sharedPreferences.getString("refreshToken", ""));

        return homeBinding.getRoot();
    }

    // 달력에 감정 얼굴 띄우기
    private void getEmotions() {
        GetEmotionsService getEmotionsService = new GetEmotionsService();
        getEmotionsService.setGetEmotionsResult(this);
        getEmotionsService.getEmotions(dateFormat);
    }

    @Override
    public void getEmotionsSuccess(int code, ArrayList<GetEmotionsResponse.Result> result) {
        Log.d("달별 감정분석 결과 가져오기", "성공");

        Set<CalendarDay> happyDates = new HashSet<>();
        Set<CalendarDay> smileDates = new HashSet<>();
        Set<CalendarDay> notBadDates = new HashSet<>();
        Set<CalendarDay> sadDates = new HashSet<>();
        Set<CalendarDay> upsetDates = new HashSet<>();

        for (int i = 0; i < result.size(); i++) {
//            Log.d("=========== keyword : ", result.get(i).getKeyWord());
//            Log.d("=========== date : ", result.get(i).getCreatedDate());

            String dateString = result.get(i).getCreatedDate();
            String year = dateString.substring(0, 4);
            String month = (dateString.charAt(5) == '0' ? dateString.substring(6, 7) : dateString.substring(5, 7));
            String day = (dateString.charAt(8) == '0' ? dateString.substring(9) : dateString.substring(8));

            switch (result.get(i).getKeyWord()) {
                case "happy" :
                    happyDates.add(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
                    break;
                case "smile" :
                    smileDates.add(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
                    break;
                case "not bad" :
                    notBadDates.add(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
                    break;
                case "sad" :
                    sadDates.add(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
                    break;
                case "upset" :
                    upsetDates.add(CalendarDay.from(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
                    break;
            }
        }

        Drawable happy = ContextCompat.getDrawable(context, R.drawable.happy);
        Drawable smile = ContextCompat.getDrawable(context, R.drawable.smile);
        Drawable notBad = ContextCompat.getDrawable(context, R.drawable.notbad);
        Drawable sad = ContextCompat.getDrawable(context, R.drawable.sad);
        Drawable upset = ContextCompat.getDrawable(context, R.drawable.upset);

        calendarView.addDecorator(new ImageDecorator(happy, happyDates));
        calendarView.addDecorator(new ImageDecorator(smile, smileDates));
        calendarView.addDecorator(new ImageDecorator(notBad, notBadDates));
        calendarView.addDecorator(new ImageDecorator(sad, sadDates));
        calendarView.addDecorator(new ImageDecorator(upset, upsetDates));
    }

    @Override
    public void getEmotionsFailure(int code, String message) {
        Log.d("failure : ", "감정 분석 결과들 불러오기 실패");
    }

    // 하단 화면 장소 띄우기
    protected void todayRouteList() {
        GetEmotionService getEmotionService = new GetEmotionService();
        getEmotionService.setGetEmotionResult(this);
        getEmotionService.getEmotion(selectedDate);
    }

    // 오늘의 루트 전체 조회 성공 -> 다이어리 장소 전체 화면에 출력
    @Override
    public void getEmotionSuccess(int code, ArrayList<GetEmotionResponse.Result> result) {
        Log.d(TAG, "오늘의 장소 전체 조회 성공");

        // TO DO: 반복문 돌려서 하나씩 꺼내 화면에 출력
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ArrayList<DiaryItem> diaryList = (ArrayList<DiaryItem>) result.stream()
                    .map(r -> new DiaryItem(r.getDiaryId(), r.getLatitude(), r.getLongitude(), r.getEmotion(), r.getKeyWord(), r.getPlace()))
                    .collect(Collectors.toList());

            if(diaryList.size() > 0) {
                homeBinding.tvStart.setText(diaryList.get(0).getPlace());
                homeBinding.tvEnd.setText(diaryList.get(diaryList.size() - 1).getPlace());
            }
        }
    }

    // 오늘의 루트 전체 조회 실패
    @Override
    public void getEmotionFailure(int code, String message) {
        Log.d(TAG, "오늘의 장소 전체 조회 실패");
    }

    @Override
    public void onResume() {
        super.onResume();
        getEmotions();
    }
}