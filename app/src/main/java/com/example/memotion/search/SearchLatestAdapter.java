package com.example.memotion.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemSearchBinding;
import com.example.memotion.diary.DiaryRecyclerAdapter;
import com.example.memotion.search.get.SearchGetResponse;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchLatestAdapter extends RecyclerView.Adapter<SearchLatestAdapter.ViewHolder>{
    private String TAG = "SearchLatestAdapter";
    private SearchFragment searchFragment;
    private ItemSearchBinding itemSearchBinding;
    private Context context;
    private ArrayList<SearchGetResponse.Result> searchList;

    public SearchLatestAdapter(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public void setSearchLatestList(ArrayList<SearchGetResponse.Result> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchLatestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        itemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchLatestAdapter.ViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLatestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof ViewHolder) {
            holder.bind(searchList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(searchList.get(position));
                        notifyItemChanged(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return searchList != null ? searchList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchBinding itemSearchBinding;

        public ViewHolder(@NotNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.itemSearchBinding = binding;
        }

        void bind(SearchGetResponse.Result result) {
            if(result.getEmotion().equals("happy"))
                itemSearchBinding.emotion.setBackgroundResource(R.drawable.happy);
            else if(result.getEmotion().equals("smile"))
                itemSearchBinding.emotion.setBackgroundResource(R.drawable.smile);
            else if(result.getEmotion().equals("notbad"))
                itemSearchBinding.emotion.setBackgroundResource(R.drawable.notbad);
            else if(result.getEmotion().equals("sad"))
                itemSearchBinding.emotion.setBackgroundResource(R.drawable.sad);
            else if(result.getEmotion().equals("upset"))
                itemSearchBinding.emotion.setBackgroundResource(R.drawable.upset);

            itemSearchBinding.keyword.setText(result.getKeyWord());
            itemSearchBinding.searchLocation.setText(result.getPlace());

            String date = result.getCreatedDate();
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8);
            String createdAt = year + ". " + month + "." + day;
            itemSearchBinding.date.setText(createdAt);
        }
    }

    private SearchLatestAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(SearchGetResponse.Result result);
    }
}
