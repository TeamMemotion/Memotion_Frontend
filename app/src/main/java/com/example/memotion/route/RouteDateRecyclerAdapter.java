package com.example.memotion.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemDateBinding;
import com.example.memotion.databinding.ItemSearchBinding;
import com.example.memotion.search.SearchFragment;
import com.example.memotion.search.SearchLatestAdapter;
import com.example.memotion.search.get.SearchGetResponse;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteDateRecyclerAdapter extends RecyclerView.Adapter<RouteDateRecyclerAdapter.ViewHolder> {
    private String TAG = "RouteDateAdapter";
    private ItemDateBinding itemDateBinding;
    private RouteActivity routeActivity;
    private Context context;
    private List<Date> dateList;

    public RouteDateRecyclerAdapter(RouteActivity routeActivity) {
        this.routeActivity = routeActivity;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public RouteDateRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        itemDateBinding = ItemDateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RouteDateRecyclerAdapter.ViewHolder(itemDateBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteDateRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof RouteDateRecyclerAdapter.ViewHolder) {
            holder.bind(dateList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(dateList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dateList != null ? dateList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDateBinding itemDateBinding;

        public ViewHolder(@NotNull ItemDateBinding binding) {
            super(binding.getRoot());
            this.itemDateBinding = binding;
        }

        void bind(Date selectDate) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(selectDate);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            String day = dayFormat.format(date);

            Log.d(TAG, "day: " + day + "일");
            itemDateBinding.itemDate.setText(day + "일");
        }
    }

    private RouteDateRecyclerAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(RouteDateRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(Date date);
    }
}
