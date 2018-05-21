package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mwojcik.wishplaces.Fragments.MapFragment;
import com.example.mwojcik.wishplaces.Fragments.WishPlaceListFragment;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.Utils.AppValues;
import com.example.mwojcik.wishplaces.Utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //Bindowanie elementow widoku za pomoca biblioteki ButterKnife
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.add_new_place_button) Button addNewPlaceBtn;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Jezeli chcemy zablokowac mozliwosc rotacji ekranu glownego, to wystarczy to odkomentowac.
//        SharedPreferences preferences = getSharedPreferences(AppValues.PREFERENCES_NAME, MODE_PRIVATE);
//        if(preferences.getInt(AppValues.PREFERENCES_DEVICE_TYPE, 0) == 0){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

        //Dodajemy listenery do elementow widoku
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        addNewPlaceBtn.setOnClickListener(addNewPlaceButton);

        setViewPager(viewPager);
    }

    //Listener kliknięcia przycisku - przenosi nas do Activity dodawania nowego miejsca
    private View.OnClickListener addNewPlaceButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, NewWishPlaceActivity.class);
            startActivity(intent);
        }
    };

    //Przypisanie fragmentow z adaptera do zakladki
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    // Listener wybrania zakładki
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            }
            else
            {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            navigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(position);

            // Ukrycie przycisku jezeli przejdziemy na zakladke z mapa
            if(position == 1){
                addNewPlaceBtn.setVisibility(View.INVISIBLE);
            } else {
                addNewPlaceBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    // Przypisanie fragmentow do adaptera -> tutaj dodajemy nowe zakladki
    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        WishPlaceListFragment placeListFragment = new WishPlaceListFragment();
        MapFragment mapFragment = new MapFragment();
        adapter.addFragment(placeListFragment);
        adapter.addFragment(mapFragment);
        viewPager.setAdapter(adapter);
    }

}
