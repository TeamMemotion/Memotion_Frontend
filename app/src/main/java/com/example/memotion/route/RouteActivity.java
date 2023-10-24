package com.example.memotion.route;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.databinding.ActivityRouteBinding;
import com.example.memotion.route.get.route.GetRouteResponse;
import com.example.memotion.route.get.route.GetRouteResult;
import com.example.memotion.route.get.route.GetRouteService;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements GetRouteResult {
    private String TAG = "RouteActivity";
    private RouteDetailItem routeDetailItem;
    private RouteDateRecyclerAdapter dateRecyclerAdapter;
    private ActivityRouteBinding routeBinding;
    private RouteRecyclerAdapter routeAdapter;
    private RecyclerView recyclerView;
    private List<Date> dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeBinding = ActivityRouteBinding.inflate(getLayoutInflater());
        setContentView(routeBinding.getRoot());

        Intent intent = getIntent();
        Long routeId = intent.getExtras().getLong("routeId");

        // 루트 조회 호출 + 루트 조회 성공 시 dateRecyclerAdapter에 Date 데이터 연결
        getRoute(routeId);

        // 여행 기간 가로로 나열하는 RecyclerView 연결
        dateRecyclerAdapter = new RouteDateRecyclerAdapter(this);
        routeBinding.dateView.setAdapter(dateRecyclerAdapter);
        routeBinding.dateView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        // 루트 상세 조회하는 RecyclerView 연결
        recyclerView = routeBinding.routeDetailView;
        routeAdapter = new RouteRecyclerAdapter(this);
        recyclerView.setAdapter(routeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // 루트 조회 호출
    public void getRoute(Long routeId) {
        Log.d(TAG, "GET-ROUTE-API 호출");
        GetRouteService getRouteService = new GetRouteService();
        getRouteService.setGetRouteResult(this);
        getRouteService.getRoute(routeId);
    }

    // 루트 조회 성공
    @Override
    public void getRouteSuccess(int code, GetRouteResponse.Result result) {
        Log.d(TAG, "루트 조회 성공");

        // "2023-10-12" -> "2023.10.12"
        String startDate = result.getStartDate().replace("-", ".");
        String endDate = result.getEndDate().replace("-", ".");
        routeBinding.routeStartDate.setText(startDate);
        routeBinding.routeEndDate.setText(endDate);
        routeBinding.likerouteCount.setText(result.getLikeCount().toString());
        routeBinding.textrouteTitle.setText(result.getName());

        // 루트 시작일 ~ 종료일을 Date 타입 리스트에 담기
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);

            while (!calendar.getTime().after(end)) {
                dateList.add(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
            }

            // Date 담기
            dateRecyclerAdapter.setDateList(dateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 루트 조회 실패
    @Override
    public void getRouteFailure(int code, String message) {
        Log.d(TAG, "루트 조회 실패");
    }
}
