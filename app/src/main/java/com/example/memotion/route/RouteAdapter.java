package com.example.memotion.route;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memotion.databinding.ItemRouteBinding;
import com.example.memotion.route.get.route.myRoute.MyRouteGetResponse;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private RouteFragment routeFragment;
    ItemRouteBinding itemRouteBinding;
    private Context context;
    private ArrayList<MyRouteGetResponse.Result> myRouteList;

    public RouteAdapter(RouteFragment routeFragment) {
        this.routeFragment = routeFragment;
    }

    public void setMyRouteList(ArrayList<MyRouteGetResponse.Result> result) {
        this.myRouteList = result;
    }

    @NonNull
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemRouteBinding = ItemRouteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(itemRouteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            holder.bind(myRouteList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(myRouteList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRouteBinding itemRouteBinding;

        public ViewHolder(ItemRouteBinding binding) {
            super(binding.getRoot());
            this.itemRouteBinding = binding;
        }

        void bind(MyRouteGetResponse.Result result) {
            if(result.getProfileImg() != null) {
                Glide.with(context).load(result.getProfileImg()).centerCrop().into(itemRouteBinding.profileImage);
            }
            if(result.getRouteImg() != null) {
                Glide.with(context).load(result.getRouteImg()).centerCrop().into(itemRouteBinding.routePic);
            }
            itemRouteBinding.routeTitle.setText(result.getName());
            itemRouteBinding.userName.setText(result.getUsername());
            itemRouteBinding.heartCount.setText(result.getLikeCount().toString());

            String startDate = result.getStartDate();
            String startYear = startDate.substring(0, 4);
            String startMonth = startDate.substring(5, 7);
            String startDay = startDate.substring(8);
            String start = startYear + "." + startMonth + "." + startDay;
            itemRouteBinding.routeStartDate.setText(start);

            String endDate = result.getEndDate();
            String endYear = endDate.substring(0, 4);
            String endMonth = endDate.substring(5, 7);
            String endDay = endDate.substring(8);
            String end = endYear + "." + endMonth + "." + endDay;
            itemRouteBinding.routeEndDate.setText(end);
        }
    }

    @Override
    public int getItemCount() {
        return myRouteList != null ? myRouteList.size() : 0;
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(MyRouteGetResponse.Result result);
    }
}