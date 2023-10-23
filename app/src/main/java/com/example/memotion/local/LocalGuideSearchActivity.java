package com.example.memotion.local;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.databinding.ActivityRouteSearchBinding;
import com.example.memotion.local.get.popular.PopularRouteGetResult;
import com.example.memotion.local.get.popular.PopularRouteGetService;
import com.example.memotion.local.get.search.SearchRouteGetResult;
import com.example.memotion.local.get.search.SearchRouteGetService;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalGuideSearchActivity extends AppCompatActivity implements PopularRouteGetResult, SearchRouteGetResult {

    ActivityRouteSearchBinding routeSearchBinding;
    private static String TAG = "LocalGuideSearchActivity";
    private LocalRouteAdapter localRouteAdapter;
    String location = "";

    private Double mLat = 0.0;    // 위도 초기값
    private Double mLng = 0.0;    // 경도 초기값
    private int state = 0;        // 인기 -> 0 검색 -> 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeSearchBinding = ActivityRouteSearchBinding.inflate(getLayoutInflater());
        setContentView(routeSearchBinding.getRoot());

        localRouteAdapter = new LocalRouteAdapter(this);

        Intent intent = getIntent();
        if(intent != null) {
            location = intent.getStringExtra("name");
            if(location != null) {
                state = 0;
                routeSearchBinding.searchLocal.setText(location);
                executeReverseGeocoding(location);
            }
        }

        routeSearchBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 1;
                String locat = String.valueOf(routeSearchBinding.searchLocal.getText());
                executeReverseGeocoding(locat);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
    }

    private void initActionBar() {
        routeSearchBinding.actionLocalguide.appbarPageNameLeftTv.setText("로컬 가이드 검색");

        routeSearchBinding.actionLocalguide.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 인기 지역 조회
    private void getPopularRoute(Double latitude, Double longitude) {
        PopularRouteGetService popularRouteGetService = new PopularRouteGetService();
        popularRouteGetService.setPopularRouteGetResult(this);
        popularRouteGetService.getPopularRoute(latitude, longitude);
    }

    @Override
    public void getPopularRouteSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result) {
        Log.d(TAG, "인기 지역 루트 조회 성공");

        for(int i = 0; i < result.size() - 1; i++) {
            Log.d("routeId: ", result.get(i).getRouteId().toString());
            if(result.get(i).getRouteImg() != null) {
                Log.d("routeImg: ", result.get(i).getRouteImg());
            }
            Log.d("startDate: ", result.get(i).getStartDate());
            Log.d("endDate: ", result.get(i).getEndDate());
            Log.d("routeName: ", result.get(i).getRouteName());
            Log.d("likeCount: ", result.get(i).getLikeCount().toString());
        }

        initRecycler(result);
    }

    @Override
    public void getPopularRouteFailure(int code, String message) {
        Log.d(TAG, "인기 지역 루트 조회 실패");
    }

    // 검색어로 조회
    private void getSearchRoute(Double latitude, Double longitude) {
        SearchRouteGetService getSearchRouteService = new SearchRouteGetService();
        getSearchRouteService.setSearchRouteGetResult(this);
        getSearchRouteService.getSearchRoute(latitude, longitude);
    }

    @Override
    public void getSearchRouteSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result) {
        Log.d(TAG, "검색 지역 루트 조회 성공");

        for(int i = 0; i < result.size() - 1; i++) {
            Log.d("routeId: ", result.get(i).getRouteId().toString());
            if(result.get(i).getRouteImg() != null) {
                Log.d("routeImg: ", result.get(i).getRouteImg());
            }
            Log.d("startDate: ", result.get(i).getStartDate());
            Log.d("endDate: ", result.get(i).getEndDate());
            Log.d("routeName: ", result.get(i).getRouteName());
            Log.d("likeCount: ", result.get(i).getLikeCount().toString());
        }

        initRecycler(result);
    }

    @Override
    public void getSearchRouteFailure(int code, String message) {
        Log.d(TAG, "검색 지역 루트 조회 실패");
    }

    // adapter 연결
    private void initRecycler(ArrayList<LocalGuideGetResponse.Result> result) {
        localRouteAdapter.setRouteList(result);
        routeSearchBinding.searchRouteList.setAdapter(localRouteAdapter);
        routeSearchBinding.searchRouteList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        localRouteAdapter.setItemClickListener(new LocalRouteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LocalGuideGetResponse.Result result) {
//                Intent intent = new Intent(getContext(), RouteActivity.class);
//                intent.putExtra("routeId", result.getRouteId());
//                startActivity(intent);
            }
        });
    }

    // 역지오코딩
    private void executeReverseGeocoding(String str) {
        if(Geocoder.isPresent() && str != null)
            new ReverseGeoTask().execute(str);
    }

    class ReverseGeoTask extends AsyncTask<String, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        @Override
        protected List<Address> doInBackground(String... strs) {
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocationName(strs[0], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses != null) {
                if (addresses.size() == 0) {
                    // 정확한 위치명을 입력하지 않은 경우
                    Toast.makeText(getApplicationContext(), "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    Address address = addresses.get(0);
                    mLat = address.getLatitude();
                    mLng = address.getLongitude();

                    if(state == 0) {
                        getPopularRoute(mLat, mLng);
                        routeSearchBinding.searchRoute.setText("\'" + location + "\' 루트 기록");
                    }
                    else if (state == 1) {
                        getSearchRoute(mLat, mLng);
                        String area = String.valueOf(routeSearchBinding.searchLocal.getText());
                        routeSearchBinding.searchRoute.setText("\'" + area + "\' 루트 기록");
                    }
                }
            }
        }
    }
}
