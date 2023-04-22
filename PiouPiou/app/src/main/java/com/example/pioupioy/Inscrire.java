package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class Inscrire extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscrire);
        findViewById(R.id.btn).setOnClickListener( clic -> {
            System.out.println("1");
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            System.out.println(intent);
            startActivity(intent);
        });
    }
}
