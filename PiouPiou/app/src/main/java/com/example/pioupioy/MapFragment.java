package com.example.pioupioy;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class MapFragment extends Fragment {
    private final String TAG = "fredrallo " + getClass().getSimpleName();
    private MapView map;
    private IMapController mapController;
    private IMap mapActivity;


    public MapFragment(){
    }

    @Override
    /**
     * Call the method that creating callback after being attached to parent activity
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create callback to parent activity
        try {
            mapActivity = (IMap) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement IGPSActivity");
        }
    }


    public void addMarker(int icon, GeoPoint location, String title, String description, int imageResource){
        Marker marker = new Marker(map);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getActivity().getDrawable(icon));
        marker.setPosition(location);
        marker.setTitle(title);
        marker.setSubDescription(description);
        marker.setImage(getActivity().getDrawable(imageResource));
        marker.setPanToView(true);  //the map will be centered on the marker position.
        marker.setDraggable(true);
        map.getOverlays().add( marker );
    }


    public void moveCamera(GeoPoint geoPoint, float zoom){
        mapController.setCenter(geoPoint);
        mapController.setZoom(zoom);
        mapController.setCenter(geoPoint);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration.getInstance().load(   getActivity().getApplicationContext(),  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()) );
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        map = rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);    //render
        map.setBuiltInZoomControls(true);               // zoomable
        map.setMultiTouchControls(true);                //  zoom with 2 fingers

        //can'y be tested with emulator...
        map.setOnClickListener( clic -> {
            GeoPoint clickedPoint = (GeoPoint) map.getProjection().fromPixels((int) clic.getX(), (int) clic.getY());
            Log.d(TAG, "clicked: "+clickedPoint);
            mapActivity.onMapClicked(clickedPoint);
        });

        mapController = map.getController();
        return rootView;
    }
}
