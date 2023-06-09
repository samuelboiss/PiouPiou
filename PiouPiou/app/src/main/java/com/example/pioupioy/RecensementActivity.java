package com.example.pioupioy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.util.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecensementActivity extends AppCompatActivity {
    private static final String CHANNEL_1_ID = "channel LOW";
    public static final String CHANNEL_2_ID = "channel DEFAULT";
    public static final String CHANNEL_3_ID = "channel HIGH";

    private static final int REQUEST_CODE_MAP = 1;

    private GeoPoint address;

    // Déclaration de la base de données Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannels();
        setContentView(R.layout.activity_recensement);

        // Récupération de l'adresse de l'utilisateur
        ImageButton addressButton = findViewById(R.id.location_button);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMapActivity();
            }
        });

        Button enregistrer = findViewById(R.id.enregistrer);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données du formulaire
                Spinner typeSpinner = findViewById(R.id.spinner_type);
                String type = typeSpinner.getSelectedItem().toString();

                SimpleDateFormat parse = new SimpleDateFormat("dd/MM/yyyy");
                Date time = new Date();
                String date = parse.format(time);


                EditText especeEditText = findViewById(R.id.espece);
                String espece = especeEditText.getText().toString();

                EditText nombreEditText = findViewById(R.id.nombre);
                int nombre = Integer.parseInt(nombreEditText.getText().toString());

                Spinner directionSpinner = findViewById(R.id.spinner_direction);
                String direction = directionSpinner.getSelectedItem().toString();

                Spinner meteoSpinner = findViewById(R.id.spinner_meteo);
                String meteo = meteoSpinner.getSelectedItem().toString();

                Spinner huntableSpinner = findViewById(R.id.spinner_huntable);
                String huntable = huntableSpinner.getSelectedItem().toString();

                Intent geoPoint = getIntent();
                address = new GeoPoint(geoPoint.getDoubleExtra("latitude", 0), geoPoint.getDoubleExtra("longitude", 0));


                Map<String, Object> census = new HashMap<>();
                census.put("date", date);
                census.put("type", type);
                census.put("espece", espece);
                census.put("nombre", nombre);
                census.put("direction", direction);
                census.put("meteo", meteo);
                census.put("chassable", huntable);
                census.put("address", address);


                // Ajout des données dans la base de données Firestore census
                db.collection("census")
                        .add(census)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Affichage d'un message de succès
                                System.out.println("Census ajouté avec ID: " + documentReference.getId());
                                sendNotificationOnChannel("Recensement", "Vous avez bien ajouter un recensement", "LOW");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Affichage d'un message d'erreur
                                System.out.println("Erreur lors de l'ajout du census" + e.getMessage());
                                sendNotificationOnChannel("Recensement", "Votre recensement n'a pas été ajouté", "LOW");

                            }
                        });
                // Redirection vers la page de la carte
                Intent intent = new Intent(RecensementActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        ImageView retour = findViewById(R.id.back_pressed_button);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Créer le NotificationChannel, seulement pour API 26+
            NotificationChannel channel1 = createNotificationChannel(CHANNEL_1_ID, "Channel 1",
                    NotificationManager.IMPORTANCE_LOW,
                    "This Channel has low priority");
            NotificationChannel channel2 = createNotificationChannel(CHANNEL_2_ID, "Channel 2",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "This Channel has default priority");
            NotificationChannel channel3 = createNotificationChannel(CHANNEL_3_ID, "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH,
                    "This Channel has high priority");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(channel1);
            Objects.requireNonNull(manager).createNotificationChannel(channel2);
            Objects.requireNonNull(manager).createNotificationChannel(channel3);
        }
    }

    private NotificationChannel createNotificationChannel(String channelId, CharSequence name, int importance, String
            channelDescription) {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(channelDescription);
            return channel;
        }
        return null;
    }

    private void sendNotificationOnChannel(String title, String content, String channelId) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pioupiou);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.pioupiou)
                .setLargeIcon(largeIcon);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "notif", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification.build());
    }


    private void launchMapActivity() {
        Intent intent = new Intent(this, PlaceMarkerActivity.class);
        startActivity(intent);
    }
}
