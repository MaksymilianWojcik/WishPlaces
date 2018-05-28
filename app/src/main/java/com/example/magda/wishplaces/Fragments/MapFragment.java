package com.example.magda.wishplaces.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.wishplaces.R;
import com.example.magda.wishplaces.dao.WishPlaceDao;
import com.example.magda.wishplaces.dto.WishPlace;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private ArrayList<WishPlace> placesList;
    private SupportMapFragment mapFragment;
    private GoogleMap map;

    public MapFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PrefTest", "onCreate()");
        placesList = new ArrayList<>();
        WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
        wishPlaceDao.open();
        placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
        wishPlaceDao.close();
    }

    public SupportMapFragment getMapFragment() {
        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapka);
        mapFragment.getMapAsync(this);
        mapFragment.setRetainInstance(true);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("RefreshingMapTest", "onMapReady");
        Log.d("PrefTest", "onMapReadyCallback()");
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.clear();
        for (WishPlace place : placesList){
            Log.d("PrefTest", "Adding place marker to map for place " + place.getName());
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude()))).title(place.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PrefTest", "onResume()");
        if(map != null){
            Log.d("PrefTest", "onResume() with map != null");
            WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
            wishPlaceDao.open();
            placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
            wishPlaceDao.close();
            onMapReady(map);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("PrefTest", "setUserVisibleHint()");
        super.setUserVisibleHint(isVisibleToUser);
        if(map!=null){
            Log.d("PrefTest", "onResume() map != null");
            WishPlaceDao wishPlaceDao = new WishPlaceDao(getContext());
            wishPlaceDao.open();
            placesList = (ArrayList<WishPlace>) wishPlaceDao.getAllFavyPlaces();
            wishPlaceDao.close();
            onMapReady(map);
        }
    }

}
