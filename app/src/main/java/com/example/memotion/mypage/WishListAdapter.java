package com.example.memotion.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memotion.databinding.ItemRouteBinding;
import com.example.memotion.mypage.get.wishlist.WishListGetResponse;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private WishListActivity wishListActivity;
    ItemRouteBinding itemRouteBinding;
    private Context context;
    private ArrayList<WishListGetResponse.Result> myWishList;

    public WishListAdapter(WishListActivity wishListActivity) {
        this.wishListActivity = wishListActivity;
    }

    public void setWishList(ArrayList<WishListGetResponse.Result> result) {
        this.myWishList = result;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemRouteBinding = ItemRouteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(itemRouteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            holder.bind(myWishList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(myWishList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myWishList != null ? myWishList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRouteBinding itemRouteBinding;

        public ViewHolder(ItemRouteBinding binding) {
            super(binding.getRoot());
            this.itemRouteBinding = binding;
        }

        void bind(WishListGetResponse.Result result) {
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

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(WishListGetResponse.Result result);
    }
}
