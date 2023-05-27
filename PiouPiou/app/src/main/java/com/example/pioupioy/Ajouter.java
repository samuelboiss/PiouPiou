package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
