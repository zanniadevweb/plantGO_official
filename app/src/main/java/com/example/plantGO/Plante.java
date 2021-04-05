package com.example.plantGO;

import java.util.Map;

public class Plante {
    String nomCommun = null;
    String nomScientifique = null;

    public Plante(String nomCommun, String  nomScientifique){
        setNomCommun(nomCommun);
        setNomScientifique(nomScientifique);
    }
    public String getNomCommun()         { return nomCommun; }
    public void setNomCommun(String nom) { nomCommun = nom;  }

    public String getNomScientifique()         { return nomScientifique; }
    public void setNomScientifique(String nom) { nomScientifique = nom;  }

        public boolean estDansLaQuete() {
        for ( Map.Entry plante : Modele.queteCourante.listePlantes.entrySet() )
            if (plante.getKey().equals(nomScientifique))
                return true;
        return false;
    }
}
