package com.example.pioupioy;



import static com.example.pioupioy.IGPS.REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


public class NavigationFragment extends Fragment {
    private final String TAG = "fredrallo " + getClass().getSimpleName();

    /**
     * Required empty public constructor
     */
    public NavigationFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigation, container, false);

        rootView.findViewById(R.id.buttonCheckPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"check permissions");
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"return in MainActivity (hope)");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                }
            }
        });


        rootView.findViewById(R.id.buttonSwitchOnOffGPS).setOnClickListener( click -> {
                Toast toast = Toast.makeText(getContext(), "You can change now", Toast.LENGTH_LONG);
                toast.show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
        });

        rootView.findViewById(R.id.buttonSettings).setOnClickListener( click -> {
                Toast toast = Toast.makeText(getContext(), "custumize authorisations", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getActivity().getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        });

        return rootView;
    }



}