package com.example.mwojcik.wishplaces.Activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.mwojcik.wishplaces.Fragments.WishPlaceDetailFragment;
import com.example.mwojcik.wishplaces.Fragments.WishPlaceListFragment;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.Utils.AppValues;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single WishPlace detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link WishPlaceListFragment}.
 */
public class WishPlaceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private WishPlace wishPlace;

    @BindView(R.id.wishplace_detail_mainToolbarActivity) Toolbar toolbar;
    @BindView(R.id.wishplace_detail_lat_tv) TextView latTV;
    @BindView(R.id.wishplace_detail_lon_tv) TextView lonTV;
    @BindView(R.id.wishplace_detail_name_tv) TextView nameTV;
    @BindView(R.id.wishplace_detail_summary_tv) TextView summaryTV;
    @BindView(R.id.wishplace_detail_description_tv) TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishplace_detail);
        ButterKnife.bind(this);

        //Jezeli chcemy pozowlic na obracanie ekranu, to zakomentować linijki 51-56
        SharedPreferences preferences = getSharedPreferences(AppValues.PREFERENCES_NAME, MODE_PRIVATE);
        if(preferences.getInt(AppValues.PREFERENCES_DEVICE_TYPE, 0) == 0){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //Odwolujemy sie do naszego toolbara i udostpeniamy przycisk back (strzalka w lewo) oraz
        //ustawiamy tytul
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) toolbar.getChildAt(0)).setText(R.string.title_wishplace_detail);

        if (savedInstanceState == null) {
            //Pobieramy przekazany ID lokalizacji wybranej z listy
            //Pobieramy dane z lokalizacji i ustawiamy wartosci w polach
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

            //Tworzymy odwolanie do mapy i przypisujemy callback w tej klasie (onMapReady)
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.place_detail_map_ac);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Callback wywolujacy sie (po mapFragment.getMapAsync) kiedy mapa jest gotowa
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