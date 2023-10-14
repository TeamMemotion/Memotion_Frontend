package com.example.memotion.arcamera.ar;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.memotion.R;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class PlacesArFragment extends ArFragment {

    private static final String TAG = "ArFragment";

    @Override
    public String[] getAdditionalPermissions() {
        // TODO: return location permission
        return new String[0];
    }

    // 아래 메소드를 추가하여 말풍선을 생성하고 AR 화면에 표시합니다.
    public void showInfo(float x, float y, float z, String message) {
        // Layout을 inflate하여 말풍선의 모양을 정의합니다.
        View infoBubbleLayout = LayoutInflater.from(requireContext()).inflate(R.layout.activity_ar_info, null);
        TextView infoText = infoBubbleLayout.findViewById(R.id.placeKeyword);
        infoText.setText(message);

        // TransformableNode를 생성하여 말풍선을 표시할 위치와 모양을 설정합니다.
        TransformationSystem transformationSystem = getTransformationSystem();
        TransformableNode infoBubble = new TransformableNode(transformationSystem);

        getArSceneView().getScene().addChild(infoBubble);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ViewRenderable.builder()
                    .setSource(requireContext(), R.layout.activity_ar_info)
                    .build()
                    .thenAccept(renderable -> {
                        infoBubble.setRenderable(renderable);
                    })
                    .exceptionally(throwable -> {
                        // ViewRenderable 생성 실패 시 처리
                        throwable.printStackTrace();
                        return null;
                    });
        }
    }
}
