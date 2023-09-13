package com.example.memotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.example.memotion.arcamera.ArActivity;
import com.example.memotion.databinding.ActivityMainBinding;
import com.example.memotion.home.HomeFragment;
import com.example.memotion.mypage.MypageFragment;
import com.example.memotion.route.RouteFragment;
import com.example.memotion.search.SearchFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.ar.sceneform.ux.ArFragment;
import com.kakao.sdk.common.KakaoSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        KakaoSdk.init(this, this.getString(R.string.kakao_native_key));

        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new HomeFragment()).commit();

        NavigationBarView navigationBarView = viewBinding.navBottom;
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new HomeFragment()).commit();
                    return true;
                } else if (itemId == R.id.menu_search) {
                    getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new SearchFragment()).commit();
                    return true;
                } else if (itemId == R.id.menu_route) {
                    getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new RouteFragment()).commit();
                    return true;
                } else if (itemId == R.id.menu_mypage) {
                    getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new MypageFragment()).commit();
                    return true;
                } else if (itemId == R.id.menu_ar) {
                    getSupportFragmentManager().beginTransaction().replace(viewBinding.containerFragment.getId(), new ArFragment()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}