package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;


// add implementation 'org.osmdroid:osmdroid-android:6.0.2' in gradle dependencies
public class MapActivity extends AppCompatActivity {
    private MapView map;
    private boolean isSelectedLocalisation = false;
    private boolean isSelectedBirdEvent = false;

    // TODO : change for geolocation
    private static final double DEFAULT_LATITUDE = 43.6152209;
    private static final double DEFAULT_LONGITUDE = 7.0727436;

    private Marker addMarker(BirdCensus birdCensus, GeoPoint geoPoint) {
        Marker customMarker = new Marker(map);
        if (birdCensus.getType().equals(BirdEventType.CENSUS)) {
            customMarker.setIcon(getDrawable(R.drawable.bird_identification_icon));
        } else if (birdCensus.getType().equals(BirdEventType.OBSERVATION)) {
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
//        // TODO : change the real data
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pioupiou);
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//
//        map.getOverlays().add(addMarker(new BirdCensus("Pigeon de test",
//                                3,
//                                new Date(),
//                                BirdEventType.CENSUS,
//                                byteArray,
//                                true, "Beau", "Sud"
//                        ), new GeoPoint(43.6185609290512, 7.073302138130848)
//                )
//        );
//
//        map.getOverlays().add(addMarker(new BirdCensus("Observatoire à Pigeon de tests",
//                                3,
//                                new Date(),
//                                BirdEventType.OBSERVATION,
//                                byteArray,
//                                true, "Beau", "Sud"
//                        ), new GeoPoint(43.6185609290512, 7.063302138130848)
//                )
//        );
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

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.hasExtra("selectLocalisation")) {
                isSelectedLocalisation = intent.getBooleanExtra("selectLocalisation", false);
            } else if (intent.hasExtra("selectBirdEvent")) {
                isSelectedBirdEvent = intent.getBooleanExtra("selectBirdEvent", false);
            }
        }

        if (isSelectedLocalisation) {
            updateGeoPoint();
        }
        if (isSelectedBirdEvent) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                BirdEvent birdEvent = bundle.getParcelable("bird_event");
                if (birdEvent != null) {
                    double latitude = birdEvent.getLatitude();
                    double longitude = birdEvent.getLongitude();
                    setMapCenterPosition(latitude, longitude);
                }
            }
        }





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

    private void updateGeoPoint() {
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(MapActivity.this, "SINGLE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapActivity.this, Recensement.class);
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

