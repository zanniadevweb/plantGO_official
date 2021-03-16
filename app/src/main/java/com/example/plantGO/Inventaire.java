package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantGO.databinding.ActivityInventaireBinding;

public class Inventaire extends AppCompatActivity {

    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    ActivityInventaireBinding binding;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
            // Inflate the layout as per google doc instructions
            binding = ActivityInventaireBinding.inflate(getLayoutInflater());
            // add the inflated view to the Included view.
            setContentView(binding.getRoot());
            //supprimer l'animation au changement d'activité
            overridePendingTransition(0,0);
            isAvailableOrNotHat2();
            isAvailableOrNotTorso2();
    }

    public void displayProfile (View view) {
        Intent intent = new Intent(Inventaire.this, ProfilActivity.class);
        Inventaire.this.startActivity(intent);
    }

    public void isAvailableOrNotHat2 () {
        if (Modele.jeuCoffreTresorGagne) {
            binding.switchHat2.setClickable(false);
            binding.switchHat2.setChecked(true);
            binding.imageViewHat2.setImageResource(R.drawable.hat2);
            binding.switchHat2.setText("Disponible");
        }
    }

    public void isAvailableOrNotTorso2 () {
        if (Modele.jeuCoffreTresorGagne) {
            binding.switchTorso2.setClickable(false);
            binding.switchTorso2.setChecked(true);
            binding.imageViewTorso2.setImageResource(R.drawable.torso2);
            binding.switchTorso2.setText("Disponible");
        }
    }


}
