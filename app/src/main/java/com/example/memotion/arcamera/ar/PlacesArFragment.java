package com.example.memotion.arcamera.ar;

import android.Manifest;
import com.google.ar.sceneform.ux.ArFragment;

public class PlacesArFragment extends ArFragment {

    @Override
    public String[] getAdditionalPermissions() {
        // TODO: return location permission
        return new String[0];
    }
}