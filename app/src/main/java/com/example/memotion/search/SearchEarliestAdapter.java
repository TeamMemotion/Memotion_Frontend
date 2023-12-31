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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemSearchBinding;
import com.example.memotion.search.get.SearchGetResponse;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchEarliestAdapter extends RecyclerView.Adapter<SearchEarliestAdapter.ViewHolder> {
    private SearchFragment searchFragment;
    private ItemSearchBinding itemSearchBinding;
    private Context context;
    private ArrayList<SearchGetResponse.Result> searchList;

    public SearchEarliestAdapter(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public void setSearchEarliestList(ArrayList<SearchGetResponse.Result> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchEarliestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();

        return new ViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEarliestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        public ViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.itemSearchBinding = binding;
        }

        void bind(SearchGetResponse.Result result) {
            String address = result.getPlace();

            if(address.length() >= 15)
                address = address.substring(0, 14) + "...";
            itemSearchBinding.searchLocation.setText(address);
            itemSearchBinding.keyword.setText(result.getKeyWord());

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

            String date = result.getCreatedDate();
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8);
            String createdAt = year + ". " + month + "." + day;
            itemSearchBinding.date.setText(createdAt);
        }
    }

    private SearchEarliestAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(SearchGetResponse.Result result);
    }
}
