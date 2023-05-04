package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Oublimdp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mPassword;
    private EditText mPasswordConfirm;


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
