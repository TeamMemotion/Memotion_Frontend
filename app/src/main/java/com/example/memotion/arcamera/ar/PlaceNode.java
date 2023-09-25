package com.example.memotion.arcamera.ar;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.example.memotion.R;
import com.example.memotion.arcamera.model.Place;

public class PlaceNode extends Node {

    private Context context;
    private Place place;
    private ViewRenderable placeRenderable;
    private TextView textViewPlace;

    public PlaceNode(Context context, Place place) {
        this.context = context;
        this.place = place;
    }

    @Override
    public void onActivate() {
        super.onActivate();

        if (getScene() == null) {
            return;
        }

        if (placeRenderable != null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ViewRenderable.builder()
                    .setView(context, R.layout.activity_ar_info)
                    .build()
                    .thenAccept(renderable -> {
                        setRenderable(renderable);
                        placeRenderable = renderable;

                        if (place != null) {
//                            textViewPlace = renderable.getView().findViewById(R.id.placeName);
                            textViewPlace.setText(place.getName());
                        }
                    });
        }
    }

    public void showInfoWindow() {
        // Show text
        if (textViewPlace != null) {
            textViewPlace.setVisibility(textViewPlace.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }

        // Hide text for other nodes
        Node parent = getParent();
        if (parent != null) {
            for (Node child : parent.getChildren()) {
                if (child instanceof PlaceNode && child != this) {
                    ((PlaceNode) child).textViewPlace.setVisibility(View.GONE);
                }
            }
        }
    }
    public Place getPlace() {
        return place;
    }
}
