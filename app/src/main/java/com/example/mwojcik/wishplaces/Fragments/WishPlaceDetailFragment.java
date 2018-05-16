package com.example.mwojcik.wishplaces.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mwojcik.wishplaces.Activity.WishPlaceDetailActivity;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;
import com.example.mwojcik.wishplaces.dto.WishPlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * A fragment representing a single WishPlace detail screen.
 * This fragment is either contained in a {@link WishPlaceListFragment}
 * in two-pane mode (on tablets) or a {@link WishPlaceDetailActivity}
 * on handsets.
 */
public class WishPlaceDetailFragment extends Fragment implements OnMapReadyCallback {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private SupportMapFragment mapFragment;
    private TextView latTV;
    private TextView lonTV;
    private TextView nameTV;
    private TextView summaryTV;
    private TextView descriptionTV;
    private WishPlace wishPlace;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WishPlaceDetailFragment() {
    }

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

        latTV = (TextView) rootView.findViewById(R.id.wishplace_detail_fragment_lat);
        lonTV = (TextView) rootView.findViewById(R.id.wishplace_detail_fragment_lon);
        nameTV = (TextView) rootView.findViewById(R.id.wishplace_detail_fragment_name);
        summaryTV = (TextView) rootView.findViewById(R.id.wishplace_detail_fragment_summary);
        descriptionTV = (TextView) rootView.findViewById(R.id.wishplace_detail_fragment_description);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.place_detail_map_fr);
        mapFragment.getMapAsync(this);

        latTV.setText("LAT: " + wishPlace.getLatitude());
        lonTV.setText("LON: " + wishPlace.getLongitude());
        nameTV.setText(wishPlace.getName());
        summaryTV.setText(wishPlace.getSummary());
        descriptionTV.setText(wishPlace.getDescription());

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
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
