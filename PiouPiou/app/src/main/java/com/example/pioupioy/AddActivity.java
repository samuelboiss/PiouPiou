package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViewById(R.id.add_census).setOnClickListener(clic -> {
            Intent intent=new Intent(getApplicationContext(), RecensementActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.add_contact).setOnClickListener( clic -> {
            Intent intent=new Intent(getApplicationContext(), AddContactActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.back_pressed_button).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        });
    }



}
