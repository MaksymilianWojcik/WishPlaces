package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.Utils.AppValues;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewWishPlaceActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;
    private SharedPreferences preferences;

    @BindView(R.id.linearlayout) LinearLayout linearLayout;
    @BindView(R.id.back_button) Button back_button;
    @BindView(R.id.findLocationBtn) Button findPlace;
    @BindView(R.id.savePlaceBtn) Button savePlaceBtn;
    @BindView(R.id.latitudeValueTV) TextView latTV;
    @BindView(R.id.longitudeValueTV) TextView lonTv;
    @BindView(R.id.placeNameET) EditText placeName;
    @BindView(R.id.placeSummaryET) EditText placeSummary;
    @BindView(R.id.placeDescriptionET) EditText placeDescription;

    private boolean isSaveButtonEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish_place);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(AppValues.PREFERENCES_NAME, MODE_PRIVATE);

        //odwolujemy sie do naszgeo toolbara i ustawiamy tytul
        Toolbar toolbar = findViewById(R.id.new_wish_place_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) toolbar.getChildAt(0)).setText(R.string.title_new_wishplace);

        //Jezeli chcemy zezwolic na rotacje ekranu w tej Activity, to wystarczy to zakomentowac
        if(preferences.getInt(AppValues.PREFERENCES_DEVICE_TYPE, 0) == 0){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Jezeli chcemy np. zezwolic na rotacje ekranu tylko w Tablecie, to zakomentowac to na dole
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //Dodajemy obsluge klikniecia widoku
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placeName.hasFocus() || placeSummary.hasFocus() || placeDescription.hasFocus()) {
                  placeName.clearFocus();
                  placeSummary.clearFocus();
                  placeDescription.clearFocus();
                }
            }
        });

        //Sprawdzamy, czy zmienil sie stan edycji danego pola - zeby sprwdzic mozliwosc odblokowania zapisu danych
        placeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    checkIfReadyToSave();
                }
            }
        });

        placeSummary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    checkIfReadyToSave();

                }
            }
        });

        placeDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    checkIfReadyToSave();
                }
            }
        });

        //Obsluga klikniecia przycisku back
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //Po kliknieciu przycisku "find place" - Wywolanie Activity dostepnego przez Google Places API - jest to ActivityForResult, ktora czeka na odpowiedz po powrocie do tego Activity
        findPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(NewWishPlaceActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        //Obsluga klikniecia przycisku save place
        savePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Pobranie wartosci z pol
                String name = placeName.getText().toString();
                String summary = placeSummary.getText().toString();
                String description = placeDescription.getText().toString();
                String lat = latTV.getText().toString();
                String lon = lonTv.getText().toString();

                //Zapisanie lokalizacji do bazy danych
                WishPlaceDao wishPlaceDao = new WishPlaceDao(NewWishPlaceActivity.this);
                wishPlaceDao.open();
                wishPlaceDao.createWishPlace(name, summary, description, lat, lon);
                wishPlaceDao.close();
                finish();
            }
        });
    }

    //Zapisanie wartosci w polach w razie zezwolenia na rotacje
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("saveButtonEnabled", isSaveButtonEnabled);
        outState.putString("longitude", lonTv.getText().toString());
        outState.putString("latitude", latTV.getText().toString());
    }

    //Odzyskanie zapisanych wartosci w polach po rotacji
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isSaveButtonEnabled = savedInstanceState.getBoolean("saveButtonEnabled");
        lonTv.setText(savedInstanceState.getString("longitude"));
        latTV.setText(savedInstanceState.getString("latitude"));

        if (isSaveButtonEnabled){
            savePlaceBtn.setEnabled(true);
        }
    }

    //Zwrocenie danych z wybranej lokalizacji (Google Places API)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                //Wybranie lokalizacji sie powiodlo
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                String lat = String.valueOf(place.getLatLng().latitude);
                String lon = String.valueOf(place.getLatLng().longitude);
                latTV.setText(lat);
                lonTv.setText(lon);
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                checkIfReadyToSave();
            } else {
                //Wybranie lokalizacji sie nie powiodlo
                Toast.makeText(this, "Please select a location", Toast.LENGTH_LONG).show();
                checkIfReadyToSave();
            }
        }
    }

    //Sprawdzamy, czy obiekt jest gotowy do zapisania w bazie danych - jezeli tak, to umozliwia klikniecie przycisku zapisu
    private boolean checkIfReadyToSave(){
        String placeNameText = placeName.getText().toString();
        String placeSummaryText = placeSummary.getText().toString();
        String placeDescriptionText = placeDescription.getText().toString();
        String placeLatText = latTV.getText().toString();
        String placeLonText = latTV.getText().toString();

        if(placeNameText != null && placeSummaryText != null && placeDescriptionText != null) {
            if (!placeNameText.equals("") && !placeSummaryText.equals("") && !placeDescriptionText.equals("")
                    && !placeLatText.equals("Unknown...") && !placeLonText.equals("Unknown...")) {
                savePlaceBtn.setEnabled(true);
                isSaveButtonEnabled = true;
                return true;
            }
        }
        savePlaceBtn.setEnabled(false);
        isSaveButtonEnabled = false;
        return false;
    }

}
