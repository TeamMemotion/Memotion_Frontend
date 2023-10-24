package com.example.memotion.route;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memotion.R;
import com.example.memotion.databinding.ItemRoutePlanBinding;
import com.example.memotion.diary.DiaryItem;
import com.example.memotion.diary.DiaryRecyclerAdapter;
import com.example.memotion.diary.PlaceEditDialog;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import okhttp3.Route;

public class RouteRecyclerAdapter extends RecyclerView.Adapter<RouteRecyclerAdapter.ViewHolder> {
    ItemRoutePlanBinding itemRoutePlanBinding;
    private RouteActivity routeActivity;
    private ArrayList<RouteDetailItem> routeDetailItems;

    private Context context;
    public RouteRecyclerAdapter(RouteActivity routeActivity){
        this.routeActivity=routeActivity;
    }

    @NonNull
    @Override
    public RouteRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        context = parent.getContext();
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

        }


        void onBind(RouteDetailItem item) {
            if(item.getUrl() != null) {
                Glide.with(context).load(item.getUrl()).centerCrop().into(itemRoutePlanBinding.routePic);
            }
            itemRoutePlanBinding.routeplanTitle.setText(item.getTitle());
            itemRoutePlanBinding.planplace.setText(item.getPlace());
            itemRoutePlanBinding.plantimeEnd.setText(item.getEnd_time());
            itemRoutePlanBinding.plantimeStart.setText(item.getStart_time());
        }
    }
}


