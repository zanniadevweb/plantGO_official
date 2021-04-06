package com.example.plantGO;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Quete { // classe basée sur un pattern de Singleton
    // attr
    int idQuete;
    double latPosition;
    double lngPosition;
    int rayon;
    boolean estFini;
    double latCoffre;
    double lngCoffre;


    public Quete(double latPosition, double lngPosition, int rayon, boolean estFini, double latCoffre, double lngCoffre) {
        this.latPosition = latPosition;
        this.lngPosition = lngPosition;
        this.rayon = rayon;
        this.estFini = estFini;
        this.latCoffre = latCoffre;
        this.lngCoffre = lngCoffre;
        this.estTerminee = estTerminee;
    }

    public Quete(int idQuete, double latPosition, double lngPosition, int rayon, boolean estFini, double latCoffre, double lngCoffre) {
        this.idQuete = idQuete;
        this.latPosition = latPosition;
        this.lngPosition = lngPosition;
        this.rayon = rayon;
        this.estFini = estFini;
        this.latCoffre = latCoffre;
        this.lngCoffre = lngCoffre;
        this.estTerminee = estTerminee;
    }

    public int getIdQuete() {
        return idQuete;
    }

    public void setIdQuete(int idQuete) {
        this.idQuete = idQuete;
    }

    public double getLatPosition() {
        return latPosition;
    }

    public void setLatPosition(double latPosition) {
        this.latPosition = latPosition;
    }

    public double getLngPosition() {
        return lngPosition;
    }

    public void setLngPosition(double lngPosition) {
        this.lngPosition = lngPosition;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public boolean isEstFini() {
        return estFini;
    }

    public void setEstFini(boolean estFini) {
        this.estFini = estFini;
    }

    public double getLatCoffre() {
        return latCoffre;
    }

    public void setLatCoffre(double latCoffre) {
        this.latCoffre = latCoffre;
    }

    public double getLngCoffre() {
        return lngCoffre;
    }

    public void setLngCoffre(double lngCoffre) {
        this.lngCoffre = lngCoffre;
    }

    public HashMap<Plante, Boolean> getListePlantes() {
        return listePlantes;
    }

    public void setListePlantes(HashMap<Plante, Boolean> listePlantes) {
        this.listePlantes = listePlantes;
    }

    public Boolean getEstTerminee() {
        return estTerminee;
    }

    public void setEstTerminee(Boolean estTerminee) {
        this.estTerminee = estTerminee;
    }

    @Override
    public String toString() {
        return "Quete{" +
                "idQuete=" + idQuete +
                ", latPosition=" + latPosition +
                ", lngPosition=" + lngPosition +
                ", rayon=" + rayon +
                ", estFini=" + estFini +
                ", latCoffre=" + latCoffre +
                ", lngCoffre=" + lngCoffre +
                ", listePlantes=" + listePlantes +
                ", estTerminee=" + estTerminee +
                '}';
    }

    HashMap<Plante, Boolean> listePlantes = new HashMap<Plante, Boolean>();
    Boolean estTerminee = false;
    Date dateDemarrage; // on enregistre la date de démarrage de la quête.
    // constr
    private Quete(){
        // for (int i=0; i<6; i++){}
            // listePlantes.put(/*Plante aléatoire*/, false);
        Plante Erable = new Plante("Erable","Erabulu");
        listePlantes.put(Erable, false);

        Plante Tilleul = new Plante("Tilleul","Tillulu");
        listePlantes.put(Tilleul, false);

        dateDemarrage = new Date();

    }

    // instance unique du singleton
    private static Quete INSTANCE = null;

    // récuperation de l'unique instance
    public static synchronized Quete getInstance() {
        final int SEMAINE = 7;

        // initialisation/première utilisation :  la quête courante n'existe pas donc on la créé
        if (INSTANCE == null)
            return nouvelleQuete();

        // utilisation normale : on ne détruit jamais la quête, il y a tjr une quête courante.
        // Si au démarrage de l'application, nous sommes dimanche
        // + la quete courante est vieille d'une semaine ou +
        // alors on la remplace
            // nb de jours écoulés depuis le debut de la quête
        Date dateDemarrage = Modele.queteCourante.dateDemarrage;
        Date dateActuelle = new Date();
        long diff = dateDemarrage.getTime() - dateActuelle.getTime();
        float diffJours = (diff / (1000*60*60*24));

        if (diffJours >= SEMAINE) {
            GregorianCalendar calendar = new GregorianCalendar();
            int today = calendar.get(calendar.DAY_OF_WEEK);

            if (today == GregorianCalendar.SUNDAY)
                nouvelleQuete();
        }
        return INSTANCE;
    }

    // changer de quete
    public static synchronized  Quete nouvelleQuete(){
        INSTANCE = new Quete();
        return INSTANCE;
    }

    // quand une plante est trouvée
    public void aTrouve(String planteTrouvee){
        for (Map.Entry comboPlante : listePlantes.entrySet()) {
            if ( ((Plante)comboPlante.getKey()).getNomCommun() == planteTrouvee )
                listePlantes.put((Plante)comboPlante.getKey(), true);
        }
    }

    // vérifier si la quete est terminée
    public Boolean estTerminée(){
        for (Map.Entry comboPlante : listePlantes.entrySet()) {
            if (!(Boolean) comboPlante.getValue())
               return false;
        }
        return true;
    }
}
