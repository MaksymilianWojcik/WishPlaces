package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.dao.WishPlaceDao;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new PrefetchData().execute();
    }

    /**
     * Async Task to load data
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
//                generateMockWishPlaces();
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashActivity.this , MainActivity.class);
            startActivity(i);
            finish();
        }

    }


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
        wishPlaceDao.close();
    }
}
