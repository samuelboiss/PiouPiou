package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
            }
        });


    }
}
