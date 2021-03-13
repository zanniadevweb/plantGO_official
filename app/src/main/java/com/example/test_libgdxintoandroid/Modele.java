package com.example.test_libgdxintoandroid;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Modele {
    // Attributs propres à libGDX
    public static String resultatpartie = "Partie non déterminée";
    public static Integer compteurDechetCollecte = 0;
    public static Integer compteurTonneau = 0;
    public static Integer randomMiniJeu = 0;
    public static Boolean estLanceJeuVertical = false;
    public static Integer tempsPartie = 0;
    public static Integer jetonRejouer = 1;
    public static Integer experienceTotaleActuelle = 0;
    public static boolean pasEncoreAjoutExperience = true;


    // Attributs propres au jeu du coffre au trésor et à l'inventaire (backpack)
    public static boolean firstInventoryLook = true;
    public static boolean jeuCoffreTresorGagne = false;
    public static boolean unSetDeBase = true;
    public static Integer experienceTotale = 0;
    public static boolean firstLoadingApplication = true;


    // Firebase
    public static Uri imageURI;


    // Quêtes
    public static boolean queteAcceptee = false;
    public static boolean lancerDeDejaFait = false;
    public static boolean popUpActif = false;
    public static boolean popUpDetruit = false;
    public static String[] plantesQueteCourante = {"Vanilla planifolia"};
    public static LatLng marqueurCoffre;
    public static String planteCourante;

    public static boolean isInTheWeeklyQuest(String currentPlant) {
        for ( String plant : plantesQueteCourante ) {
            if (plant.equals(currentPlant))
                return true;
        }
        return false;
    }

    public static boolean queteTerminee = false; // Si true : Active le lancer de dé et lance un jeu au hasard parmi les deux par défaut si résultat > 3

    // Google Maps
    public static LatLng marqueurQuete;
    public static LatLng maPosition;
    public static double latitudeTempsT;
    public static double longitudeTempsT;
    public static Marker MyMarker;
    public static Circle circle;
    public static double randomLat;
    public static double randomLng;
}
