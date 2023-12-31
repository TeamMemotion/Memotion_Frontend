package com.example.memotion.diary;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.memotion.R;
import com.example.memotion.diary.post.emotion.PostEmotionRequest;
import com.example.memotion.diary.post.emotion.PostEmotionResult;
import com.example.memotion.diary.post.emotion.PostEmotionService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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

// 23.08.24 : 지도 API 추가
public class PlaceAddDialog extends Dialog implements PostEmotionResult {
    final String TAG = "PlaceAddDialog";
    final static int PERMISSION_REQ_CODE = 100;

    private Context context;
    private Dialog dialog;
    private FusedLocationProviderClient flpClient;
    private Location mLastLocation;
    private GoogleMap mGoogleMap;    // 지도 객체
    private Marker mCenterMarker;

    private Double mLat = 360.0;    // 위도 초기값
    private Double mLng = 360.0;    // 경도 초기값
    private String mLastEmotion;
    private String inputLocation = "장소 이름";
    private boolean mLastShare = true;

    private DialogListener dialogListener;

    public interface DialogListener {
        void onDialogResult(String result);
    }

    public PlaceAddDialog(@NonNull Context context, DialogListener listener) {
        super(context);
        this.context = context;
        dialogListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // 검색 버튼
        dialog.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = dialog.findViewById(R.id.gps_editText);   // 검색 위치
                inputLocation = editText.getText().toString();

                // reverseGeocoding -> 위도 경도 추출 + 현재 위치 변경
                if(inputLocation.getBytes().length > 0)
                    executeReverseGeocoding(inputLocation);
                else
                    Toast.makeText(context, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.btnHappy).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mLastEmotion = "happy";
                dialog.findViewById(R.id.btnHappy).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.btnSmile).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnNotbad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnUpset).setVisibility(View.INVISIBLE);
            }
        });

        dialog.findViewById(R.id.btnSmile).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mLastEmotion = "smile";
                dialog.findViewById(R.id.btnHappy).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSmile).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.btnNotbad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnUpset).setVisibility(View.INVISIBLE);
            }
        });

        dialog.findViewById(R.id.btnNotbad).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mLastEmotion = "not bad";
                dialog.findViewById(R.id.btnHappy).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSmile).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnNotbad).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.btnSad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnUpset).setVisibility(View.INVISIBLE);
            }
        });

        dialog.findViewById(R.id.btnSad).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mLastEmotion = "sad";
                dialog.findViewById(R.id.btnHappy).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSmile).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnNotbad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSad).setVisibility(View.VISIBLE);
                dialog.findViewById(R.id.btnUpset).setVisibility(View.INVISIBLE);
            }
        });

        dialog.findViewById(R.id.btnUpset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLastEmotion = "upset";
                dialog.findViewById(R.id.btnHappy).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSmile).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnNotbad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnSad).setVisibility(View.INVISIBLE);
                dialog.findViewById(R.id.btnUpset).setVisibility(View.VISIBLE);
            }
        });

        dialog.findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(mLastShare == true) {
                    mLastShare = false;
                    dialog.findViewById(R.id.btnShare).setBackgroundResource(R.drawable.share_not_ok);
                } else {
                    mLastShare = true;
                    dialog.findViewById(R.id.btnShare).setBackgroundResource(R.drawable.share_ok);
                }
            }
        });

        // 저장 버튼
        dialog.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "다이어리 저장 시작");
                String createdDate = DiaryActivity.date; // 선택한 날짜
                EditText keyWord = dialog.findViewById(R.id.keyword); // 감정

                postEmotion(createdDate, keyWord.getText().toString());
            }
        });

        // 권한 확인 후 mapLoad()
        checkPermission();
        mapLoad();

        flpClient = LocationServices.getFusedLocationProviderClient(context);
        flpClient.requestLocationUpdates (
                getLocationRequest(),
                mLocCallback,
                Looper.getMainLooper()
        );

        dialog.show();
    }

    // 다이어리 저장 API 호출
    private void postEmotion(String createdDate, String keyWord) {
        Log.d(TAG, "API 호출");
        PostEmotionService postEmotionService = new PostEmotionService();
        postEmotionService.setPostEmotionResult(this);
        postEmotionService.postEmotion(new PostEmotionRequest(mLastLocation.getLatitude(), mLastLocation.getLongitude(), mLastEmotion, keyWord, inputLocation, createdDate, mLastShare));
    }

    @Override
    public void postEmotionSuccess(int code, Long result) {
        Log.d(TAG, "다이어리 저장 성공");

        // 성공 시 DiaryActivity로 이동 후 -> 저장한 날짜 + diaryId로 화면 출력 다시하기
        if(dialogListener != null) {
            dialogListener.onDialogResult(String.valueOf(result));
        }

        dialog.dismiss();
    }

    @Override
    public void postEmotionFailure(int code, String message) {
        Log.d(TAG, "다이어리 저장 실패");
        Toast.makeText(context, "다이어리 저장 실패", Toast.LENGTH_SHORT).show();
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
        // Google Maps 초기화
        MapsInitializer.initialize(context);
        // SupportMapFragment를 찾음
        SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity) context).getSupportFragmentManager()
                .findFragmentById(R.id.gpsMap);
        mapFragment.getMapAsync (mapReadyCallback); // map 정보 가져오기 (Callback 호출)
    }

    // getMapAsync의 매개변수로 전달
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback () {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap;
            checkPermission();
            mGoogleMap.setMyLocationEnabled(true);

            // 지도 초기 위치 이동 (초기위치  : 동덕여자대학교)
            LatLng latLng = new LatLng (37.606320, 127.041808);
            // 지정한 위치로 애니메이션 이동
            mGoogleMap.animateCamera (CameraUpdateFactory.newLatLngZoom (latLng, 15));

            // 지도 중심 마커 추가
            MarkerOptions markerOptions = new MarkerOptions ()
                    .position (latLng) // LatLng 객체
                    .title ("현재 위치") // infowindow : 터치 시 뜨는 정보
                    .snippet ("이동중") // infowindow : 터치 시 뜨는 정보
                    .icon (BitmapDescriptorFactory.defaultMarker (BitmapDescriptorFactory.HUE_BLUE));

            // 지도에 마커 추가 후 추가한 마커 정보 기록
            mCenterMarker = mGoogleMap.addMarker (markerOptions); // addMarker : 구글맵 마커 만들어줘~ 요청
            mCenterMarker.showInfoWindow ();
        }
    };

    LocationCallback mLocCallback = new LocationCallback () {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            // 검색값이 있는 경우 현재 위치로 animateCamera 실행 X
            if(mLat == 360.0 && mLng == 360.0) {
                for (Location loc : locationResult.getLocations ()) {
                    double lat = loc.getLatitude ();
                    double lng = loc.getLongitude ();

                    // 지도 위치 이동 (가장 최근 위치 기록)
                    mLastLocation = loc;
                    LatLng currentLoc = new LatLng (lat, lng);

                    // 지정한 위치로 애니메이션 이동
                    if(mGoogleMap != null)
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom (currentLoc, 15));
                    mCenterMarker.setPosition(currentLoc);

                    // 위도 경도로 주소 획득
                    executeGeocoding(currentLoc);
                }
            }
        }
    };

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest ();
        locationRequest.setInterval (5000);
        locationRequest.setFastestInterval (1000);
        locationRequest.setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void executeGeocoding(LatLng latLng) {
        if(Geocoder.isPresent() && latLng != null)
            new GeoTask().execute(latLng);
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

                TextView gpsPlaceFullName = dialog.findViewById(R.id.rt_gpsPlaceFullName);
                gpsPlaceFullName.setText(markerAddress);

                TextView gpsPlaceName = dialog.findViewById(R.id.rt_gpsPlaceName);
                if(inputLocation.length() >= 15)
                    gpsPlaceName.setText(inputLocation.substring(0, 14) + "...");
                else
                    gpsPlaceName.setText(inputLocation);
            }
        }
    }

    private void executeReverseGeocoding(String str) {
        if(Geocoder.isPresent() && str != null)
            new ReverseGeoTask().execute(str);
    }

    class ReverseGeoTask extends AsyncTask<String, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        @Override
        protected List<Address> doInBackground(String... strs) {
            List<Address> addresses = null;

            try{
                addresses = geocoder.getFromLocationName(strs[0], 1);
            } catch(IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses != null) {
                if (addresses.size () == 0) { 
                    // 정확한 위치명을 입력하지 않은 경우
                    Toast.makeText (context, "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show ();
                } else {
                    Address address = addresses.get (0);
                    mLat = address.getLatitude ();
                    mLng = address.getLongitude ();

                    mLastLocation.setLatitude(mLat);
                    mLastLocation.setLongitude(mLng);

                    if (mLat != 360.0 && mLng != 360.0) {
                        // 지도 현재 위치 변경
                        mGoogleMap.animateCamera (CameraUpdateFactory.newLatLngZoom (new LatLng (mLat, mLng), 15));
                        
                        // 장소 검색 주소 변경
                        String markerAddress = address.getAddressLine (0);
                        TextView gpsPlaceFullName = dialog.findViewById(R.id.rt_gpsPlaceFullName);
                        gpsPlaceFullName.setText(markerAddress);

                        TextView gpsPlaceName = dialog.findViewById(R.id.rt_gpsPlaceName);
                        if(inputLocation.length() >= 15)
                            gpsPlaceName.setText(inputLocation.substring(0, 14) + "...");
                        else
                            gpsPlaceName.setText(inputLocation);

                        // 마커 위치 변경
                        mCenterMarker.setPosition(new LatLng(mLat, mLng));
                    } else
                        Toast.makeText (context, "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show ();
                }
            }
        }
    }
}
