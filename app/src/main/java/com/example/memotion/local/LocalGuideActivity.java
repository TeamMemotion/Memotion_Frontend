package com.example.memotion.local;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.databinding.ActivityLocalGuideBinding;
import com.example.memotion.route.LocalGuideAdapter;
import com.example.memotion.route.RouteActivity;
import com.example.memotion.route.RouteAdapter;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResult;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetService;

import java.util.ArrayList;


public class LocalGuideActivity extends AppCompatActivity implements LocalGuideGetResult {
    ActivityLocalGuideBinding localGuideBinding;
    private static String TAG = "LocalGuideActivity";
    private LocalRouteAdapter localRouteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localGuideBinding = ActivityLocalGuideBinding.inflate(getLayoutInflater());
        setContentView(localGuideBinding.getRoot());

        localRouteAdapter = new LocalRouteAdapter(this);

        localGuideBinding.searchLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                startActivity(intent);
            }
        });

        localGuideBinding.txSeoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "서울");
                startActivity(intent);
            }
        });

        localGuideBinding.txBusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "부산");
                startActivity(intent);
            }
        });

        localGuideBinding.txJeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "제주");
                startActivity(intent);
            }
        });

        localGuideBinding.txGangneung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "강릉");
                startActivity(intent);
            }
        });

        localGuideBinding.txIncheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "인천");
                startActivity(intent);
            }
        });

        localGuideBinding.txGyeongju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "광주");
                startActivity(intent);
            }
        });

        localGuideBinding.txDaejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "대전");
                startActivity(intent);
            }
        });

        localGuideBinding.txJeonju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "전주");
                startActivity(intent);
            }
        });

        localGuideBinding.txYeosu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalGuideSearchActivity.class);
                intent.putExtra("name", "여수");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
        getLocalGuide();
    }

    private void initActionBar() {
        localGuideBinding.actionLocalguide.appbarPageNameLeftTv.setText("로컬 가이드");

        localGuideBinding.actionLocalguide.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 로컬 가이드 조회
    private void getLocalGuide() {
        LocalGuideGetService getLocalGuideService = new LocalGuideGetService();
        getLocalGuideService.setLocalGuideGetResult(this);
        getLocalGuideService.getLocalGuide();
    }

    @Override
    public void getLocalGuideSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result) {
        Log.d(TAG, "최신 루트 기록 조회 성공");

        for(int i = 0; i < result.size() - 1; i++) {
            Log.d("routeId: ", result.get(i).getRouteId().toString());
            if(result.get(i).getRouteImg() != null)
                Log.d("routeImage: ", result.get(i).getRouteImg());
            Log.d("username: ", result.get(i).getUsername());
        }
        initRecycler(result);
    }

    @Override
    public void getLocalGuideFailure(int code, String message) {
        Log.d(TAG, "최신 루트 기록 조회 실패");
    }

    // adapter 연결
    private void initRecycler(ArrayList<LocalGuideGetResponse.Result> result) {
        localRouteAdapter.setRouteList(result);
        localGuideBinding.latestRouteView.setAdapter(localRouteAdapter);
        localGuideBinding.latestRouteView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        localRouteAdapter.setItemClickListener(new LocalRouteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LocalGuideGetResponse.Result result) {
//                Intent intent = new Intent(getContext(), RouteActivity.class);
//                intent.putExtra("routeId", result.getRouteId());
//                startActivity(intent);
            }
        });
    }
}
