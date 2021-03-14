package com.example.test_libgdxintoandroid;

public class Plante {
    String nomCommun;
    String nomScientifique;

    public Plante(String nomCommun, String  nomScientifique){
        setNomCommun(nomCommun);
        setNomScientifique(nomScientifique);
    }
    public String getNomCommun()         { return nomCommun; }
    public void setNomCommun(String nom) { nomCommun = nom;  }

    public String getNomScientifique()         { return nomScientifique; }
    public void setNomScientifique(String nom) { nomScientifique = nom;  }
}
