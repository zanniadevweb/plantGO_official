package com.example.test_libgdxintoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setProgress(Modele.experienceTotaleActuelle);
        TextView tv1 = findViewById(R.id.textViewExperienceTotale);
        tv1.setText( Modele.experienceTotaleActuelle + " / " + getResources().getString(R.string.experience_maximum) + " xp");

    }

    // retour accueil
    public void displayAccueil(View view) {
        Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
        ProfilActivity.this.startActivity(intent);
    }

    public void displayCharacter(View view) {
        Intent intent = new Intent(ProfilActivity.this, Personnage.class);
        ProfilActivity.this.startActivity(intent);
    }

    public void displayBackpack(View view) {
        Intent intent = new Intent(ProfilActivity.this, Inventaire.class);
        ProfilActivity.this.startActivity(intent);
    }

    public void displayHerbier(View view) {
        Intent intent = new Intent(ProfilActivity.this, HerbierActivity.class);
        ProfilActivity.this.startActivity(intent);
    }
}