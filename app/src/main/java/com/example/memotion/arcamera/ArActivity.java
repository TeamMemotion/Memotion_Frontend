package com.example.memotion.arcamera;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.memotion.R;
import com.google.ar.sceneform.ux.ArFragment;

public class ArActivity extends AppCompatActivity {

    private static final String TAG = "ArActivity";
    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isSupportedDevice()) {
            return;
        }

        setContentView(R.layout.activity_ar);
        // AR 카메라 환경 설정
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

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
}
