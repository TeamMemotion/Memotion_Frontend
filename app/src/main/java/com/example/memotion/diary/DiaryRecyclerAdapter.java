package com.example.memotion.diary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ArrayList<DiaryItem> diaryList;

    @NonNull
    @Override
    public DiaryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(diaryList.get(position));
    }

    public void setDiaryList(ArrayList<DiaryItem> diaryList) {
        this.diaryList = diaryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return diaryList != null ? diaryList.size() : 0;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    // 데이터 리스트로부터 아이템 데이터 참조
                    if (pos != RecyclerView.NO_POSITION) {
                        DiaryItem item = diaryList.get(pos);
                        Long diaryId = item.getDiaryId();
                        PlaceEditDialog dialog = new PlaceEditDialog(view.getContext(), item);
                        dialog.start();
                    }
                }
            });
        }

        void onBind(DiaryItem item){
            // checkBox text로 diaryId 삽입
            checkBox.setText(String.valueOf(item.getDiaryId()));

            // address에 item 위도, 경도로 geocoding한 주소 삽입
            LatLng newLatLng = new LatLng(item.getLatitude(), item.getLongitude());
            executeGeocoding(newLatLng);

            // emotion에 item 감정 텍스트에 따라 이미지 삽입
            String emotionName = item.getEmotion();
            int resourceId = emotion.getContext().getResources().getIdentifier(emotionName, "drawable", emotion.getContext().getPackageName());
            if (resourceId != 0) {
                emotion.setImageResource(resourceId);
            }
        }

        // Geocoding (위도, 경도 -> 실제 주소로 변환)
        private void executeGeocoding(LatLng latLng) {
            if(Geocoder.isPresent() && latLng != null)
                new GeoTask().execute(latLng);
        }

        class GeoTask extends AsyncTask<LatLng, Void, List<Address>> {
            Geocoder geocoder = new Geocoder(MainActivity.context, Locale.getDefault());

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
                    diaryAddress.setText(markerAddress);
                }
            }
        }
    }
}