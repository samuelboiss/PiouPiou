package com.example.pioupioy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AjouterContact extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_un_contact);

        EditText nomEditText = findViewById(R.id.nom);
        EditText numeroEditText = findViewById(R.id.numero);

        Button valideButton = findViewById(R.id.valider);

        valideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString();
                String numero = numeroEditText.getText().toString();

                // ContactsContract permet d'ajouter un contact dans le répertoire du téléphone
                // Inser.action permet de créer un nouveau contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // On précise que l'on veut ajouter un contact de type "personne"
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                // On ajoute le nom et le numéro de téléphone avec putExtra
                intent.putExtra(ContactsContract.Intents.Insert.NAME, nom);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, numero);

                startActivity(intent);
            }
        });
    }
}
