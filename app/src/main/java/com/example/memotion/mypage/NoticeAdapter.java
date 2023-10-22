package com.example.memotion.mypage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memotion.R;
import com.example.memotion.databinding.ItemNoticeBinding;
import com.example.memotion.mypage.get.notice.all.NoticeAllGetResponse;
import com.example.memotion.mypage.get.notice.detail.NoticeDetailGetResult;
import com.example.memotion.mypage.get.notice.detail.NoticeDetailGetService;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private NoticeActivity noticeActivity;
    ItemNoticeBinding itemNoticeBinding;
    private Context context;
    private ArrayList<NoticeAllGetResponse.Result> noticeAllList;

    public NoticeAdapter(NoticeActivity noticeActivity) { this.noticeActivity = noticeActivity; }

    public void setNoticeAllList(ArrayList<NoticeAllGetResponse.Result> result) {
        this.noticeAllList = result;
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemNoticeBinding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();

        return new ViewHolder(itemNoticeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        holder.bind(noticeAllList.get(position));
    }

    @Override
    public int getItemCount() {
        return noticeAllList != null ? noticeAllList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements NoticeDetailGetResult {
        private ItemNoticeBinding itemNoticeBinding;

        public ViewHolder(ItemNoticeBinding binding) {
            super(binding.getRoot());
            this.itemNoticeBinding = binding;
        }

        void bind(NoticeAllGetResponse.Result result) {
            boolean[] isSelected = {false};

            String date = result.getCreatedAt();
            String year = date.substring(0, 4);
            String month = date.substring(5, 7);
            String day = date.substring(8);
            String createdAt = year + "년 " + month + "월 " + day + "일";

            Log.d("noticeId: ", result.getNoticeId().toString());
            Log.d("createdAt: ", result.getCreatedAt());
            Log.d("변환한 날짜: ", createdAt);

            itemNoticeBinding.noticeTitle.setText(result.getName());
            itemNoticeBinding.noticeTime.setText(createdAt);

            // 상세 공지 보기
            itemNoticeBinding.seeNoticeDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelected[0] = !isSelected[0];
                    if (isSelected[0]) {
                        noticeDetail(result.getNoticeId());
                    } else {
                        itemNoticeBinding.seeNoticeDetail.setImageResource(R.drawable.back);
                        itemNoticeBinding.noticeFramelayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        // 공지사항 상세
        private void noticeDetail(Long noticeId) {
            NoticeDetailGetService noticeDetailGetService = new NoticeDetailGetService();
            noticeDetailGetService.setNoticeDetailGetResult(this);
            noticeDetailGetService.getNoticeDetail(noticeId);
        }

        @Override
        public void getNoticeDetailSuccess(int code, NoticeAllGetResponse.Result result) {
            Log.d("noticeId 값 : ", result.getNoticeId().toString());
            Log.d("title 값 : ", result.getName());
            Log.d("공지사항 내용 : ", result.getContent());

            itemNoticeBinding.seeNoticeDetail.setImageResource(R.drawable.clickback);
            itemNoticeBinding.noticeContent.noticeContent.setText(result.getContent());
            itemNoticeBinding.noticeFramelayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void getNoticeDetailFailure(int code, String message) {
            Toast.makeText(context, "공지사항 세부 내용 로딩 실패", Toast.LENGTH_SHORT).show();
            Log.d("공지사항 상세 조회 실패 : ", message);
        }
    }
}
