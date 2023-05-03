package com.example.pioupioy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn2).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), inscrire.class);
            System.out.println(intent);
            startActivity(intent);
        });

        findViewById(R.id.label3).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), oublimdp.class);
            startActivity(intent);
        });

        findViewById(R.id.btn).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        });

    }
}
