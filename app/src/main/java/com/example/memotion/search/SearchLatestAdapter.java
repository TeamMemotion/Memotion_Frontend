package com.example.memotion.search;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemSearchBinding;
import com.example.memotion.search.post.SearchGetResponse;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchLatestAdapter extends RecyclerView.Adapter<SearchLatestAdapter.ViewHolder>{

    private SearchFragment searchFragment;
    ItemSearchBinding itemSearchBinding;
    private Context context;
    private ArrayList<SearchGetResponse.Result> searchList;

    public SearchLatestAdapter(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public void setSearchLatestList(ArrayList<SearchGetResponse.Result> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchLatestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();

        return new ViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLatestAdapter.ViewHolder holder, int position) {
        if (holder instanceof SearchLatestAdapter.ViewHolder) {
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

            LatLng location = new LatLng(result.getLatitude(), result.getLongitude());
            executeGeocoding(location);

            String date = result.getCreatedDate();
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8);
            String createdAt = year + ". " + month + "." + day;
            itemSearchBinding.date.setText(createdAt);

            Log.d("diaryId: ", result.getDiaryId().toString());
            Log.d("keyword: ", result.getKeyWord());
            Log.d("emotion: ", result.getEmotion());
            Log.d("createdAt: ", result.getCreatedDate());
        }
    }

    private SearchLatestAdapter.OnItemClickListener itemClickListener;

    public void setItemClickListener(SearchLatestAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(SearchGetResponse.Result result);
    }

    private void executeGeocoding(LatLng latLng) {
        if(Geocoder.isPresent() && latLng != null)
            new GeoTask().execute(latLng);
    }

    class GeoTask extends AsyncTask<LatLng, Void, List<Address>> {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        @Override
        protected List<Address> doInBackground(LatLng...latLngs) {
            List<Address> address = null;

            try{
                address = geocoder.getFromLocation(latLngs[0].latitude, latLngs[0].longitude, 1);
            } catch(IOException e){
                e.printStackTrace();
            }
            return address;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses != null) {
                Address address = addresses.get(0);
                String markerAddress = address.getAddressLine (0);

                itemSearchBinding.location.setText(markerAddress);
            }
        }
    }
}
