package com.example.pioupioy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Console;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {

    private EditText mEditTextMail;
    private EditText mEditTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        mEditTextMail = findViewById(R.id.textbox);
        mEditTextPassword = findViewById(R.id.textbox2);

        findViewById(R.id.btn2).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), Inscrire.class);
            System.out.println(intent);
            startActivity(intent);
        });

        findViewById(R.id.label3).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), Oublimdp.class);
            startActivity(intent);
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEditTextMail.getText().toString();
                String password = mEditTextPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Erreur lors de la connexion : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }
}
