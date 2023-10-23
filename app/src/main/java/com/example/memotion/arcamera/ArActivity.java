package com.example.memotion.arcamera;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.memotion.R;
import com.example.memotion.arcamera.ar.PlacesArFragment;
import com.google.ar.sceneform.ux.ArFragment;

public class ArActivity extends AppCompatActivity {

//    private static final String TAG = "ArActivity";
//    private ArFragment arFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (!isSupportedDevice()) {
//            return;
//        }
//
//        setContentView(R.layout.activity_ar);
//        // AR 카메라 환경 설정
//        //arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
//        PlacesArFragment arFragment = (PlacesArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
//
//        float latitude = 37.7749f; // 예시 위도
//        float longitude = -122.4194f; // 예시 경도
//        String message = "현재 위치"; // 말풍선에 표시할 메시지
//
//        // 현재 위치에 말풍선을 나타내기 위해 showInfo() 메소드 호출
//        showInfo(latitude, 0.0f, longitude, message);
//    }
//
//    private boolean isSupportedDevice() {
//        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        String openGlVersionString = activityManager.getDeviceConfigurationInfo().getGlEsVersion();
//        if (Double.parseDouble(openGlVersionString) < 3.0) {
//            Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show();
//            finish();
//            return false;
//        }
//        return true;
//    }
//
//    private void showInfo(float x, float y, float z, String message) {
//        if (arFragment instanceof PlacesArFragment) {
//            ((PlacesArFragment) arFragment).showInfo(x, y, z, message);
//            Log.d(TAG, "msg:" + message);
//        }
//    }
//}


}