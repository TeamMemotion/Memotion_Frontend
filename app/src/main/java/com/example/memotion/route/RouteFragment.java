package com.example.memotion.route;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.example.memotion.MainActivity;
import com.example.memotion.R;
import com.example.memotion.databinding.FragmentRouteBinding;
import com.example.memotion.local.LocalGuideActivity;

public class RouteFragment extends Fragment {

    FragmentRouteBinding routeBinding;
    private static String TAG = "RouteFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routeBinding = FragmentRouteBinding.inflate(inflater, container, false);

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
    }
}
