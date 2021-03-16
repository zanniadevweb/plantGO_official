package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantGO.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {

    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    com.example.plantGO.databinding.ActivityProfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout as per google doc instructions
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        // add the inflated view to the Included view.
        setContentView(binding.getRoot());
        //supprimer l'animation au changement d'activité
        overridePendingTransition(0,0);

        binding.progressBar.setProgress(Modele.experienceTotaleActuelle);
        binding.textViewExperienceTotale.setText( Modele.experienceTotaleActuelle + " / " + getResources().getString(R.string.experience_maximum) + " xp");

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