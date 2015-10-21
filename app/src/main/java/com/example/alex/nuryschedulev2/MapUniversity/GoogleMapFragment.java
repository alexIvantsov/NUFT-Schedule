package com.example.alex.nuryschedulev2.MapUniversity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.nuryschedulev2.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Oleksandr on 21.10.2015.
 */
public class GoogleMapFragment extends Fragment {

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_google_map, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getStringArray(R.array.screen_array)[2]);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
            return;
        }
        map.addMarker(new MarkerOptions().position(new LatLng(50.4392457,30.5098698)).title("Корпус А"));
        map.setMyLocationEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(50.4392457,30.5098698))
                .zoom(17)
                .bearing(45)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }
}
