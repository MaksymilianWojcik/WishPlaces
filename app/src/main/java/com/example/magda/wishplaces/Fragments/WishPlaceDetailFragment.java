package com.example.magda.wishplaces.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.magda.wishplaces.Activity.WishPlaceDetailActivity;
import com.example.magda.wishplaces.R;
import com.example.magda.wishplaces.dao.WishPlaceDao;
import com.example.magda.wishplaces.dto.WishPlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WishPlaceDetailFragment extends Fragment implements OnMapReadyCallback {

    public static final String ARG_ITEM_ID = "item_id";

    private SupportMapFragment mapFragment;
    private WishPlace wishPlace;

    @BindView(R.id.wishplace_detail_fragment_lat) TextView latTV;
    @BindView(R.id.wishplace_detail_fragment_lon) TextView lonTV;
    @BindView(R.id.wishplace_detail_fragment_name) TextView nameTV;
    @BindView(R.id.wishplace_detail_fragment_summary) TextView summaryTV;
    @BindView(R.id.wishplace_detail_fragment_description) TextView descriptionTV;


    public WishPlaceDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int wishplace_id = getArguments().getInt(ARG_ITEM_ID);
            WishPlaceDao dao = new WishPlaceDao(getContext());
            dao.open();
            wishPlace = dao.getFavyPlaceById(wishplace_id);
            dao.close();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wishplace_detail, container, false);

        ButterKnife.bind(this, rootView);

        if (wishPlace != null) {
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.place_detail_map_fr);
            mapFragment.getMapAsync(this);
            latTV.setText("LAT: " + wishPlace.getLatitude());
            lonTV.setText("LON: " + wishPlace.getLongitude());
            nameTV.setText(wishPlace.getName());
            summaryTV.setText(wishPlace.getSummary());
            descriptionTV.setText(wishPlace.getDescription());
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(wishPlace.getLatitude()), Double.parseDouble(wishPlace.getLongitude()))).title(wishPlace.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(wishPlace.getLatitude()), Double.parseDouble(wishPlace.getLongitude())),16));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
    }
}
