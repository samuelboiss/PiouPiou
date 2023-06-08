package com.example.pioupioy;

import org.osmdroid.util.GeoPoint;

public interface IGPS {
   int REQUEST_CODE = 400;  // request code used in requestPermissions() méthode and its callback onRequestPermissionsResult()
    void moveCamera();      // move camera (with zoom) to center the map to the GPS position
    void addMarkerToMap(GeoPoint location);

    }
