package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Oublimdp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oublimdp);
        findViewById(R.id.btn).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            System.out.println(intent);
            startActivity(intent);
        });
    }
}