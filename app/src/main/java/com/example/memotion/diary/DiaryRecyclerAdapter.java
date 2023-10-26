package com.example.memotion.diary;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.memotion.MainActivity;
import com.example.memotion.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder>{
    private DiaryActivity diaryActivity;
    private ArrayList<DiaryItem> diaryList;

    public DiaryRecyclerAdapter(DiaryActivity diaryActivity) {
        this.diaryActivity = diaryActivity;
    }
    @NonNull
    @Override
    public DiaryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof DiaryRecyclerAdapter.ViewHolder) {
            holder.bind(diaryList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(diaryList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    public void setDiaryList(ArrayList<DiaryItem> diaryList) {
        this.diaryList = diaryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return diaryList != null ? diaryList.size() : 0;
    }

    private DiaryRecyclerAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(DiaryRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(DiaryItem diaryItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView diaryAddress;
        ImageView emotion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            diaryAddress = (TextView) itemView.findViewById(R.id.reviewText);
            emotion = (ImageView) itemView.findViewById(R.id.feel_icon);
        }

        void bind(DiaryItem item){
            // checkBox text로 diaryId 삽입
            checkBox.setText(String.valueOf(item.getDiaryId()));

            // address에 place 삽입
            diaryAddress.setText(item.getPlace());

            // emotion에 item 감정 텍스트에 따라 이미지 삽입
            String emotionName = item.getEmotion();
            int resourceId = emotion.getContext().getResources().getIdentifier(emotionName, "drawable", emotion.getContext().getPackageName());
            if (resourceId != 0) {
                emotion.setImageResource(resourceId);
            }
        }
    }
}