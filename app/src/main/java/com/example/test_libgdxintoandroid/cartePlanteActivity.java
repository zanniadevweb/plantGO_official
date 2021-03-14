package com.example.test_libgdxintoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class cartePlanteActivity extends AppCompatActivity {
    Plante planteAssociee;
    String description;
    int niveauDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_plante);

        // Récupérer image via la base de données
        // Set l'image récupérée

        // Récupérer la texte descriptif de la base de données
        // Set le texte descriptif récupéré

    }

    public void displayHerbier(View view) {
        Intent intent = new Intent(cartePlanteActivity.this, HerbierActivity.class);
        cartePlanteActivity.this.startActivity(intent);
    }

    public void augmenterNiveauDescription(View view) {
        int exp = 0; // Devra être globale et non définie ici !

        if (exp > 200 ) // Récupérer attribut : niveauDeDébloquage, au lieu de la valeur en dure
             { /* cartePlante.augmenterNiveauDec() */ }

        else {  Context context = getApplicationContext();
                CharSequence text = "Tu manques de niveau ... Retente une prochaine fois !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show(); }

    }
}