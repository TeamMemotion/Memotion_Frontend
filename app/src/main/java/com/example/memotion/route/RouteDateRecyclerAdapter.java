package com.example.memotion.route;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemDateBinding;
import com.example.memotion.databinding.ItemSearchBinding;
import com.example.memotion.diary.DiaryItem;
import com.example.memotion.diary.PlaceEditDialog;
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
    private int selectedPosition = -1;   // 날짜 선택 위치
    public static Date selectedDate;

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
            holder.bind(dateList.get(position), position);

            Log.d(TAG, "onBindViewHolder 들어옴");
            if (position != selectedPosition) {
                holder.itemDateBinding.selectBar.setVisibility(View.INVISIBLE);
                holder.itemDateBinding.itemDate.setTypeface(null, Typeface.NORMAL);
                holder.itemDateBinding.itemDate.setTextColor(0xFF6B6B6B);
            }
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

            // TO DO. 날짜 클릭 시 날짜별 RouteDetail 호출 (리스트 요소 클릭 시) -> 어지니가 API 호출 코드 작성
            // itemView 클릭 시 텍스트 진하게 + 이미지 색 변경
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // itemDate bold체로 변경 + 검정색으로 변경
                    itemDateBinding.itemDate.setTypeface(null, Typeface.BOLD);
                    itemDateBinding.itemDate.setTextColor(Color.BLACK);

                    // 이전에 선택한 날짜의 selectBar를 invisible로 설정
                    if (selectedPosition != getAdapterPosition()) {
                        notifyItemChanged(selectedPosition);
                    }

                    // 선택된 날짜의 selectBar visible로 변경
                    selectedPosition = getAdapterPosition();
                    itemDateBinding.selectBar.setVisibility(View.VISIBLE);

                    selectedDate = dateList.get(selectedPosition);
                }
            });

        }

        void bind(Date selectDate, int position) {
            Log.d(TAG, "Date 바인딩 시작");

            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            String day = dayFormat.format(selectDate);

            Log.d(TAG, "day: " + day + "일");
            itemDateBinding.itemDate.setText(day + "일");

            // 데이터 바인딩이 끝났는데도 어떤 날짜도 선택하지 않은 경우 > 선택일 시작일로 초기화
            if(position == 0 && selectedPosition == -1) {
                // itemDate bold체로 변경 + 검정색으로 변경
                itemDateBinding.itemDate.setTypeface(null, Typeface.BOLD);
                itemDateBinding.itemDate.setTextColor(Color.BLACK);

                // 선택된 날짜의 selectBar visible로 변경
                selectedPosition = position;
                itemDateBinding.selectBar.setVisibility(View.VISIBLE);
                selectedDate = selectDate;
            }

            // 데이터 바인딩 끝나면 현재 선택된 날짜를 기준으로 Route_Detail 조회하는 API 호출
            if(position == getItemCount() - 1) {

            }
        }
    }
}
