package com.example.plantGO;

public class Appartient {
    int idQuete;
    int idPlante;
    boolean estTrouve;

    public Appartient(int idPlante, boolean estTrouve) {
        this.idPlante = idPlante;
        this.estTrouve = estTrouve;
    }

    public Appartient(int idQuete, int idPlante, boolean estTrouve) {
        this.idQuete = idQuete;
        this.idPlante = idPlante;
        this.estTrouve = estTrouve;
    }

    public int getIdQuete() {
        return idQuete;
    }

    public void setIdQuete(int idQuete) {
        this.idQuete = idQuete;
    }

    public int getIdPlante() {
        return idPlante;
    }

    public void setIdPlante(int idPlante) {
        this.idPlante = idPlante;
    }

    public boolean isEstTrouve() {
        return estTrouve;
    }

    public void setEstTrouve(boolean estTrouve) {
        this.estTrouve = estTrouve;
    }

    @Override
    public String toString() {
        return "Appartient{" +
                "idQuete=" + idQuete +
                ", idPlante=" + idPlante +
                ", estTrouve=" + estTrouve +
                '}';
    }
}
