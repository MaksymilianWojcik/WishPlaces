package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.mwojcik.wishplaces.Fragments.WishPlaceDetailFragment;
import com.example.mwojcik.wishplaces.Fragments.WishPlaceListFragment;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;
import com.example.mwojcik.wishplaces.dto.WishPlace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity representing a single WishPlace detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link WishPlaceListFragment}.
 */
public class WishPlaceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private WishPlace wishPlace;

    private Toolbar toolbar;
    private TextView latTV;
    private TextView lonTV;
    private TextView nameTV;
    private TextView summaryTV;
    private TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishplace_detail);

        toolbar = (Toolbar) findViewById(R.id.wishplace_detail_mainToolbarActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        latTV = (TextView) findViewById(R.id.wishplace_detail_lat_tv);
        lonTV = (TextView) findViewById(R.id.wishplace_detail_lon_tv);
        nameTV = (TextView) findViewById(R.id.wishplace_detail_name_tv);
        summaryTV = (TextView) findViewById(R.id.wishplace_detail_summary_tv);
        descriptionTV = (TextView) findViewById(R.id.wishplace_detail_description_tv);


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(WishPlaceDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(WishPlaceDetailFragment.ARG_ITEM_ID));
//            WishPlaceDetailFragment fragment = new WishPlaceDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.wishplace_detail_container, fragment)
//                    .commit();

            if (getIntent().hasExtra(WishPlaceDetailFragment.ARG_ITEM_ID)){
                int wishplace_id = getIntent().getIntExtra(WishPlaceDetailFragment.ARG_ITEM_ID, 0);
                WishPlaceDao dao = new WishPlaceDao(this);
                dao.open();
                wishPlace = dao.getFavyPlaceById(wishplace_id);
                dao.close();

                latTV.setText("LAT: " + wishPlace.getLatitude());
                lonTV.setText("LON: " + wishPlace.getLongitude());
                nameTV.setText(wishPlace.getName());
                summaryTV.setText(wishPlace.getSummary());
                descriptionTV.setText(wishPlace.getDescription());
            }

            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.place_detail_map_ac);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

//api mwojcik
//AIzaSyC9BsHHM04uRZwW77hkXMvNcT8ZjMXCWTk