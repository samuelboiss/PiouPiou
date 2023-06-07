package com.example.pioupioy.connection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pioupioy.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.insertEmailEdit);

        findViewById(R.id.reinitialisationButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String email = mEmailField.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Un email de réinitialisation vous a été envoyé", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ConnectionActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "L'email n'existe pas", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}
