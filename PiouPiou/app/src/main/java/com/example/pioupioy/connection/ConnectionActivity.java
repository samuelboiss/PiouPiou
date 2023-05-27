package com.example.pioupioy.connection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.example.pioupioy.MapActivity;
import com.example.pioupioy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;



public class ConnectionActivity extends AppCompatActivity {

    private EditText mEditTextMail;
    private EditText mEditTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mAuth = FirebaseAuth.getInstance();


        mEditTextMail = findViewById(R.id.insertEmailEdit);
        mEditTextPassword = findViewById(R.id.insertPasswordEdit);

        findViewById(R.id.registerButton).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            System.out.println(intent);
            startActivity(intent);
        });

        findViewById(R.id.forget_password).setOnClickListener(clic -> {
            Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.connectionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEditTextMail.getText().toString();
                String password = mEditTextPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(ConnectionActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ConnectionActivity.this, "Erreur lors de la connexion : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ConnectionActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
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
