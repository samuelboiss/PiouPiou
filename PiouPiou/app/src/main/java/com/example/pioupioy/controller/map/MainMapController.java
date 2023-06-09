package com.example.pioupioy.controller.map;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.example.pioupioy.controller.observer.IObservable;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainMapController implements IObservable {
    private final MapView map;

    private final MarkerController markerController;

    public MainMapController(Context context, MapView map, FragmentActivity activity) {
        this.map = map;
        this.markerController = new MarkerController(context, map, activity);
        this.markerController.addObservers(this);
    }

    public MapView getMap() {
        return map;
    }

    @Override
    public void updateMarker(MarkerController markerController) {
        map.getOverlays().clear();
        for (Marker marker : this.markerController.getMarkerList()) {
            map.getOverlays().add(marker);
        }
        map.invalidate();

    }


    public void initMap(double latitude, double longitude) {
        setupMap();
        setMapCenterPosition(latitude, longitude);
        setMarker();
    }

    public void setupMap() {
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    public void setMapCenterPosition(double latitude, double longitude) {
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

    }

    public void setMarker() {
        for (Marker marker : markerController.getMarkerList()) {
            map.getOverlays().add(marker);
        }
    }
}
