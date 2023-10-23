package com.example.memotion.route;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.memotion.R;
import com.example.memotion.databinding.DialogRouteWriteBinding;
import com.example.memotion.route.post.route.PostRouteRequest;
import com.example.memotion.route.post.route.PostRouteResult;
import com.example.memotion.route.post.route.PostRouteService;

public class RouteWriteDialog extends Dialog implements PostRouteResult {
    private String TAG = "RouteWriteDialog";
    private Context context;
    DialogRouteWriteBinding routeWriteBinding;

    public RouteWriteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeWriteBinding = DialogRouteWriteBinding.inflate(getLayoutInflater());
        setContentView(routeWriteBinding.getRoot());

        // 닫기 버튼 클릭 시
        routeWriteBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 저장 버튼 클릭 시
        routeWriteBinding.btnSaveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String routeTitle = routeWriteBinding.addRouteTitle.getText().toString();
                String etPeriod = routeWriteBinding.etPeriod.getText().toString();
                String startYear = routeWriteBinding.startYear.getText().toString();
                String startMonth = routeWriteBinding.startMonth.getText().toString();
                String startDay = routeWriteBinding.startDate.getText().toString();
                String endYear = routeWriteBinding.endYear.getText().toString();
                String endMonth = routeWriteBinding.endMonth.getText().toString();
                String endDay = routeWriteBinding.endDate.getText().toString();

                if(routeTitle == null)
                    Toast.makeText(context, "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if (etPeriod == null)
                    Toast.makeText(context, "기간을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if(startYear == null || startMonth == null || startDay == null)
                    Toast.makeText(context, "시작일을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if(endYear == null || endMonth == null || endDay == null)
                    Toast.makeText(context, "종료일을 입력하세요.", Toast.LENGTH_SHORT).show();
                else{
                    String startDate = startYear + "-" + startMonth + "-" + startDay;
                    String endDate = endYear + "-" + endMonth + "-" + endDay;

                    // 루트 추가 API 호출
                    postRoute(routeTitle, startDate, endDate);
                }
            }
        });
    }

    // 루트 추가 API
    private void postRoute(String title, String startDate, String endDate) {
        Log.d(TAG, "POST-ROUTE-API 호출");
        PostRouteService postRouteService = new PostRouteService();
        postRouteService.setPostRouteResult(this);
        postRouteService.postRoute(new PostRouteRequest(title, startDate, endDate));
    }

    // 루트 추가 성공
    @Override
    public void postRouteSuccess(int code, Long result) {
        Log.d(TAG, "루트 저장 성공");

        // TO DO. 화면 이동 코드 추가
        Intent intent = new Intent(getContext(), RouteActivity.class);
        intent.putExtra("routeId", result); // routeId를 전달
        getContext().startActivity(intent);
    }

    @Override
    public void postRouteFailure(int code, String message) {
        Log.d(TAG, "루트 저장 실패");
        Toast.makeText(context, "루트 저장 실패", Toast.LENGTH_SHORT).show();
    }
}
