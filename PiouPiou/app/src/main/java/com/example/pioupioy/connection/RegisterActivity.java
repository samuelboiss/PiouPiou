package com.example.pioupioy.connection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pioupioy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEditTextPseudo;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditPassWordConfirm;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(this);

        mEditTextPseudo = findViewById(R.id.insertPseudoEdit);
        mEditTextEmail = findViewById(R.id.insertEmailEdit);
        mEditTextPassword = findViewById(R.id.insertPasswordEdit);
        mEditPassWordConfirm = findViewById(R.id.confirmPasswordEdit);
        mButtonRegister = findViewById(R.id.createAccountButton);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = mEditTextPseudo.getText().toString();
                String email = mEditTextEmail.getText().toString();
                String password = mEditTextPassword.getText().toString();
                String passwordConfirm = mEditPassWordConfirm.getText().toString();

                if (pseudo.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else if (password.equals(passwordConfirm)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ConnectionActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String error = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(RegisterActivity.this, "Erreur : " + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }
}

/*
        findViewById(R.id.btn).setOnClickListener( clic -> {
            String email = findViewById(R.id.textbox).toString();
            String password = findViewById(R.id.textbox3).toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Rediriger vers la page d'accueil
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            System.out.println(intent);
                            startActivity(intent);
                        } else {
                            // Afficher un message d'erreur
                            Toast.makeText(Inscrire.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
    */

