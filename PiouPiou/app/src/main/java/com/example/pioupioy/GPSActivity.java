package com.example.pioupioy;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;

/**
 * display a googleMap and listen to to GPS sensor
 * A GPSFragment manage the location module (GPS)
 * A NavigationFragment control the user
 * This activity implements IGPSActivity, so it can moveCamera() to center map to GPS location
 *
 * This application needs Google API service (menu Files/setting/sdk  --> sdk tools --> Google Play Services
 **/

public class GPSActivity extends AppCompatActivity implements IGPS, IMap {
    private final String TAG = "fredrallo " + getClass().getSimpleName();
    private GPSFragment gps;
    private NavigationFragment navigation;
    private MapFragment map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        gps =  (GPSFragment) getSupportFragmentManager().findFragmentById(R.id.gpsLocation);
        navigation = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation);
        map = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.addMarker(org.osmdroid.library.R.drawable.osm_ic_follow_me_on, new GeoPoint(43.36719,7.4707), "lisa",  "my teacher", R.drawable.gpson );
    }


    public void moveCamera() {
        Log.d( TAG, "camera moved");
        try {
            gps.setPlaceName( "Ville: " + gps.getPlaceName() );
        } catch (IOException e) {
            Log.d( TAG, "--> getPlaceName not found");
            gps.setPlaceName( getString(R.string.placeName) );
        }
        //TODO change 15f

        map.moveCamera( gps.getPosition(),15f );
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode=" + requestCode);

        switch (requestCode) {
            case REQUEST_CODE: {  //GPS FINE LOCATION only autorisation result code
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE authorisation Granted", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d(TAG, "(only) FINE LOCATION permission Granted");
                } else {
                    Log.d(TAG, "(only) FINE LOCATION permission NOT Granted");
                }
            }
            break;
        }

        //refresh display
        gps.refresh();
    }


    @Override
    public void onMapClicked(GeoPoint geoPoint) {
        Log.d(TAG, "map click = " + geoPoint);
    }
}
