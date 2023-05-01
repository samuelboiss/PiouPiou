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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;
import java.util.logging.Logger;

public class Ajouter extends AppCompatActivity {
    private static final String CHANNEL_1_ID = "channel LOW" ;
    public static final String CHANNEL_2_ID = "channel DEFAULT";
    public static final String CHANNEL_3_ID = "channel HIGH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannels();
        setContentView(R.layout.ajouter);
        findViewById(R.id.ajouter_un_recensement).setOnClickListener( clic -> {
            //Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            //startActivity(intent);
            sendNotificationOnChannel("Recensement","Vous avez bien ajouter un recensement", "LOW");
        });
    }

    private void createNotificationChannels() {
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

}
