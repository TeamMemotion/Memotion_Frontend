package com.example.memotion.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.diary.DiaryItem;
import com.example.memotion.diary.DiaryRecyclerAdapter;
import com.example.memotion.diary.PlaceEditDialog;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import okhttp3.Route;

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.ViewHolder> {
    private RouteActivity routeActivity;
    private ArrayList<RouteDetailItem> routeDetailItems;
    public RouteRecyclerAdapter(RouteActivity routeActivity){
        this.routeActivity=routeActivity;}
    @NonNull
    @Override
    public RouteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(routeDetailItems.get(position));
    }
    public void setRouteDetailItems(ArrayList<RouteDetailItem> detailItemsList) {
        this.routeDetailItems = detailItemsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routeDetailItems !=null ? routeDetailItems.size() : 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    // 데이터 리스트로부터 아이템 데이터 참조
                    if (pos != RecyclerView.NO_POSITION) {
                        RouteDetailItem item = routeDetailItems.get(pos);
                        //상세 루트 자세히 볼 수 있는 다이얼로그
                    }
                }
            });
        }


        void onBind(RouteDetailItem item) {


        }
    }
}


