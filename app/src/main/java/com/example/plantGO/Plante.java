package com.example.plantGO;

import java.util.Map;

public class Plante {
    int idPlante = -1;
    String nomCommun = null;
    String nomScientifique = null;
    String famille = null;
    String inventeur = null;
    int niveauDescription = -1;
    boolean estDebloque = false;


    public Plante(String nomCommun, String nomScientifique, String famille, String inventeur, int niveauDescription, boolean estDebloque) {
        this.nomCommun = nomCommun;
        this.nomScientifique = nomScientifique;
        this.famille = famille;
        this.inventeur = inventeur;
        this.niveauDescription = niveauDescription;
        this.estDebloque = estDebloque;
    }

    public Plante(int idPlante, String nomCommun, String nomScientifique, String famille, String inventeur, int niveauDescription, boolean estDebloque) {
        this.idPlante = idPlante;
        this.nomCommun = nomCommun;
        this.nomScientifique = nomScientifique;
        this.famille = famille;
        this.inventeur = inventeur;
        this.niveauDescription = niveauDescription;
        this.estDebloque = estDebloque;
    }

    public int getIdPlante() {
        return idPlante;
    }

    public void setIdPlante(int idPlante) {
        this.idPlante = idPlante;
    }

    public String getNomCommun() {
        return nomCommun;
    }

    public void setNomCommun(String nomCommun) {
        this.nomCommun = nomCommun;
    }

    public String getNomScientifique() {
        return nomScientifique;
    }

    public void setNomScientifique(String nomScientifique) {
        this.nomScientifique = nomScientifique;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public String getInventeur() {
        return inventeur;
    }

    public void setInventeur(String inventeur) {
        this.inventeur = inventeur;
    }

    public int getNiveauDescription() {
        return niveauDescription;
    }

    public void setNiveauDescription(int niveauDescription) {
        this.niveauDescription = niveauDescription;
    }

    public boolean isEstDebloque() {
        return estDebloque;
    }

    public void setEstDebloque(boolean estDebloque) {
        this.estDebloque = estDebloque;
    }

    @Override
    public String toString() {
        return "Plante{" +
                "idPlante=" + idPlante +
                ", nomCommun='" + nomCommun + '\'' +
                ", nomScientifique='" + nomScientifique + '\'' +
                ", famille='" + famille + '\'' +
                ", inventeur='" + inventeur + '\'' +
                ", niveauDescription=" + niveauDescription +
                ", estDebloque=" + estDebloque +
                '}';
    }

    public Plante(String nomCommun, String  nomScientifique){
        setNomCommun(nomCommun);
        setNomScientifique(nomScientifique);
    }



    public boolean estDansLaQuete() {
        for ( Map.Entry plante : Modele.queteCourante.listePlantes.entrySet() )
            if (plante.getKey().equals(nomScientifique))
                return true;
        return false;
    }
}
