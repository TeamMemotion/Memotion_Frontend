package com.example.memotion.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memotion.R;
import com.example.memotion.databinding.ItemDateBinding;
import com.example.memotion.databinding.ItemRoutePlanBinding;
import com.example.memotion.diary.DiaryItem;
import com.example.memotion.diary.DiaryRecyclerAdapter;
import com.example.memotion.diary.PlaceEditDialog;
import com.example.memotion.route.get.routedetail.GetRouteDetailResponse;
import com.example.memotion.route.get.routedetailList.GetRouteDetailListResponse;
import com.example.memotion.search.SearchLatestAdapter;
import com.example.memotion.search.get.SearchGetResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import okhttp3.Route;

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.ViewHolder> {
    private String TAG = "RouteRecyclerAdapter";
    private ItemRoutePlanBinding itemRoutePlanBinding;
    private RouteActivity routeActivity;
    private ArrayList<GetRouteDetailListResponse.Result.RouteDetailResult> routeDetailList;
    private Context context;

    public RouteRecyclerAdapter(RouteActivity routeActivity){
        this.routeActivity = routeActivity;
    }

    public void setRouteDetailListItems(ArrayList<GetRouteDetailListResponse.Result.RouteDetailResult> detailItemsList) {
        this.routeDetailList = detailItemsList;
    }

    @NonNull
    @Override
    public RouteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        itemRoutePlanBinding = ItemRoutePlanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RouteRecyclerAdapter.ViewHolder(itemRoutePlanBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof RouteRecyclerAdapter.ViewHolder) {
            holder.bind(routeDetailList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(routeDetailList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return routeDetailList != null ? routeDetailList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRoutePlanBinding itemRoutePlanBinding;

        public ViewHolder(@NonNull ItemRoutePlanBinding binding) {
            super(binding.getRoot());
            this.itemRoutePlanBinding = binding;
        }

        void bind(GetRouteDetailListResponse.Result.RouteDetailResult item) {
            Log.d(TAG, item.getContent());
            Log.d(TAG, item.getUrl());
            Log.d(TAG, item.getEnd_time());
            Log.d(TAG, item.getStart_time());
            Log.d(TAG, item.getSelect_date());

            itemRoutePlanBinding.routeplanTitle.setText(item.getTitle());
            itemRoutePlanBinding.planplace.setText(item.getPlace());
            itemRoutePlanBinding.plantimeEnd.setText(item.getEnd_time());
            itemRoutePlanBinding.plantimeStart.setText(item.getStart_time());

            if(item.getUrl() != null) {
                Glide.with(context).load(item.getUrl()).centerCrop().into(itemRoutePlanBinding.routePic);
            }
        }
    }

    private RouteRecyclerAdapter.OnItemClickListener itemClickListener;


    public void setItemClickListener(RouteRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(GetRouteDetailListResponse.Result.RouteDetailResult item);
    }
}


