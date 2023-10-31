package com.example.memotion.route;

import static com.example.memotion.route.RouteDateRecyclerAdapter.selectedDate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityRouteBinding;
import com.example.memotion.route.get.route.GetRouteResponse;
import com.example.memotion.route.get.route.GetRouteResult;
import com.example.memotion.route.get.route.GetRouteService;
import com.example.memotion.route.get.routedetailList.GetRouteDetailListResponse;
import com.example.memotion.route.get.routedetailList.GetRouteDetailListResult;
import com.example.memotion.route.get.routedetailList.GetRouteDetailListService;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RouteActivity extends AppCompatActivity implements GetRouteResult, GetRouteDetailListResult {
    private static String TAG = "RouteActivity";

    private RouteDateRecyclerAdapter dateRecyclerAdapter;
    private ActivityRouteBinding routeBinding;
    private RouteRecyclerAdapter routeAdapter;

    private List<Date> dateList = new ArrayList<>();
    private Long routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeBinding = ActivityRouteBinding.inflate(getLayoutInflater());
        setContentView(routeBinding.getRoot());

        Intent intent = getIntent();
        routeId = intent.getExtras().getLong("routeId");

        // 루트 조회 호출 + 루트 조회 성공 시 dateRecyclerAdapter에 Date 데이터 연결
        getRoute(routeId);

        // 여행 기간 가로로 나열하는 RecyclerView 연결
        dateRecyclerAdapter = new RouteDateRecyclerAdapter(this);
        dateRecyclerAdapter.setSelectedPosition(0);

        // 루트 상세 조회하는 RecyclerView 연결
        routeAdapter = new RouteRecyclerAdapter(this);

        // + 버튼 클릭 시
        routeBinding.routeDetailAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "+ 버튼 클릭 시 동작");
                    Date date = selectedDate;
                    Log.d(TAG, "선택일: " + date.toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
                    String selectDate = sdf.format(date);

                    Intent intent = new Intent(RouteActivity.this, RouteAddMyActivity.class);
                    intent.putExtra("selectDate", selectDate);
                    intent.putExtra("routeId", routeId);
                    startActivity(intent);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
    }

    private void initActionBar() {
        routeBinding.actionRoute.appbarPageNameLeftTv.setText("루트 상세 기록");
        routeBinding.actionRoute.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
            routeBinding.dateView.setAdapter(dateRecyclerAdapter);
            routeBinding.dateView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

            // 여행 시작 첫째날 기준으로 루트 상세 리스트 조회 API 호출
            getRouteDetailList(routeId, result.getStartDate());

            // 날짜 클릭 시 선택된 날짜 기준으로 루트 상세 조회 API 호출출
           dateRecyclerAdapter.setItemClickListener(new RouteDateRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Date date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(date);
                    getRouteDetailList(routeId, formattedDate);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 루트 조회 실패
    @Override
    public void getRouteFailure(int code, String message) {
        Log.d(TAG, "루트 상세 리스트 조회 실패");
    }

    // 루트 디테일 리스트 조회 호출
    public void getRouteDetailList(Long routeDetailId, String selectDate) {
        Log.d(TAG, "GET-ROUTE-DETAIL-LIST-API 호출");
        GetRouteDetailListService getRouteDetailListService = new GetRouteDetailListService();
        getRouteDetailListService.setGetRouteDetailListResult(this);
        getRouteDetailListService.getRouteDetailList(routeDetailId, selectDate);
    }

    // 루트 디테일 리스트 조회 성공
    @Override
    public void getRouteDetailListSuccess(int code, GetRouteDetailListResponse.Result result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result.getRouteDetailResult().stream()
                            .map(r -> {
                                return Log.d(TAG, r.getContent());
                            }).collect(Collectors.toList());
        }

        routeAdapter.setRouteDetailListItems(result.getRouteDetailResult());
        routeBinding.routeDetailView.setAdapter(routeAdapter);
        routeBinding.routeDetailView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // 클릭 이벤트 발생 시 루트 디테일 상세 조회
        routeAdapter.setItemClickListener(new RouteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GetRouteDetailListResponse.Result.RouteDetailResult item) {
//                RouteDetailAddDialog dialog = new RouteDetailAddDialog(RouteActivity.this, item.getRouteId());
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 제목 표시 비활성화
//                dialog.setContentView(R.layout.dialog_route_detail_add); // 다이얼로그 레이아웃 설정
//                dialog.show();
                Log.d(TAG, "루트 상세 조회 시도==" + item.getRecordDetailId());
                Intent intent = new Intent(RouteActivity.this, RouteAddMyActivity.class);
                intent.putExtra("routeDetailId", item.getRecordDetailId());
                startActivity(intent);
            }
        });
    }

    // 루트 디테일 리스트 조회 실패
    @Override
    public void getRouteDetailListFailure(int code, String message) {
        Log.d(TAG, "GET-ROUTE-DETAIL-LIST-FAIL");
    }
}
