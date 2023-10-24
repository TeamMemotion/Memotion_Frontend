package com.example.memotion.arcamera;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.memotion.R;
import com.example.memotion.RetrofitClient;
import com.example.memotion.arcamera.ar.PlacesArFragment;
import com.example.memotion.arcamera.emotion.GetEmotionResponse;
import com.example.memotion.arcamera.emotion.GetEmotionRetrofitInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.ar.sceneform.ux.ArFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArActivity extends AppCompatActivity {

    private static final String TAG = "ArActivity";
    private ArFragment arFragment;
    private FusedLocationProviderClient fusedLocationClient;
    private GetEmotionRetrofitInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isSupportedDevice()) {
            return;
        }

        setContentView(R.layout.activity_ar);
        // AR 카메라 환경 설정
        //arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        PlacesArFragment arFragment = (PlacesArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(GetEmotionRetrofitInterface.class);

        float latitude = 37.7749f; // 예시 위도
        float longitude = -122.4194f; // 예시 경도
        String message = "현재 위치"; // 말풍선에 표시할 메시지

        // 현재 위치에 말풍선을 나타내기 위해 showInfo() 메소드 호출
        fetchEmotionData(latitude, longitude);
    }

    private boolean isSupportedDevice() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String openGlVersionString = activityManager.getDeviceConfigurationInfo().getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < 3.0) {
            Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show();
            finish();
            return false;
        }
        return true;
    }

    private void showInfo(float x, float y, float z, String message) {
        if (arFragment instanceof PlacesArFragment) {
            ((PlacesArFragment) arFragment).showInfo(x, y, z, message);
            Log.d(TAG, "msg:" + message);
        }
    }

    private void fetchEmotionData(float latitude, float longitude) {
        // 여기서 데이터베이스에서 위치-키워드 데이터를 가져오는 로직을 구현해야 합니다.
        // 데이터베이스에서 가져온 키워드를 keyword 변수에 할당합니다.

        // 예시: 데이터베이스에서 키워드 가져오기
        String keyword = getKeywordFromDatabase(latitude, longitude);

        if (keyword != null) {
            // 데이터베이스에서 키워드를 가져왔을 경우, 해당 키워드를 "placeKeyword" TextView에 표시합니다.
            String message = "Emotion: " + keyword;
            showInfo(0.0f, 0.0f, 0.0f, message);
        }
    }

    // 데이터베이스에서 위치에 해당하는 키워드를 가져오는 메서드
    private String getKeywordFromDatabase(float latitude, float longitude) {
        // 여기에서 데이터베이스 쿼리를 실행하여 위치 정보에 해당하는 키워드를 가져오는 로직을 구현합니다.
        // 데이터베이스 쿼리를 실행하고 결과를 문자열로 반환합니다.

        // 예시: 고정된 키워드를 반환하는 대신 실제 데이터베이스 쿼리를 실행
        if (latitude == 37.7749f && longitude == -122.4194f) {
            return "키워드_예시";
        } else {
            return null;
        }
    }
}