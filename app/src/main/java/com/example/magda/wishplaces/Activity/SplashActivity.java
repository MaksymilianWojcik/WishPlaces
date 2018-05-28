package com.example.magda.wishplaces.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.magda.wishplaces.R;
import com.example.magda.wishplaces.Utils.AppValues;
import com.example.magda.wishplaces.dao.WishPlaceDao;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivityLog";
    private boolean isTablet = false;
    private SharedPreferences preferences;

    // Czas trwania splash screenu
    private static int SPLASH_TIME_OUT = AppValues.SPLASH_ACTIVITY_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate()");

        //Pobranie preferencji z aplikacji
        preferences = getSharedPreferences(AppValues.PREFERENCES_NAME, MODE_PRIVATE);

        //Sprawdzamy, czy jestesmy na tablecie czy na telefonie
        if (findViewById(R.id.tabletCheckCase) != null){
            isTablet = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            preferences.edit().putInt(AppValues.PREFERENCES_DEVICE_TYPE, 1).commit();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            preferences.edit().putInt(AppValues.PREFERENCES_DEVICE_TYPE, 0).commit();
        }

        //Informujemy o wymaganym polaczeniu z internetem
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.internet_alert_dialog_title);
        builder.setMessage(R.string.internet_alert_dialog_info);
        //blokujemy mozliwosc anulowania warningu
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //wywolujemy AsyncTaska (dzialanie w tle) po kliknieciu przycisku "ok"
                new PrefetchData().execute();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @TargetApi(23)
            @Override
            public void onDismiss(DialogInterface dialog) {
            }

        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    //Jest to po to (gdybysmy zezwolili na rotacje widoku) zeby uniknac wywolania kilku watkow z rzedu
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
    }

    // Async task - generujemy sobie sztuczne dane w tle i usypiamy widok na 3 sekundy (SPLASH_TIME_OUT)
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        //Wykonaj przed rozpoczeciem dzialania w tle
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Wykonaj w tle
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                //jezeli pierwszy raz uruchamiamy aplikacje na telefonie, wygenerujmy sobie jakies przykladowe dane
                if(preferences.getBoolean(AppValues.PREFERENCES_FIRST_LOGIN, true)){
                    generateMockWishPlaces();
                    preferences.edit().putBoolean(AppValues.PREFERENCES_FIRST_LOGIN, false).commit();
                }
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Wykonaj po zakonczeniu dzialania w tle
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    /**
     * Generujemy dane do bazy
     */
    private void generateMockWishPlaces(){
        WishPlaceDao wishPlaceDao = new WishPlaceDao(this);
        wishPlaceDao.open();
        wishPlaceDao.createWishPlace("Wyspy owcze", "Wakacje 2018", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "53.4293685", "14.344447");
        wishPlaceDao.createWishPlace("Kanary", "Wspomnienie", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "21.4293685", "14.344447");
        wishPlaceDao.createWishPlace("Asia Trip", "Urlop w sierpniu", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "43.4233438", "-122.0728817");
        wishPlaceDao.createWishPlace("jUeSej", "Work and travel", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "37.4629101", "-85.2449094");
        wishPlaceDao.createWishPlace("Majowka", "Majowka and fun", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "11.4629101", "-33.2449094");
        wishPlaceDao.createWishPlace("Konkret", "Plan na rok 2019", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "14.4629101", "21.2449094");
        wishPlaceDao.createWishPlace("Test1", "Test1", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018.",
                "54.4629101", "-21.2449094");
        wishPlaceDao.createWishPlace("Test2", "Test2", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "34.4629101", "-91.2449094");
        wishPlaceDao.createWishPlace("Test3", "Test3", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "84.4629101", "71.2449094");
        wishPlaceDao.createWishPlace("Test4", "Test4", "Tutaj bede razem z Maksem i Eliza. Wyjazd 27.06.2018",
                "114.4629101", "121.2449094");
        wishPlaceDao.close();
    }
}
