package com.example.pioupioy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;


// add implementation 'org.osmdroid:osmdroid-android:6.0.2' in gradle dependencies
public class MapActivity extends AppCompatActivity {

    private MapView map;

    // TODO : change for geolocation
    private static final double DEFAULT_LATITUDE = 43.6152209;
    private static final double DEFAULT_LONGITUDE = 7.0727436;

    private Marker addMarker(BirdCensus birdCensus, GeoPoint geoPoint) {
        Marker customMarker = new Marker(map);
        if (birdCensus.getType().equals(ItemType.CENSUS)) {
            customMarker.setIcon(getDrawable(R.drawable.bird_identification_icon));
        } else if (birdCensus.getType().equals(ItemType.OBSERVATION)) {
            customMarker.setIcon(getDrawable(R.drawable.observation_site_icon));
        }
        customMarker.setPosition(geoPoint);
        customMarker.setPanToView(true);

        customMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                BirdCensusInfo fragment = new BirdCensusInfo(birdCensus);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.birdCensus_info, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                findViewById(R.id.birdCensus_info).setVisibility(View.VISIBLE);
                return false;
            }
        });
        return customMarker;
    }

    private void setMarker() {
        // TODO : change the real data
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pioupiou);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        map.getOverlays().add(addMarker(new BirdCensus("Pigeon de test",
                                3,
                                new Date(),
                                ItemType.CENSUS,
                                byteArray,
                                true, "Beau", "Sud"
                        ), new GeoPoint(43.6185609290512, 7.073302138130848)
                )
        );

        map.getOverlays().add(addMarker(new BirdCensus("Observatoire à Pigeon de tests",
                                3,
                                new Date(),
                                ItemType.OBSERVATION,
                                byteArray,
                                true, "Beau", "Sud"
                        ), new GeoPoint(43.6185609290512, 7.063302138130848)
                )
        );
    }

    private void setupMap() {
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
    }

    private void setMapCenterPosition(double latitude, double longitude) {
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        IMapController mapController = (MapController) map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.map_page);

        findViewById(R.id.birdCensus_info).setVisibility(View.INVISIBLE);
        findViewById(R.id.navBar).setVisibility(View.INVISIBLE);

        setupMap();

        setMapCenterPosition(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);

        setMarker();

        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.navBar).setVisibility(View.VISIBLE);
                map.setClickable(false);
                map.setEnabled(false);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewGroup parent = (ViewGroup) map.getParent();
        parent.removeView(map);
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.birdCensus_info).setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }

}

