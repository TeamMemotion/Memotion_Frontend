package com.example.memotion.route;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memotion.databinding.ItemLocalBinding;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import java.util.ArrayList;

public class LocalGuideAdapter extends RecyclerView.Adapter<LocalGuideAdapter.ViewHolder> {
    private RouteFragment routeFragment;
    ItemLocalBinding itemLocalBinding;
    private Context context;
    private ArrayList<LocalGuideGetResponse.Result> localGuideList;

    public LocalGuideAdapter(RouteFragment routeFragment) {
        this.routeFragment = routeFragment;
    }

    public void setLocalGuideList(ArrayList<LocalGuideGetResponse.Result> localGuideList) {
        this.localGuideList = localGuideList;
    }

    @NonNull
    @Override
    public LocalGuideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemLocalBinding = ItemLocalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(itemLocalBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalGuideAdapter.ViewHolder holder, int position) {
        if (holder instanceof LocalGuideAdapter.ViewHolder) {
            holder.bind(localGuideList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(localGuideList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return localGuideList != null ? localGuideList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLocalBinding itemLocalBinding;

        public ViewHolder(ItemLocalBinding binding) {
            super(binding.getRoot());
            this.itemLocalBinding = binding;
        }

        void bind(LocalGuideGetResponse.Result result) {
            itemLocalBinding.userName.setText(result.getUsername());
            if(result.getProfileImg() != null) {
                Glide.with(context).load(result.getProfileImg()).centerCrop().into(itemLocalBinding.profileImage);
            }
            if(result.getRouteImg() != null) {
                Glide.with(context).load(result.getRouteImg()).centerCrop().into(itemLocalBinding.routePic);
            }
        }
    }

    private LocalGuideAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(LocalGuideAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(LocalGuideGetResponse.Result result);
    }
}
