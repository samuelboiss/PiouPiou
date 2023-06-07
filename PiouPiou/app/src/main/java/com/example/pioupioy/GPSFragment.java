package com.example.pioupioy;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.Marker;

import org.osmdroid.util.GeoPoint;
import java.io.IOException;
import java.util.List;
import java.util.Locale;



/**
 * this fragment is able to manage autorisation for location module (GPS)
 * It can get the GPS position in a LatLng
 * It can get the placeName to the GPS position (nearest city)
 * It can force to display a placeName in the TextView
 *

 */
public class GPSFragment extends Fragment {
    private final String TAG = "fredrallo " + getClass().getSimpleName();
    private IGPS gpsActivity;
    private Location currentLocation;
    private TextView textViewPlaceName;
    private ImageView imageGPSGranted;
    private ImageView imageGPSActivated;

    public GPSFragment() { }

    private IMap mapFragment;
    private MapFragment map;





    @Override
    /**
     * Call the method that creating callback after being attached to parent activity
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create callback to parent activity
        try {
            map = (MapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
            gpsActivity = (IGPS) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement IGPSActivity");
        }
    }

    private void sendCurrentLocationToMapFragment() {
        if (currentLocation != null && mapFragment != null) {
            GeoPoint currentLocationPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.addMarker(org.osmdroid.library.R.drawable.osm_ic_follow_me_on, currentLocationPoint, "Current Location", "", org.osmdroid.library.R.drawable.osm_ic_follow_me_on);
        }
    }

    public void refresh(){
        //check if GPS permission is already GRANTED
        boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Log.d(TAG, "permissionGranted = " + permissionGranted);
        if (permissionGranted) {
            imageGPSGranted.setImageResource(R.drawable.gpson);
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    currentLocation = location;
                    gpsActivity.moveCamera();
                    sendCurrentLocationToMapFragment(); // Envoyer la position actuelle à MainActivity/MapFragment
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Log.d(TAG, "satus changed=" + s);
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.d(TAG, s+" sensor ON");
                    imageGPSActivated.setImageResource(R.drawable.unlocked);
                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.d(TAG, s+" sensor OFF");
                    imageGPSActivated.setImageResource(R.drawable.locked);
                }
            };
            LocationManager locationManager = (LocationManager) (getActivity().getSystemService(LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, listener);
            // map.addMarker(org.osmdroid.library.R.drawable.osm_ic_follow_me_on, new GeoPoint(43.36719,7.4707), "lisa",  "my teacher", R.drawable.work );
            // je veux utilise l'exemple en commentaire pour mettre un marker sur la position  actuelle

            imageGPSActivated.setImageResource( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ? R.drawable.unlocked : R.drawable.locked );

        }else {
            //GPS permission is still not GRANTED
            imageGPSGranted.setImageResource(R.drawable.gpsoff);
            imageGPSActivated.setImageResource(R.drawable.locked);
            Log.d(TAG, "Permission NOT GRANTED  ! ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gps, container, false);
        textViewPlaceName = rootView.findViewById(R.id.placeName);
        imageGPSGranted = rootView.findViewById( R.id.imageGPSGranted );
        imageGPSActivated = rootView.findViewById(R.id.imageGPSActivated);

        refresh();
        return rootView;
    }


    GeoPoint getPosition() {
        return new GeoPoint(  currentLocation.getLatitude(), currentLocation.getLongitude() );
    }


    String getPlaceName() throws IOException {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        String cityName = addresses.get(0).getLocality();
        return cityName;
    }

    void setPlaceName(String placeName ) {
        textViewPlaceName.setText( placeName );
    }
}
