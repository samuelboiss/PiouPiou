package com.example.pioupioy.controller.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.pioupioy.BirdCensus;
import com.example.pioupioy.BirdEvent;
import com.example.pioupioy.BirdEventDraftAdapter;
import com.example.pioupioy.BirdEventInfo;
import com.example.pioupioy.BirdObservation;
import com.example.pioupioy.DraftActivity;
import com.example.pioupioy.R;
import com.example.pioupioy.controller.observer.IObservable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkerController {
    private final List<Marker> markerList = new ArrayList<>();
    private final List<IObservable> observers = new ArrayList<>();
    private final Context context;
    private final MapView map;
    private final FragmentActivity activity;

    public MarkerController(Context context, MapView mapView, FragmentActivity activity) {
        this.context = context;
        this.map = mapView;
        this.activity = activity;

        searchToDatabase();
    }

    private Marker addMarkerBirdCensus(BirdCensus birdCensus) {
        Marker customMarker = new Marker(map);
        customMarker.setIcon(context.getDrawable(R.drawable.bird_identification_icon));
        customMarker.setPosition(birdCensus.getAddress());
        customMarker.setPanToView(true);

        customMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                BirdEventInfo fragment = new BirdEventInfo(birdCensus);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.birdCensus_info, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                activity.findViewById(R.id.birdCensus_info).setVisibility(View.VISIBLE);
                return false;
            }
        });
        return customMarker;
    }

    private Marker addMarkerBirdObservation(BirdObservation birdObservation) {
        Marker customMarker = new Marker(map);
        customMarker.setIcon(context.getDrawable(R.drawable.observation_site_icon));
        customMarker.setPosition(birdObservation.getAddress());
        customMarker.setPanToView(true);

        customMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                BirdEventInfo fragment = new BirdEventInfo(birdObservation);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.birdCensus_info, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                activity.findViewById(R.id.birdCensus_info).setVisibility(View.VISIBLE);
                return false;
            }
        });
        return customMarker;
    }


    public List<Marker> getMarkerList() {
        return markerList;
    }

    public void addObservers(IObservable observable) {
        observers.add(observable);
    }

    private void notifyObservers() {
        for (IObservable observable : observers) {
            observable.updateMarker(this);
        }
    }

    private void searchToDatabase() {
        markerList.clear();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_default_icon);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionBirdEvent = db.collection("census");

        collectionBirdEvent.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<BirdEvent> birdEventList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Map<String, Object> data = document.getData();

                            String name = (String) data.get("espece");
                            long number = (long) data.get("nombre");
                            String date = (String) data.get("date");
                            String type = (String) data.get("type");
                            String weather = (String) data.get("meteo");
                            String direction = (String) data.get("direction");
                            String huntable = (String) data.get("chassable");
                            HashMap<String, Object> address = data.get("address") != null ? (HashMap<String, Object>) data.get("address") : null;
                            GeoPoint location = new GeoPoint(0.0, 0.0);

                            if (address != null) {
                                double latitude = (double) address.get("latitude");
                                double longitude = (double) address.get("longitude");
                                location = new GeoPoint(latitude, longitude);
                            }

                            boolean isHuntable;

                            assert huntable != null;
                            if (huntable.equals("oui")) {
                                isHuntable = true;
                            } else {
                                isHuntable = false;
                            }

                            assert type != null;
                            if (type.equals("census")) {
                                BirdCensus birdCensus = new BirdCensus(name, (int) number, date, byteArray, isHuntable, location, direction, weather);
                                markerList.add(addMarkerBirdCensus(birdCensus));
                            } else if (type.equals("observation")) {
                                BirdObservation birdObservation = new BirdObservation(name, (int) number, date, byteArray, isHuntable, location, direction, weather);
                                markerList.add(addMarkerBirdObservation(birdObservation));
                            }
                        }
                    }
                    notifyObservers();
                }
            }
        });
    }

}
