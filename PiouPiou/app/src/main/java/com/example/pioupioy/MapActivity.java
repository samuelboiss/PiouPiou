package com.example.pioupioy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pioupioy.controller.map.MainMapController;

import org.osmdroid.config.Configuration;


public class MapActivity extends AppCompatActivity {
    private static final double DEFAULT_LATITUDE = 43.6152209;
    private static final double DEFAULT_LONGITUDE = 7.0727436;
    private boolean isSelectedBirdEvent = false;

    private MainMapController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map_page);

        findViewById(R.id.birdCensus_info).setVisibility(View.INVISIBLE);
        findViewById(R.id.navBar).setVisibility(View.INVISIBLE);

        this.controller = new MainMapController(this, findViewById(R.id.map), this);
        controller.initMap(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.navBar).setVisibility(View.VISIBLE);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("selectBirdEvent")) {
                isSelectedBirdEvent = intent.getBooleanExtra("selectBirdEvent", false);
            }
        }
        if (isSelectedBirdEvent) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                BirdEvent birdEvent = bundle.getParcelable("bird_event");
                if (birdEvent != null) {
                    double latitude = birdEvent.getLatitude();
                    double longitude = birdEvent.getLongitude();
                    controller.initMap(latitude, longitude);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        controller.getMap().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getMap().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewGroup parent = (ViewGroup) controller.getMap().getParent();
        parent.removeView(controller.getMap());
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.birdCensus_info).setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

}

