package com.example.memotion.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memotion.databinding.ActivityWishlistBinding;
import com.example.memotion.mypage.get.wishlist.WishListGetResponse;
import com.example.memotion.mypage.get.wishlist.WishListGetResult;
import com.example.memotion.mypage.get.wishlist.WishListGetService;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity implements WishListGetResult {
    ActivityWishlistBinding wishlistBinding;
    private static String TAG = "WishListActivity";
    private WishListAdapter wishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishlistBinding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(wishlistBinding.getRoot());

        wishListAdapter = new WishListAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActionBar();
        getWishList();
    }

    private void initActionBar() {
        wishlistBinding.actionWishlist.appbarPageNameLeftTv.setText("관심 루트 기록");

        wishlistBinding.actionWishlist.appbarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 관심 목록 조회
    private void getWishList() {
        WishListGetService getWishListService = new WishListGetService();
        getWishListService.setWishListGetResult(this);
        getWishListService.getWishList();
    }

    @Override
    public void getWishListSuccess(int code, ArrayList<WishListGetResponse.Result> result) {
        Log.d(TAG, "관심루트 조회 성공");

        for(int i = 0; i < result.size() - 1; i++) {
            Log.d("routeId: ", result.get(i).getRouteId().toString());
            if(result.get(i).getRouteImg() != null) {
                Log.d("routeImg: ", result.get(i).getRouteImg());
            }
            Log.d("startDate: ", result.get(i).getStartDate());
            Log.d("endDate: ", result.get(i).getEndDate());
            Log.d("name: ", result.get(i).getName());
            Log.d("likeCount: ", result.get(i).getLikeCount().toString());
        }

        initRecycler(result);
    }

    @Override
    public void getWishListFailure(int code, String message) {
        Log.d(TAG, "관심루트 조회 실패");
    }

    private void initRecycler(ArrayList<WishListGetResponse.Result> result) {
        wishListAdapter.setWishList(result);
        wishlistBinding.wishlistView.setAdapter(wishListAdapter);
        wishlistBinding.wishlistView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        wishListAdapter.setItemClickListener(new WishListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WishListGetResponse.Result result) {
//                Intent intent = new Intent(getContext(), RouteActivity.class);
//                intent.putExtra("routeId", result.getRouteId());
//                startActivity(intent);
            }
        });
    }
}
