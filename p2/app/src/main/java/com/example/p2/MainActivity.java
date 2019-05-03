package com.example.p2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_weather:
                    toolbar.setTitle("weather there ...");
                    fragment = new WeatherFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_topack:
                    toolbar.setTitle("to pack ...");
                    fragment = new ToPackFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_remember:
                    toolbar.setTitle("do not forget ...");
                    fragment = new RememberFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get toolbar + set app bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar color
        //https://www.youtube.com/watch?v=1DoA7aJplKA
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#494949")));


        //load 1st frag
        getSupportActionBar().setTitle("weather");
        loadFragment(new WeatherFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment){
        if (fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragContainer, fragment);
            transaction.commit();
        }
    }
}
