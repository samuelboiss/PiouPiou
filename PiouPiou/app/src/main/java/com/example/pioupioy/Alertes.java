package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pioupioy.connection.ConnectionActivity;

public class Alertes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertes);
        findViewById(R.id.retour2).setOnClickListener( clic -> {
            System.out.println("1");
            Intent intent=new Intent(getApplicationContext(), ConnectionActivity.class);
            System.out.println(intent);
            startActivity(intent);
        });
    }
}
