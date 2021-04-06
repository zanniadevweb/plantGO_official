package com.example.plantGO;

import android.net.Uri;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;
import java.util.Vector;

public class Modele {
    // Attributs propres à libGDX
    public static String resultatpartie = "Partie non déterminée";
    public static Integer compteurDechetCollecte = 0;
    public static Integer randomMiniJeu = 0;
    public static Boolean estLanceJeuVertical = false;
    public static Integer tempsPartie = 0;
    public static Integer jetonRejouer = 1;
    public static Integer experienceTotaleActuelle = 0;
    public static boolean pasEncoreAjoutExperience = true;
    public static boolean partieDejaLance = false;
        // pour lancer un minijeu
    public static boolean planteReconnue = false;

    // Attributs propres au jeu du coffre au trésor et à l'inventaire (backpack)
    public static boolean firstInventoryLook = true;
    public static boolean jeuCoffreTresorGagne = false;
    public static boolean unSetDeBase = true;
    public static Integer experienceTotale = 0;
    public static boolean firstLoadingApplication = true;


    // Attributs propres au Personnage
    public static String couleurPeau = "2";
    public static String genre = "1";


    // Firebase
    public static Uri imageURI;


    // Quêtes
    public static boolean queteAcceptee = false;
    public static boolean popUpActif = false;
    public static boolean popUpDetruit = false;
    public static Quete queteCourante = Quete.getInstance();
    public static Plante planteCourante;
    public static boolean queteTerminee = false; // Si true : Active le lancer de dé et lance un jeu au hasard parmi les deux par défaut si résultat > 3

    // Google Maps
    public static LatLng marqueurCoffre;
    public static LatLng marqueurQuete;
    public static LatLng maPosition;
    public static double latitudeTempsT;
    public static double longitudeTempsT;
    public static Marker MyMarker;
    public static Circle circle;
    public static double randomLat;
    public static double randomLng;

}
