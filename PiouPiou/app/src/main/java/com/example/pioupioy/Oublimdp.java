package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Oublimdp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oublimdp);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.textbox);

        findViewById(R.id.btn).setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
                String email = mEmailField.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(Oublimdp.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Oublimdp.this, "Un Email de reinitialisation vous a été envoyé", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Oublimdp.this, "L'Email n'existe pas", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}
