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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Inscrire extends AppCompatActivity {
    public static final String CHANNEL_1_ID ="channel LOW" ;
    public static final String CHANNEL_2_ID = "channel DEFAULT";
    public static final String CHANNEL_3_ID = "channel HIGH";
    private FirebaseAuth mAuth;
    private EditText mEditTextPseudo;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditPassWordConfirm;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscrire);

        mAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);

        mEditTextPseudo = findViewById(R.id.textbox4);
        mEditTextEmail = findViewById(R.id.textbox);
        mEditTextPassword = findViewById(R.id.textbox2);
        mEditPassWordConfirm = findViewById(R.id.textbox3);
        mButtonRegister = findViewById(R.id.btn);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = mEditTextPseudo.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                String passwordConfirm = mEditPassWordConfirm.getText().toString();

                if (pseudo.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(Inscrire.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else if (password.equals(passwordConfirm)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Inscrire.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        sendNotificationOnChannel("Inscription","Vous avez bien réussi votre inscription", "LOW");
                                    } else {
                                        String error = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(Inscrire.this, "Erreur : " + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Inscrire.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }


    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Créer le NotificationChannel, seulement pour API 26+
            NotificationChannel channel1 = createNotificationChannel( CHANNEL_1_ID,"Channel 1",
                    NotificationManager.IMPORTANCE_LOW,
                    "This Channel has low priority");
            NotificationChannel channel2 = createNotificationChannel( CHANNEL_2_ID,"Channel 2",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    "This Channel has default priority");
            NotificationChannel channel3 = createNotificationChannel( CHANNEL_3_ID,"Channel 2",
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

/*
        findViewById(R.id.btn).setOnClickListener( clic -> {
            String email = findViewById(R.id.textbox).toString();
            String password = findViewById(R.id.textbox3).toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Rediriger vers la page d'accueil
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            System.out.println(intent);
                            startActivity(intent);
                        } else {
                            // Afficher un message d'erreur
                            Toast.makeText(Inscrire.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
    */

