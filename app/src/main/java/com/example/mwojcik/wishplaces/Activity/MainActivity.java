package com.example.mwojcik.wishplaces.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mwojcik.wishplaces.Fragments.MapFragment;
import com.example.mwojcik.wishplaces.Fragments.WishPlaceListFragment;
import com.example.mwojcik.wishplaces.R;
import com.example.mwojcik.wishplaces.Utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ViewPager viewPager;
    private BottomNavigationView navigation;

    private Button addNewPlaceBtn;

    private MenuItem prevMenuItem;

    private WishPlaceListFragment placeListFragment;
    private MapFragment mapFragment;

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

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            }
            else
            {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: "+position);
            navigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(position);

            if(position == 1){
                addNewPlaceBtn.setVisibility(View.INVISIBLE);
            } else {
                addNewPlaceBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(mOnPageChangeListener);

        addNewPlaceBtn = (Button) findViewById(R.id.add_new_place_button);
        addNewPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWishPlaceActivity.class);
                startActivity(intent);
            }
        });

        setViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        placeListFragment = new WishPlaceListFragment();
        mapFragment = new MapFragment();
        adapter.addFragment(placeListFragment);
        adapter.addFragment(mapFragment);
        viewPager.setAdapter(adapter);

    }

}
