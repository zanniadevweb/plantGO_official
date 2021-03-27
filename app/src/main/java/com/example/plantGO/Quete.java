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

    // to string
    public String toString(){
        String listePlantesString = "";
        Plante plante;
        if (INSTANCE != null )
            for (Map.Entry comboPlante_estTrouvee : listePlantes.entrySet()) {
                plante = (Plante)comboPlante_estTrouvee.getKey();
                listePlantesString += plante.getNomCommun() + "\n";
            }
        return listePlantesString;
    }

    // quand une plante est trouvée
    public void aTrouve(Plante planteTrouvee){
        listePlantes.put(planteTrouvee, true);
    }

    // vérifier si la quete est terminée
    public Boolean estTerminée(){
        for (Map.Entry comboPlante_estTrouvee : listePlantes.entrySet()) {
            if (!(Boolean) comboPlante_estTrouvee.getValue())
               return false;
        }
        return true;
    }
}
