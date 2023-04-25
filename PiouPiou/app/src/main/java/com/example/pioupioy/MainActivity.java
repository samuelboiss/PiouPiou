package com.example.pioupioy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn2).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(), inscrire.class);
            startActivity(intent);
        });

        findViewById(R.id.label3).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(), Oublimdp.class);
            startActivity(intent);
        });
    }
}
