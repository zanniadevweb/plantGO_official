package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Personnage extends AppCompatActivity {

    //images array list
    public static ArrayList<Integer> imagesHat = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};

    //images array list
    public static ArrayList<Integer> imagesTorso = new ArrayList<Integer>(); // {R.drawable.torso1, R.drawable.torso2};

    //images array list
    public static ArrayList<Integer> imagesPants = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnage);
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

    public void changerCouleurPeau1 (View view) {
        ImageView imageViewPersonnage = findViewById(R.id.imageViewPersonnage);
        if (Modele.genre.equals("1")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color1);
        }
        if (Modele.genre.equals("2")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color1);
        }
        Modele.couleurPeau = "1";
    }

    public void changerCouleurPeau2 (View view) {
        ImageView imageViewPersonnage = findViewById(R.id.imageViewPersonnage);
        if (Modele.genre.equals("1")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color2);
        }
        if (Modele.genre.equals("2")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color2);
        }
        Modele.couleurPeau = "2";
    }

    public void changerCouleurPeau3 (View view) {
        ImageView imageViewPersonnage = findViewById(R.id.imageViewPersonnage);
        if (Modele.genre.equals("1")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color3);
        }
        if (Modele.genre.equals("2")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color3);
        }
        Modele.couleurPeau = "3";
    }

    public void changerSexe1 (View view) {
        ImageView imageViewPersonnage = findViewById(R.id.imageViewPersonnage);
        ImageView imageViewHaut = findViewById(R.id.imageViewHautP);
        ImageView imageViewBas = findViewById(R.id.imageViewBasP);

        imageViewHaut.setImageResource(R.drawable.haut_forestier_male);
        imageViewBas.setImageResource(R.drawable.bas_forestier_male);

        if (Modele.couleurPeau.equals("1")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color1);
        }
        if (Modele.couleurPeau.equals("2")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color2);
        }
        if (Modele.couleurPeau.equals("3")) {
            imageViewPersonnage.setImageResource(R.drawable.male_color3);
        }
        Modele.genre = "1";
    }

    public void changerSexe2 (View view) {
        ImageView imageViewPersonnage = findViewById(R.id.imageViewPersonnage);
        ImageView imageViewHaut = findViewById(R.id.imageViewHautP);
        ImageView imageViewBas = findViewById(R.id.imageViewBasP);

        imageViewHaut.setImageResource(R.drawable.haut_forestier_female);
        imageViewBas.setImageResource(R.drawable.bas_forestier_female);

        if (Modele.couleurPeau.equals("1")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color1);
        }
        if (Modele.couleurPeau.equals("2")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color2);
        }
        if (Modele.couleurPeau.equals("3")) {
            imageViewPersonnage.setImageResource(R.drawable.female_color3);
        }
        Modele.genre = "2";
    }

    public void changerHatMinus (View view) {
        ImageView imageViewHaut = findViewById(R.id.imageViewChapeauP);
        imageViewHaut.setImageResource(R.drawable.hat1);
    }

    public void changerHatPlus (View view) {
        ImageView imageViewHaut = findViewById(R.id.imageViewChapeauP);
        imageViewHaut.setImageResource(R.drawable.hat1_violet);
    }

    public void changerTorsoMinus (View view) {
        ImageView imageViewHaut = findViewById(R.id.imageViewHautP);
        if (Modele.genre.equals("1")) {
            imageViewHaut.setImageResource(R.drawable.haut_forestier_male);
        }
        if (Modele.genre.equals("2")) {
            imageViewHaut.setImageResource(R.drawable.haut_forestier_female);
        }
    }

    public void changerTorsoPlus (View view) {
        ImageView imageViewHaut = findViewById(R.id.imageViewHautP);
        if (Modele.genre.equals("1")) {
            imageViewHaut.setImageResource(R.drawable.haut_violet_male);
        }
        if (Modele.genre.equals("2")) {
            imageViewHaut.setImageResource(R.drawable.haut_violet_female);
        }
    }

    public void changerPantsMinus (View view) {
        ImageView imageViewBas = findViewById(R.id.imageViewBasP);
        if (Modele.genre.equals("1")) {
            imageViewBas.setImageResource(R.drawable.bas_forestier_male);
        }
        if (Modele.genre.equals("2")) {
            imageViewBas.setImageResource(R.drawable.bas_forestier_female);
        }
    }

    public void changerPantsPlus (View view) {
        ImageView imageViewBas = findViewById(R.id.imageViewBasP);
        if (Modele.genre.equals("1")) {
            imageViewBas.setImageResource(R.drawable.bas_violet_male);
        }
        if (Modele.genre.equals("2")) {
            imageViewBas.setImageResource(R.drawable.bas_violet_female);
        }
    }


    public void displayProfile(View view) {
        Intent intent = new Intent(Personnage.this, ProfilActivity.class);
        Personnage.this.startActivity(intent);
    }
}