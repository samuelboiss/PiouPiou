package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class PlaceMarkerActivity extends AppCompatActivity {
    private MapView map;

    // TODO : change for geolocation
    private static final double DEFAULT_LATITUDE = 43.6152209;
    private static final double DEFAULT_LONGITUDE = 7.0727436;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_choose_localisation);

        setupMap();

        setMapCenterPosition(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);

        updateGeoPoint();
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

    private void setupMap() {
        map = findViewById(R.id.map_point);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    private void setMapCenterPosition(double latitude, double longitude) {
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        IMapController mapController = (MapController) map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

    }

    private void updateGeoPoint() {
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(PlaceMarkerActivity.this, "SINGLE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PlaceMarkerActivity.this, Recensement.class);
                intent.putExtra("latitude", p.getLatitude());
                intent.putExtra("longitude", p.getLongitude());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getBaseContext(), mReceive);
        map.getOverlays().add(mapEventsOverlay);

    }
}
