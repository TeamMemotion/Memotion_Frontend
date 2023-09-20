package com.example.memotion.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.databinding.ItemMyDiaryBinding;
import com.example.memotion.mypage.get.mydiary.GetDiaryEmotionResponse;

import java.util.ArrayList;

public class MyDiaryAdapter extends RecyclerView.Adapter<MyDiaryAdapter.ViewHolder> {

    private MyDiaryActivity myDiaryActivity;
    ItemMyDiaryBinding itemMyDiaryBinding;
    private Context context;
    private ArrayList<GetDiaryEmotionResponse.Result> myDiaryList;

    public MyDiaryAdapter(MyDiaryActivity myDiaryActivity) { this.myDiaryActivity = myDiaryActivity; }

    public void setMyDiaryList(ArrayList<GetDiaryEmotionResponse.Result> result) {
        this.myDiaryList = result;
    }

    @NonNull
    @Override
    public MyDiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemMyDiaryBinding = ItemMyDiaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();

        return new ViewHolder(itemMyDiaryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDiaryAdapter.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            holder.bind(myDiaryList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(myDiaryList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myDiaryList != null ? myDiaryList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMyDiaryBinding itemMyDiaryBinding;

        public ViewHolder(ItemMyDiaryBinding binding) {
            super(binding.getRoot());
            this.itemMyDiaryBinding = binding;
        }
        void bind (GetDiaryEmotionResponse.Result result) {
            itemMyDiaryBinding.title.setText(result.getTitle());
            itemMyDiaryBinding.date.setText("작성일자 " + result.getCreatedDate());
        }

    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(GetDiaryEmotionResponse.Result result);
    }
}
