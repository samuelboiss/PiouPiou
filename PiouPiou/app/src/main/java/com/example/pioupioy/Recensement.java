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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Recensement extends AppCompatActivity {

    // Déclaration de la base de données Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recensement);

        Button enregistrer = findViewById(R.id.enregistrer);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des données du formulaire
                EditText dateEditText = findViewById(R.id.date);
                String date = dateEditText.getText().toString();

                EditText espèceEditText = findViewById(R.id.espece);
                String espece = espèceEditText.getText().toString();

                EditText nombreEditText = findViewById(R.id.nombre);
                int nombre = Integer.parseInt(nombreEditText.getText().toString());

                Spinner directionSpinner = findViewById(R.id.spinner_direction);
                String direction = directionSpinner.getSelectedItem().toString();

                Spinner meteoSpinner = findViewById(R.id.spinner_meteo);
                String meteo = meteoSpinner.getSelectedItem().toString();

                Spinner huntableSpinner = findViewById(R.id.spinner_huntable);
                String huntable = huntableSpinner.getSelectedItem().toString();

                Map<String, Object> census = new HashMap<>();
                census.put("date", date);
                census.put("espece", espece);
                census.put("nombre", nombre);
                census.put("direction", direction);
                census.put("meteo", meteo);
                census.put("chassable", huntable);


                // Ajout des données dans la base de données Firestore census
                db.collection("census")
                        .add(census)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Affichage d'un message de succès
                                System.out.println("Census ajouté avec ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Affichage d'un message d'erreur
                                System.out.println("Erreur lors de l'ajout du census" + e.getMessage());
                            }
                        });
                // Redirection vers la page de la carte
                Intent intent = new Intent(Recensement.this, MapActivity.class);
                startActivity(intent);
                sendNotificationOnChannel("Recensement","Vous avez bien réussi votre recensement", "LOW");

            }
        });
    }
    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Créer le NotificationChannel, seulement pour API 26+
            NotificationChannel channel1 = createNotificationChannel( Inscrire.CHANNEL_1_ID,"Channel 1",
                    NotificationManager.IMPORTANCE_LOW,
                    "This Channel has low priority");
            NotificationChannel channel2 = createNotificationChannel( Inscrire.CHANNEL_2_ID,"Channel 2",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "This Channel has default priority");
            NotificationChannel channel3 = createNotificationChannel( Inscrire.CHANNEL_3_ID,"Channel 2",
                    NotificationManager.IMPORTANCE_HIGH,
                    "This Channel has high priority");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(channel1);
            Objects.requireNonNull(manager).createNotificationChannel(channel2);
            Objects.requireNonNull(manager).createNotificationChannel(channel3);
        }
    }

    public NotificationChannel createNotificationChannel(String channelId, CharSequence name, int importance, String
            channelDescription) {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(channelDescription);
            return channel;
        }
        return null;
    }

    public void sendNotificationOnChannel(String title, String content, String channelId) {
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
}
