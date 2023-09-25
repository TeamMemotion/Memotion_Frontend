package com.example.memotion.arcamera.ar;

import android.util.Log;

import com.google.ar.sceneform.ux.ArFragment;

public class PlacesArFragment extends ArFragment {

    private static final String TAG = "ArFragment";

    @Override
    public String[] getAdditionalPermissions() {
        // TODO: return location permission
        return new String[0];
    }

    //여기서 화면이 나옴
}