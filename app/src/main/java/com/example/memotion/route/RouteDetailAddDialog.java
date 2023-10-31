package com.example.memotion.route;

import static com.example.memotion.route.RouteAddMyActivity.PERMISSION_REQ_CODE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.memotion.R;
import com.example.memotion.databinding.DialogRouteDetailAddBinding;
import com.example.memotion.route.get.routedetail.GetRouteDetailResponse;
import com.example.memotion.route.get.routedetail.GetRouteDetailResult;
import com.example.memotion.route.get.routedetail.GetRouteDetailService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RouteDetailAddDialog extends Dialog implements GetRouteDetailResult {
    private String TAG = "RouteWriteDialog";
    private Context context;
    private DialogRouteDetailAddBinding routeDetailAddBinding;
    public Long routeDetailId;

    private GoogleMap mGoogleMap;    // 지도 객체
    private Marker mCenterMarker;
    private Double latitude;
    private Double longitude;

    public RouteDetailAddDialog(@NonNull Context context, Long routeDetailId) {
        super(context);
        this.context = context;
        this.routeDetailId = routeDetailId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeDetailAddBinding = DialogRouteDetailAddBinding.inflate(getLayoutInflater());
        setContentView(routeDetailAddBinding.getRoot());

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        // 닫기 버튼 클릭 시
        routeDetailAddBinding.rdaBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // 루트 상세 조회 API 호출
        getRouteDetail(routeDetailId);
    }

    // 루트 상세 조회 API
    private void getRouteDetail(Long routeDetailId) {
        Log.d(TAG, "GET-ROUTE-DETAIL-API 호출");
        GetRouteDetailService getRouteDetailService = new GetRouteDetailService();
        getRouteDetailService.setGetRouteDetailResult(this);
        getRouteDetailService.getRouteDetail(routeDetailId);
    }

    // 루트 상세 조회 성공
    @Override
    public void getRouteDetailSuccess(int code, GetRouteDetailResponse.Result result) {
        Log.d(TAG, "루트 상세 조회 성공");

        try {
            latitude = result.getLatitude();
            longitude = result.getLongitude();

            // 권한 확인 후 mapLoad()
            checkPermission();
            mapLoad();

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date startDate = dateFormat.parse(result.getStart_time());
            Date endDate = dateFormat.parse(result.getEnd_time());

            // Date 객체에서 시와 분을 추출
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

            String startHour = hourFormat.format(startDate);
            String startMinute = minuteFormat.format(startDate);
            String endHour = hourFormat.format(endDate);
            String endMinute = minuteFormat.format(endDate);
            String[] selectDate = result.getSelect_date().split("-");

            routeDetailAddBinding.rdaStartHour.setText(startHour);
            routeDetailAddBinding.rdaStartMinute.setText(startMinute);
            routeDetailAddBinding.rdaEndHour.setText(endHour);
            routeDetailAddBinding.rdaEndMinute.setText(endMinute);
            routeDetailAddBinding.rdaRouteTitle.setText(result.getTitle());
            routeDetailAddBinding.rdaSelectDate.setText(selectDate[0] + "년 " + selectDate[1] + "월 " + selectDate[2]);
            routeDetailAddBinding.rdaMemo.setText(result.getContent());
            routeDetailAddBinding.rdaGpsPlaceFullName.setText(result.getPlace());

            Glide.with(getContext())
                    .load(result.getUrl()) // 이미지 URL
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE) // 캐시 사용 안 함 (선택 사항)
                            .skipMemoryCache(true) // 메모리 캐시 사용 안 함 (선택 사항)
                    )
                    .into(routeDetailAddBinding.rdaImage);

            if(result.getUrl() != null)
                routeDetailAddBinding.rdaImageBtnCamera.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 루트 상세 조회 실패
    @Override
    public void getRouteDetailFailure(int code, String message) {
        Log.d(TAG, "루트 상세 조회 실패");
        Toast.makeText(context, "루트 상세 조회 실패", Toast.LENGTH_SHORT).show();
    }

    // 권한 체크
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // 권한이 있을 경우 수행할 동작
            Log.d(TAG, "권한 획득");
            Toast.makeText (context, "Permissions Granted", Toast.LENGTH_SHORT).show ();
        } else {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQ_CODE);
            Log.d(TAG, "권한 획득 실패 -> 권한 요청");
        }
    }

    private void mapLoad() {
        Log.d(TAG, "mapLoad 시작");

        // Google Maps 초기화
        MapsInitializer.initialize(context);
        // SupportMapFragment를 찾음
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity) context).getSupportFragmentManager()
                .findFragmentById(R.id.rda_gpsMap);
        mapFragment.getMapAsync (mapReadyCallback); // map 정보 가져오기 (Callback 호출)
    }

    // getMapAsync의 매개변수로 전달
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback () {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap;
            checkPermission();
            mGoogleMap.setMyLocationEnabled(true);

            // 지도 초기 위치 이동
            LatLng latLng = new LatLng (latitude, longitude);
            // 지정한 위치로 애니메이션 이동
            mGoogleMap.animateCamera (CameraUpdateFactory.newLatLngZoom (latLng, 15));

            // 지도 중심 마커 추가
            MarkerOptions markerOptions = new MarkerOptions ()
                    .position (latLng) // LatLng 객체
                    .title ("현재 위치") // infowindow : 터치 시 뜨는 정보
                    .snippet ("이동중") // infowindow : 터치 시 뜨는 정보
                    .icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_BLUE));

            // 지도에 마커 추가 후 추가한 마커 정보 기록
            mCenterMarker = mGoogleMap.addMarker(markerOptions); // addMarker : 구글맵 마커 만들어줘~ 요청
            mCenterMarker.showInfoWindow();
        }
    };
}
