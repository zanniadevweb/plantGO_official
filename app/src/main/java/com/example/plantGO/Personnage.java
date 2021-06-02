package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.plantGO.databinding.ActivityPersonnageBinding;

public class Personnage extends AppCompatActivity {

    //images array list
    public static ArrayList<Integer> imagesHat = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};

    //images array list
    public static ArrayList<Integer> imagesTorso = new ArrayList<Integer>(); // {R.drawable.torso1, R.drawable.torso2};

    //images array list
    public static ArrayList<Integer> imagesPants = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};

    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    ActivityPersonnageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout as per google doc instructions
        binding = ActivityPersonnageBinding.inflate(getLayoutInflater());
        // add the inflated view to the Included view.
        setContentView(binding.getRoot());
        Modele.couleurPeau = "2";
        Modele.genre = "1";

        if (Modele.unSetDeBase) {
            HataddElement(R.drawable.hat1);
            TorsoaddElement(R.drawable.haut_forestier_male);
            PantsaddElement(R.drawable.bas_forestier_male);
            TorsoaddElement(R.drawable.haut_forestier_female);
            PantsaddElement(R.drawable.bas_forestier_female);
            Modele.unSetDeBase = false;
        }

        if (Modele.firstInventoryLook && Modele.jeuCoffreTresorGagne) {
            HataddElement(R.drawable.hat1_violet);
            TorsoaddElement(R.drawable.haut_violet_male);
            PantsaddElement(R.drawable.bas_violet_male);
            Modele.firstInventoryLook = false;
        }

    }

    static void HataddElement(int element) {
        imagesHat.add(element);
    }

    static void TorsoaddElement(int element) {
        imagesTorso.add(element);
    }

    static void PantsaddElement(int element) {
        imagesPants.add(element);
    }

    public <Resource> void changerCouleurPeauSelonGenre(String genreM, String genreF, Resource resM, Resource resF) {
        if (Modele.genre.equals(genreM)) {
            binding.imageViewPersonnage.setImageResource((Integer) resM);
        }
        if (Modele.genre.equals(genreF)) {
            binding.imageViewPersonnage.setImageResource((Integer) resF);
        }
    }

    public void changerCouleurPeau1 (View view) {
        changerCouleurPeauSelonGenre("1", "2", R.drawable.male_color1, R.drawable.female_color1);
        Modele.couleurPeau = "1";
    }

    public void changerCouleurPeau2 (View view) {
        changerCouleurPeauSelonGenre("1", "2", R.drawable.male_color2, R.drawable.female_color2);
        Modele.couleurPeau = "2";
    }

    public void changerCouleurPeau3 (View view) {
        changerCouleurPeauSelonGenre("1", "2", R.drawable.male_color3, R.drawable.female_color3);
        Modele.couleurPeau = "3";
    }

    public <Resource> void changerGenreSelonVetements(String couleurPeau1, String couleurPeau2, String couleurPeau3, Resource resM1, Resource resM2, Resource resM3) {
        if (Modele.couleurPeau.equals(couleurPeau1)) {
            binding.imageViewPersonnage.setImageResource((Integer) resM1);
        }
        if (Modele.couleurPeau.equals(couleurPeau2)) {
            binding.imageViewPersonnage.setImageResource((Integer) resM2);
        }
        if (Modele.couleurPeau.equals(couleurPeau3)) {
            binding.imageViewPersonnage.setImageResource((Integer) resM3);
        }
    }

    public void changerSexe1 (View view) {
        binding.imageViewHautP.setImageResource(R.drawable.haut_forestier_male);
        binding.imageViewBasP.setImageResource(R.drawable.bas_forestier_male);
        changerGenreSelonVetements("1", "2", "3", R.drawable.male_color1, R.drawable.male_color2, R.drawable.male_color3);
        Modele.genre = "1";
    }

    public void changerSexe2 (View view) {
        binding.imageViewHautP.setImageResource(R.drawable.haut_forestier_female);
        binding.imageViewBasP.setImageResource(R.drawable.bas_forestier_female);
        changerGenreSelonVetements("1", "2", "3", R.drawable.female_color1, R.drawable.female_color2, R.drawable.female_color3);
        Modele.genre = "2";
    }

    public void changerHatMinus (View view) {
        binding.imageViewChapeauP.setImageResource(R.drawable.hat1);
    }

    public void changerHatPlus (View view) {
        binding.imageViewChapeauP.setImageResource(R.drawable.hat1_violet);
    }

    public <Resource> void changerTorsoSelonGenre(String genreM, String genreF, Resource resM, Resource resF) {
        if (Modele.genre.equals(genreM)) {
            binding.imageViewHautP.setImageResource((Integer) resM);
        }
        if (Modele.genre.equals(genreF)) {
            binding.imageViewHautP.setImageResource((Integer) resF);
        }
    }

    public void changerTorsoMinus (View view) {
        changerTorsoSelonGenre("1", "2", R.drawable.haut_forestier_male, R.drawable.haut_forestier_female);
    }

    public void changerTorsoPlus (View view) {
        changerTorsoSelonGenre("1", "2", R.drawable.haut_violet_male, R.drawable.haut_violet_female);
    }

    public <Resource> void changerPantsSelonGenre(String genreM, String genreF, Resource resM, Resource resF) {
        if (Modele.genre.equals(genreM)) {
            binding.imageViewBasP.setImageResource((Integer) resM);
        }
        if (Modele.genre.equals(genreF)) {
            binding.imageViewBasP.setImageResource((Integer) resF);
        }
    }

    public void changerPantsMinus (View view) {
        changerPantsSelonGenre("1", "2", R.drawable.bas_forestier_male, R.drawable.bas_forestier_female);
    }

    public void changerPantsPlus (View view) {
        changerPantsSelonGenre("1", "2", R.drawable.bas_violet_male, R.drawable.bas_violet_female);
    }


    public void displayProfile(View view) {
        Intent intent = new Intent(Personnage.this, ProfilActivity.class);
        Personnage.this.startActivity(intent);
    }
}