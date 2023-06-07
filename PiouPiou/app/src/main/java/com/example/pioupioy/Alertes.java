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
        findViewById(R.id.back_pressed_button).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        });
    }
}
