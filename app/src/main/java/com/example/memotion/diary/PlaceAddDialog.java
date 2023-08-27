package com.example.memotion.diary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.memotion.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// 23.08.24 : 지도 API 추가
public class PlaceAddDialog {
    final String TAG = "PlaceAddDialog";
    final static int PERMISSION_REQ_CODE = 100;

    private Context context;
    private Dialog dialog;

    public PlaceAddDialog(Context context) {
        this.context = context;
    }

    public void start() {
        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diary_write_dialog);
        dialog.setCanceledOnTouchOutside(true); //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dialog.setCancelable(true);    // 취소가 가능하도록 하는 코드

        // 닫기 버튼
        dialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        checkPermission();
        dialog.show();
    }

    // 권한 체크
    private void checkPermission() {
        // 권한 체크
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQ_CODE);
        } else {
            // 권한이 있는 경우 지도 초기화
            initializeMap();
        }
    }

    private void initializeMap() {
        // Google Maps 초기화
        MapsInitializer.initialize(context);

        // SupportMapFragment를 찾음
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity) context).getSupportFragmentManager()
                .findFragmentById(R.id.gpsMap);

        // 지도 뷰의 생명주기 관리
        if(mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    // 구글맵이 준비되면 실행되는 콜백
                    // 구글맵에 마커 추가 및 이동
                    if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);   // 내 위치 표시 활성화

                        // 권한이 있는 경우 내 위치 표시, 마커 추가 및 카메라 이동
                        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastKnownLocation != null) {
                            LatLng myLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(myLatLng).title("My Location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15));
                            Log.d(TAG, "권한 획득 : 지도 띄우기 성공");
                        }
                    } else{
                        // 권한이 없는 경우 권한 요청
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_REQ_CODE);
                        Log.d(TAG, "권한 없음 : 지도 띄우기 실패");
                    }
                }
            });
        }
    }
}
