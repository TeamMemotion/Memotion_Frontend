package com.example.memotion.route;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ActivityDiaryBinding;
import com.example.memotion.databinding.ActivityRouteBinding;

public class RouteActivity extends AppCompatActivity {

    RouteDetailItem routeDetailItem;
    private RecyclerView recyclerView;
    private ActivityRouteBinding activityRouteBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRouteBinding = ActivityRouteBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_route);

        Intent intent = getIntent(); /*데이터 수신*/
        int routeId = intent.getExtras().getInt("routeId");

    }


}
