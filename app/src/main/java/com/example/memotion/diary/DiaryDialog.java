package com.example.memotion.diary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.memotion.R;
import com.example.memotion.databinding.DialogDiaryBinding;
import com.example.memotion.search.get.SearchGetResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DiaryDialog extends Dialog {
    private String TAG = "DiaryDialog";
    final static int PERMISSION_REQ_CODE = 200;

    private Context context;
    private DialogDiaryBinding diaryBinding;
    private SearchGetResponse.Result result;
    private Marker mCenterMarker;
    private GoogleMap mGoogleMap;

    private boolean mLastShare = true;

    public DiaryDialog(@NonNull Context context, SearchGetResponse.Result result) {
        super(context);
        this.context = context;
        this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryBinding = DialogDiaryBinding.inflate(getLayoutInflater());
        setContentView(diaryBinding.getRoot());

        // 권한 확인 후 mapLoad()
        checkPermission();
        mapLoad();

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        String emotion = result.getEmotion();
        Log.d(TAG, "emotion: " + emotion);

        if(emotion.equals("happy"))
            diaryBinding.searchBtnHappy.setVisibility(View.VISIBLE);
        else if(emotion.equals("smile"))
            diaryBinding.searchBtnSmile.setVisibility(View.VISIBLE);
        else if(emotion.equals("sad"))
            diaryBinding.searchBtnSad.setVisibility(View.VISIBLE);
        else if(emotion.equals("upset"))
            diaryBinding.searchBtnUpset.setVisibility(View.VISIBLE);
        else if(emotion.equals("notbad"))
            diaryBinding.searchBtnNotbad.setVisibility(View.VISIBLE);

        diaryBinding.getKeyword.setText(result.getKeyWord());
        diaryBinding.gpsGetText.setText(result.getCreatedDate() + " 작성 다이어리");
        diaryBinding.getGpsPlaceName.setText(result.getPlace());

        if(result.isShare() != true)
            diaryBinding.btnShare.setBackgroundDrawable(context.getDrawable(R.drawable.share_not_ok));

        // 닫기 버튼 클릭
        diaryBinding.searchBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "닫기 버튼 클릭");
                dismiss();
            }
        });
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

    @Override
    public void dismiss() {
        super.dismiss();

        // 여기에서 지도 초기화 및 리소스 해제 작업을 수행
        if (mGoogleMap != null) {
            mGoogleMap.clear(); // 마커 제거 등
            mGoogleMap = null; // 지도 객체 해제
        }
    }

    private void mapLoad() {
        // SupportMapFragment를 찾음
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity) context).getSupportFragmentManager()
                    .findFragmentById(R.id.get_gpsMap);
        mapFragment.getMapAsync(mapReadyCallback); // map 정보 가져오기 (Callback 호출)
    }

    // getMapAsync의 매개변수로 전달
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback () {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            try {
                checkPermission();

                mGoogleMap = googleMap;
                mGoogleMap.setMyLocationEnabled(true);

                // 지도 초기 위치 이동
                LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());
                // 지정한 위치로 애니메이션 이동
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // 지도 중심 마커 추가
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng) // LatLng 객체
                        .title("현재 위치") // infowindow : 터치 시 뜨는 정보
                        .snippet("이동중") // infowindow : 터치 시 뜨는 정보
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                // 지도에 마커 추가 후 추가한 마커 정보 기록
                mCenterMarker = googleMap.addMarker(markerOptions); // addMarker : 구글맵 마커 만들어줘~ 요청
                mCenterMarker.showInfoWindow();

                executeGeocoding(latLng);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void executeGeocoding(LatLng latLng) {
        if(Geocoder.isPresent() && latLng != null)
            new DiaryDialog.GeoTask().execute(latLng);
    }

    class GeoTask extends AsyncTask<LatLng, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        @Override
        protected List<Address> doInBackground(LatLng...latLngs) {
            List<Address> address = null;

            try{
                address = geocoder.getFromLocation(latLngs[0].latitude, latLngs[0].longitude, 1);
            } catch(IOException e){
                e.printStackTrace();
            }
            return address;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses != null) {
                Address address = addresses.get(0);
                String markerAddress = address.getAddressLine (0);

                diaryBinding.getGpsPlaceFullName.setText(markerAddress);
            }
        }
    }
}
