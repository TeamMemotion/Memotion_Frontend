package com.example.memotion.route;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityRouteAddMyBinding;
import com.example.memotion.route.get.routedetail.GetRouteDetailResponse;
import com.example.memotion.route.get.routedetail.GetRouteDetailResult;
import com.example.memotion.route.get.routedetail.GetRouteDetailService;
import com.example.memotion.route.patch.routedetail.PatchRouteDetailRequest;
import com.example.memotion.route.post.routedetail.PostRouteDetailRequest;
import com.example.memotion.route.post.routedetail.PostRouteDetailResponse;
import com.example.memotion.route.post.routedetail.PostRouteDetailResult;
import com.example.memotion.route.post.routedetail.PostRouteDetailService;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RouteAddMyActivity extends AppCompatActivity implements PostRouteDetailResult, GetRouteDetailResult {

    ActivityRouteAddMyBinding routeAddMyBinding;
    final String TAG = "RouteAddMyActivity";
    final static int PERMISSION_REQ_CODE = 100;

    private FusedLocationProviderClient flpClient;
    private Location mLastLocation;
    private GoogleMap mGoogleMap;    // 지도 객체
    private Marker mCenterMarker;

    private Double mLat = 360.0;    // 위도 초기값
    private Double mLng = 360.0;    // 경도 초기값
    private String mLastEmotion;
    private boolean mLastShare = true;
    private MultipartBody.Part body;
    private Long routeDetailId = Long.valueOf(-1);

    private Long routeId = Long.valueOf(-1);
//    private int filterState = -1;   // 0부터 시작
//    private String filterContent;
    private String date;

    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeAddMyBinding = ActivityRouteAddMyBinding.inflate(getLayoutInflater());
        setContentView(routeAddMyBinding.getRoot());

        // 스피너
        // 스피너 텍스트를 정하는 것 (최신순 오래된순)
//        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
//        routeAddMyBinding.spinnerBtn.setAdapter(adapter);

        // 생성인지 수정인지 조회
        Intent intent = getIntent();
        if(intent != null) {
            // intent로 routeId랑 날짜 받기
            date = intent.getStringExtra("selectDate");
            routeId = intent.getLongExtra("routeId", -1);

            if(date != null) {
//                if(routeId != -1) {
//                    // get api 상세 조회
//////                  // 저장된 날짜로 스피너 띄우기
//////                  // 받아온 날짜로 arraylist에 있는 형태로 타입 변환
//////                  int position = items.indexOf( );
//////                  if(position >= 0) {
//////                      routeAddMyBinding.spinnerBtn.setSelection(position);
//                }
                routeAddMyBinding.currentDate.setText(date);
                if(routeDetailId != -1) {
                    getRouteDetail();
                }
            }
        }
        //setUpSpinnerHandler();


        // 저장
        routeAddMyBinding.btnSaveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(routeDetailId == -1) {
                    // 생성
                    saveRouteDetail();
                } else {
                    // 수정
                }
            }
        });

        // 닫기 버튼
        routeAddMyBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 사진 넣기
        routeAddMyBinding.imageBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 갤러리 접근 권한이 있는 경우
                if (ContextCompat.checkSelfPermission(RouteAddMyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    navigateGallery();
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(RouteAddMyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) { // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
                    showPermissionContextPopup();
                } else { // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                    ActivityCompat.requestPermissions(RouteAddMyActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                }
            }
        });

        // 검색 버튼
        routeAddMyBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputLocation = routeAddMyBinding.placeAdd.toString();
                Log.d(TAG, "검색 위치: " + inputLocation);

                // reverseGeocoding -> 위도 경도 추출 + 현재 위치 변경
                if(inputLocation.getBytes().length > 0)
                    executeReverseGeocoding(inputLocation);
                else
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 권한 확인 후 mapLoad()
        checkPermission();
        mapLoad();

        flpClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        flpClient.requestLocationUpdates (
                getLocationRequest (),
                mLocCallback,
                Looper.getMainLooper ()
        );

        // editText 내용 입력시 버튼 활성화
        routeAddMyBinding.addRouteTitle.addTextChangedListener(textWatcher);
        routeAddMyBinding.startAmPm.addTextChangedListener(textWatcher);
        routeAddMyBinding.startHour.addTextChangedListener(textWatcher);
        routeAddMyBinding.startMinute.addTextChangedListener(textWatcher);
        routeAddMyBinding.endAmPm.addTextChangedListener(textWatcher);
        routeAddMyBinding.endHour.addTextChangedListener(textWatcher);
        routeAddMyBinding.endMinute.addTextChangedListener(textWatcher);
        routeAddMyBinding.placeAdd.addTextChangedListener(textWatcher);
    }

    // 저장 api
    private PostRouteDetailRequest saveRequest() {
        String content = routeAddMyBinding.writeMemo.getText().toString();
        String endTime = stringToTimestamp(routeAddMyBinding.endAmPm.getText().toString(), routeAddMyBinding.endHour.getText().toString(), routeAddMyBinding.endMinute.getText().toString());
        Double latitude = mLat;
        Double longitude = mLng;
        String place = routeAddMyBinding.placeAdd.getText().toString();
        String selectDate = routeAddMyBinding.currentDate.getText().toString();
        String startTime = stringToTimestamp(routeAddMyBinding.startAmPm.getText().toString(), routeAddMyBinding.startHour.getText().toString(), routeAddMyBinding.startMinute.getText().toString());
        String title = routeAddMyBinding.addRouteTitle.getText().toString();
        return new PostRouteDetailRequest(content, endTime, latitude, longitude, place, routeId, selectDate, startTime, title, body);
    }

    private void saveRouteDetail() {
        PostRouteDetailService postRouteDetailService = new PostRouteDetailService();
        postRouteDetailService.setPostRouteDetailResult(this);
        postRouteDetailService.postRouteDetail(saveRequest());
    }

    @Override
    public void postRouteDetailSuccess(int code, PostRouteDetailResponse.Result result) {
        Toast.makeText(getApplicationContext(), "저장을 성공하였습니다.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, result.getRouteId().toString());
        Log.d(TAG, result.getTitle());
        Log.d(TAG, result.getSelectDate());
        Log.d(TAG, result.getStartTime());
        Log.d(TAG, result.getEndTime());
        Log.d(TAG, result.getUrl());
    }

    @Override
    public void postRouteDetailFailure(int code, String message) {
        Log.d("상세 저장 실패: ", message);
    }

//
//    // 수정 api
//    private PatchRouteDetailRequest patchRequest() {
//        //
//        return PatchRouteDetailRequest();
//    }
//    private void patchRouteDetail() {
//
//    }

    // 조회 api
    private void getRouteDetail() {
        GetRouteDetailService getRouteDetailService = new GetRouteDetailService();
        getRouteDetailService.setGetRouteDetailResult(this);
//        getRouteDetailService.getRouteDetail();
    }

    @Override
    public void getRouteDetailSuccess(int code, GetRouteDetailResponse.Result result) {

    }

    @Override
    public void getRouteDetailFailure(int code, String message) {

    }


//    // 스피너
//    // 최신순 클릭하면 그거에 맞는 adapter or 오래된순 클릭하면 그거에 맞는 adapter 보여주는 역할
//    private void setUpSpinnerHandler() {
//        routeAddMyBinding.spinnerBtn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                filterContent = items.get(position);
//                filterState = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }

    // editText 내용 입력시 버튼 활성화
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!routeAddMyBinding.addRouteTitle.toString().isEmpty() && !routeAddMyBinding.startAmPm.toString().isEmpty() && !routeAddMyBinding.startHour.toString().isEmpty()
                    && !routeAddMyBinding.startMinute.toString().isEmpty() && !routeAddMyBinding.endAmPm.toString().isEmpty() && !routeAddMyBinding.endHour.toString().isEmpty()
                    && !routeAddMyBinding.endMinute.toString().isEmpty() && !routeAddMyBinding.placeAdd.toString().isEmpty()) {
                routeAddMyBinding.btnSaveRoute.setClickable(true);
                routeAddMyBinding.btnSaveRoute.setBackgroundResource(R.drawable.btn_pink_background);
            } else {
                routeAddMyBinding.btnSaveRoute.setClickable(true);
                routeAddMyBinding.btnSaveRoute.setBackgroundResource(R.drawable.btn_background_grey_color);
            }
        }
    };

    // 시간 형식 통일
    public String stringToTimestamp(String amPm, String hour, String minute) {
        int hourInt = 0;

        // 01, 02 이런 식으로 들어왔을 때
        if (hour.charAt(0) == '0') {
            // 1의 자리만 substring
            hourInt = Integer.parseInt(hour.substring(1));
            if (amPm.equals("오후") && Integer.parseInt(hour) != 12) {
                hourInt = Integer.parseInt(hour) + 12;
            } else {
                hourInt = Integer.parseInt(hour);
            }
        } else {
            if (amPm.equals("오후") && Integer.parseInt(hour) != 12) {
                hourInt = Integer.parseInt(hour) + 12;
            } else {
                hourInt = Integer.parseInt(hour);
            }
        }

        String orderTime = hourInt + ":" + minute;

        return orderTime;
    }


    // 권한 체크
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(RouteAddMyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(RouteAddMyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // 권한이 있을 경우 수행할 동작
            Log.d(TAG, "권한 획득");
            Toast.makeText (getApplicationContext(), "Permissions Granted", Toast.LENGTH_SHORT).show ();
        } else {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions(RouteAddMyActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQ_CODE);
            Log.d(TAG, "권한 획득 실패 -> 권한 요청");
        }
    }
    private void mapLoad() {
        // Google Maps 초기화
        MapsInitializer.initialize(this); // this는 현재 액티비티의 컨텍스트를 나타냅니다.
        // SupportMapFragment를 찾음
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gpsMap);
        mapFragment.getMapAsync(mapReadyCallback); // map 정보 가져오기 (Callback 호출)
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
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

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

                routeAddMyBinding.placeAdd.setText(markerAddress);
            }
        }
    }

    private void executeReverseGeocoding(String str) {
        if(Geocoder.isPresent() && str != null)
            new ReverseGeoTask().execute(str);
    }

    class ReverseGeoTask extends AsyncTask<String, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

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
                    Toast.makeText (getApplicationContext(), "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show ();
                } else {
                    Address address = addresses.get(0);
                    mLat = address.getLatitude();
                    mLng = address.getLongitude();

                    mLastLocation.setLatitude(mLat);
                    mLastLocation.setLongitude(mLng);

                    if (mLat != 360.0 && mLng != 360.0) {
                        // 지도 현재 위치 변경
                        mGoogleMap.animateCamera (CameraUpdateFactory.newLatLngZoom (new LatLng (mLat, mLng), 15));

                        // 장소 검색 주소 변경
                        String markerAddress = address.getAddressLine (0);
                        routeAddMyBinding.placeAdd.setText(markerAddress);

                        // 마커 위치 변경
                        mCenterMarker.setPosition(new LatLng(mLat, mLng));
                    } else
                        Toast.makeText (getApplicationContext(), "정확한 위치명을 입력하세요", Toast.LENGTH_SHORT).show ();
                }
            }
        }
    }

    // 이미지
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navigateGallery();
            } else {
                Toast.makeText(getApplicationContext(), "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 2000) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String filePath = absolutelyPath(selectedImageUri, getApplicationContext());
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                Log.d("이미지 넣음====", body.toString());
                routeAddMyBinding.rtImage.setImageURI(selectedImageUri);
                Log.d("이미지 넣음====", selectedImageUri.toString());
                routeAddMyBinding.cameraBtnTxt.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public String absolutelyPath(Uri path, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor c = context.getContentResolver().query(path, proj, null, null, null);
        if (c != null) {
            int index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            c.moveToFirst();
            String result = c.getString(index);
            c.close();
            return result;
        }
        return "";
    }

    // 이미지 권한
    private void showPermissionContextPopup() {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("권한이 필요합니다.")
                .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
                .setPositiveButton("동의하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                    }
                })
                .setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소 버튼 클릭 시 처리
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
