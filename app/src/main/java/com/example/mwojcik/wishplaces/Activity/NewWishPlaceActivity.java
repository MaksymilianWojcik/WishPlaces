package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class NewWishPlaceActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;
    private Button back_button;
    private Button findPlace;
    private Button savePlaceBtn;
    private LinearLayout linearLayout;

    private EditText placeName;
    private EditText placeSummary;
    private EditText placeDescription;

    private TextView latTV;
    private TextView lonTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish_place);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        latTV = (TextView) findViewById(R.id.latitudeValueTV);
        lonTv = (TextView) findViewById(R.id.longitudeValueTV);
        savePlaceBtn = (Button) findViewById(R.id.savePlaceBtn);
        placeName = (EditText) findViewById(R.id.placeNameET);
        placeSummary = (EditText) findViewById(R.id.placeSummaryET);
        placeDescription = (EditText) findViewById(R.id.placeDescriptionET);

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

        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findPlace = (Button) findViewById(R.id.findLocationBtn);

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

        savePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = placeName.getText().toString();
                String summary = placeSummary.getText().toString();
                String description = placeDescription.getText().toString();
                String lat = latTV.getText().toString();
                String lon = lonTv.getText().toString();

                WishPlaceDao wishPlaceDao = new WishPlaceDao(NewWishPlaceActivity.this);
                wishPlaceDao.open();
                wishPlaceDao.createWishPlace(name, summary, description, lat, lon);
                wishPlaceDao.close();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                String msgLon = String.valueOf(place.getLatLng());
                String lat = String.valueOf(place.getLatLng().latitude);
                String lon = String.valueOf(place.getLatLng().longitude);
                latTV.setText(lat);
                lonTv.setText(lon);
                Log.d("NewWishPlace", msgLon);
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
//                savePlaceBtn.setEnabled(true);
                checkIfReadyToSave();
            } else {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_LONG).show();
                checkIfReadyToSave();
            }
        }
    }

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
                return true;
            }
        }
        savePlaceBtn.setEnabled(false);
        return false;
    }

}
