package com.example.pioupioy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pioupioy.connection.ConnectionActivity;
import com.example.pioupioy.utils.ImageUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.util.GeoPoint;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DraftActivity extends AppCompatActivity {
    private final List<BirdEvent> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

//        addBirdEvent(new BirdCensus("Pigeon", 4, "20/02/2015", null, true, new GeoPoint(0.0, 0.0), "Nord", "Soleil"));
//        addBirdEvent(new BirdCensus("Pie", 4, "20/02/2015", null, true, new GeoPoint(0.0, 0.0), "Nord", "Soleil"));

        fillDraftList();

        findViewById(R.id.back_pressed_button).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        });
    }

    private void addBirdEvent(BirdEvent birdEvent) {
        eventList.add(birdEvent);
    }

    private void fillDraftList() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bird_default_icon);

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
                            HashMap<String, Object> address =  data.get("address") != null ? (HashMap<String, Object>) data.get("address") : null;
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
                                BirdCensus birdCensus = new BirdCensus(name,(int) number, date, byteArray, isHuntable, location, direction, weather);
                                birdEventList.add(birdCensus);
                            } else if (type.equals("observation")) {
                                BirdObservation birdObservation = new BirdObservation(name, (int) number, date, byteArray, isHuntable, location, direction, weather);
                                    birdEventList.add(birdObservation);

                            } else {
                                Toast.makeText(DraftActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    ListView listView = (ListView) findViewById(R.id.draft_list);
                    BirdEventDraftAdapter eventListAdapter = new BirdEventDraftAdapter(getApplicationContext(), birdEventList);
                    listView.setAdapter(eventListAdapter);
                } else {
                    Toast.makeText(DraftActivity.this, "Erreur", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
