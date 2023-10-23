package com.example.memotion.route;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.MainActivity;
import com.example.memotion.R;
import com.example.memotion.databinding.FragmentRouteBinding;
import com.example.memotion.local.LocalGuideActivity;
import com.example.memotion.mypage.get.mydiary.GetDiaryEmotionResponse;
import com.example.memotion.route.get.localGuide.LocalGuideGetResponse;
import com.example.memotion.route.get.localGuide.LocalGuideGetResult;
import com.example.memotion.route.get.localGuide.LocalGuideGetService;

import java.util.ArrayList;

public class RouteFragment extends Fragment implements LocalGuideGetResult {

    FragmentRouteBinding routeBinding;
    private static String TAG = "RouteFragment";
    private ArrayList<LocalGuideGetResponse.Result> localGuideList = new ArrayList<>();

    private LocalGuideAdapter localGuideAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routeBinding = FragmentRouteBinding.inflate(inflater, container, false);

        localGuideAdapter = new LocalGuideAdapter(this);

        routeBinding.btnToLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LocalGuideActivity.class);
                startActivity(intent);
            }
        });

        // + 버튼 클릭 시 루트 기록 추가
        routeBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteWriteDialog dialog = new RouteWriteDialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 제목 표시 비활성화
                dialog.setContentView(R.layout.dialog_route_write); // 다이얼로그 레이아웃 설정
                dialog.show();
            }
        });

        return routeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLocalGuide();
    }

    // 로컬 가이드 조회
    private void getLocalGuide() {
        LocalGuideGetService getLocalGuideService = new LocalGuideGetService();
        getLocalGuideService.setLocalGuideGetResult(this);
        getLocalGuideService.getLocalGuide();
    }

    @Override
    public void getLocalGuideSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result) {
        Log.d(TAG, "로컬가이드 조회 성공");
        int size;
        if(result.size() < 8)
            size = result.size();
        else
            size = 8;

        for(int i = 0; i < size; i++) {
            localGuideList.add(result.get(i));
            Log.d("routeId: ", localGuideList.get(i).getRouteId().toString());
            if(localGuideList.get(i).getRouteImg() != null)
                Log.d("routeImage: ", localGuideList.get(i).getRouteImg());
            Log.d("username: ", localGuideList.get(i).getUsername());
        }
        initRecycler(result);
    }

    @Override
    public void getLocalGuideFailure(int code, String message) {
        Log.d(TAG, "로컬가이드 조회 실패");
    }

    // adapter 연결
    private void initRecycler(ArrayList<LocalGuideGetResponse.Result> result) {
        localGuideAdapter.setLocalGuideList(result);
        routeBinding.recyclerView2.setAdapter(localGuideAdapter);
        routeBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        localGuideAdapter.setItemClickListener(new LocalGuideAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LocalGuideGetResponse.Result result) {
                Intent intent = new Intent(getContext(), RouteActivity.class);

            }
        });
    }

    // 내가 쓴 루트 기록 조회
}
