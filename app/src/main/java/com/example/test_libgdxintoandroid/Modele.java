package com.example.test_libgdxintoandroid;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Random;

public class Modele {
    // Attributs propres à libGDX
    public static String resultatpartie = "Partie non déterminée";
    public static Integer compteurDechetCollecte = 0;
    public static Integer compteurTonneau = 0;
    public static Integer random = 0;
    public static Boolean testBoolean = false;
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


    // Google Maps
    public static LatLng marqueurQuete;
    public static LatLng maPosition;
    public static double latitudeTempsT;
    public static double longitudeTempsT;
    public static Marker MyMarker;
}
