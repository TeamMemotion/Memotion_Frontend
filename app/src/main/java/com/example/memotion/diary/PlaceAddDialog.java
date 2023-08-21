package com.example.memotion.diary;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.example.memotion.R;

public class PlaceAddDialog {

    private Context context;

    public PlaceAddDialog(Context context) {
        this.context = context;
    }

    public void start() {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diary_write_dialog);
        dialog.setCanceledOnTouchOutside(true); //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
        dialog.setCancelable(true);    // 취소가 가능하도록 하는 코드

        // 닫기 버튼
        dialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
