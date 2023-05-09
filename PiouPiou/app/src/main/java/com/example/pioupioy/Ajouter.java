package com.example.pioupioy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;
import java.util.logging.Logger;

public class Ajouter extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter);
        findViewById(R.id.ajouter_un_recensement).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(),Recensement.class);
            startActivity(intent);
        });

        findViewById(R.id.ajouter_un_contact).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(),AjouterContact.class);
            startActivity(intent);
        });
    }



}
