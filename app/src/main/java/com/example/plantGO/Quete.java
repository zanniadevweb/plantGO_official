package com.example.plantGO;

import java.util.HashMap;
import java.util.Map;

public class Quete { // classe basée sur un pattern de Singleton
    // attr
    HashMap<Plante, Boolean> listePlantes = new HashMap<Plante, Boolean>();
    Boolean estTerminee = false;
    // constr
    private Quete(){
        for (int i=0; i<6; i++){}
            // listePlantes.put(/*Plante aléatoire*/, false);
    }

    // instance unique du singleton
    private static Quete INSTANCE = null;

    // récuperation de l'unique instance
    public static synchronized Quete getInstance(){
        if (INSTANCE == null)
            INSTANCE = new Quete();
        return INSTANCE;
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
