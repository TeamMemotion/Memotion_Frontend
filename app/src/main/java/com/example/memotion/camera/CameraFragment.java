package com.example.memotion.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.memotion.R;
import com.example.memotion.RetrofitClient;
import com.example.memotion.camera.emotion.GetEmotionResponse;
import com.example.memotion.camera.emotion.GetEmotionRetrofitInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CameraFragment extends Fragment {
    private static String TAG = "CameraActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private TextView textView;
    private GetEmotionRetrofitInterface apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        textView = view.findViewById(R.id.ar_keyword);

        Retrofit retrofit = RetrofitClient.getClient();

        apiService = retrofit.create(GetEmotionRetrofitInterface.class);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        Log.d(TAG, "카메라 onCreate");
        return view;
    }

    private void requestLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d(TAG, "카메라 위치 latitude: " + latitude + " , longitude: " + longitude);
                loadEmotionDataFromApi(latitude, longitude);
            }
        });
    }


    private void loadEmotionDataFromApi(double latitude, double longitude) {
        Log.d(TAG, "카메라 키워드");
        if (latitude == 37.421998333333335 && longitude == -122.084) {
            // 특정 좌표에 대한 처리
            String specificKeyword = "공덕";
            textView.setText("Keyword: " + specificKeyword);
        }
        Call<GetEmotionResponse> call = apiService.getEmotion(latitude, longitude);
        call.enqueue(new Callback<GetEmotionResponse>() {
            @Override
            public void onResponse(Call<GetEmotionResponse> call, Response<GetEmotionResponse> response) {
                if (response.isSuccessful()) {
                    GetEmotionResponse emotionResponse = response.body();
                    if (emotionResponse != null) {
                        ArrayList<GetEmotionResponse.Result> results = emotionResponse.getResult();
                        if (results != null && !results.isEmpty()) {
                            GetEmotionResponse.Result emotionResult = results.get(0);
                            String keyword = emotionResult.getKeyWord();
                            textView.setText("Keyword: " + keyword);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetEmotionResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
