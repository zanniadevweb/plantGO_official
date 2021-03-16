package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

public class ConsignesDeJeu extends AppCompatActivity {
    // Avant le lancement d'un mini-jeu l'application est dans l'état "onCreate"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignesjeu);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité

        if (Modele.resultatpartie.equals("Partie non déterminée") && !(Modele.partieDejaLance)) {
            masquerLabelsExperienceTempsJeu();
            TextView tv0 = findViewById(R.id.texteVictoireDefaite);
            tv0.setVisibility(View.INVISIBLE);
            afficherInformationsJeu();
        }
    }

    // Au retour d'un mini-jeu (commande Gdx.app.exit();), l'application revient à la dernière activité Android lancée (ConsignesDeJeu) dans l'état "onResume"
    @Override
    public void onResume() {
        super.onResume();
        if (Modele.partieDejaLance) {
            changerTexteLancerJeuEnRetourMenu();
            masquerLabelsConsignesJeu();
            TextView tv0 = findViewById(R.id.texteVictoireDefaite);
            tv0.setVisibility(View.VISIBLE);
            afficherLabelsExperienceTempsJeu();

            if (Modele.resultatpartie.equals("Partie gagnée")) {
                tv0.setText("Vous avez gagné !");
                afficheExperienceTempsJeuSiGagne();
            }
            if (Modele.resultatpartie.equals("Partie perdue")) {
                tv0.setText("Vous avez perdu !");
                afficheMessagePasGainExperienceSiPerdu();
            }
        }
    }

    public void lancerJeuOuRetourMenuPrincipal(View view) { // Bouton qui permet de lancer le jeu choisi par le lancer de dé
        if (Modele.resultatpartie.equals("Partie non déterminée")) { // Si on n'a pas encore joué une partie, le fait de lancer un jeu prépare l'affichage pour un retour de partie : la partie est considérée "indéterminée"
            lancerUnJeu();
        }
        if (Modele.resultatpartie.equals("Partie gagnée") || Modele.resultatpartie.equals("Partie perdue")) { // Si on revient d'une partie, le bouton affiche "Retour au Menu principal" au lieu de "Lancer le jeu"
            revenirMenuPrincipal();
        }
    }

    public void revenirMenuPrincipal() {
        Intent intent = new Intent(ConsignesDeJeu.this, MainActivity.class);
        ConsignesDeJeu.this.startActivity(intent);
    }

    public void lancerUnJeu() {
        Modele.partieDejaLance = true;

        Intent intent = new Intent(ConsignesDeJeu.this, AndroidLauncherRandom.class);
        new DelayAction(5); // Il semble que ça crashe si on ne met pas un délai avant d'exécuter le processus (logique car on passe d'un système openGL à un autre)
        ConsignesDeJeu.this.startActivity(intent);
    }

    public void afficherInformationsJeu() {
        afficherLabelsConsignesJeu();

        if ( Modele.randomMiniJeu == 1 || !Modele.estLanceJeuVertical)    {
            afficherTexteConsignesJeuHorizontal();
        }

        if ( Modele.randomMiniJeu == 2 || Modele.estLanceJeuVertical)    {
            afficherTexteConsignesJeuVertical();
        }
    }

    public void afficherLabelsConsignesJeu() {
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
    }

    public void masquerLabelsConsignesJeu() {
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
    }

    public void changerTexteLancerJeuEnRetourMenu() {
        Button bt2 = findViewById(R.id.lancerJeu);
        bt2.setText("<== Retour au menu principal");
    }

    public void afficherTexteConsignesJeuHorizontal() {
        String titreJeuHorizontal = "Jeu Horizontal";
        String consignesJeuHorizontal = "Appuyez sur la touche haut pour sauter."
                + "\nAllez au bout du niveau pour gagner.";
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setText(titreJeuHorizontal);
        tv2.setText(consignesJeuHorizontal);
    }

    public void afficherTexteConsignesJeuVertical() {
        String titreJeuVertical = "Jeu Vertical";
        String consignesJeuVertical = "Déplacez-vous avec les touches directionnelles gauche "
                + "\net droite. La touche haut permet de sauter."
                + "\nCollectez tous les déchets pour gagner.";
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setText(titreJeuVertical);
        tv2.setText(consignesJeuVertical);
    }

    public void afficheExperienceTempsJeuSiGagne() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        Integer tempsJeu = Modele.tempsPartie;

        if (Modele.resultatpartie.equals("Partie gagnée")) {
            Integer experienceJeuGagne = 50;
            Integer experienceSupplementaireTemps = 0;

            if (tempsJeu > 200) { // Temps Jeu compris entre 200 et 300 s
                experienceSupplementaireTemps = 50;
            }
            if (tempsJeu > 150 && tempsJeu < 200) { // Temps Jeu compris entre 150 et 200 s
                experienceSupplementaireTemps = 25;
            }
            if (tempsJeu > 100 && tempsJeu < 150) { // Temps Jeu compris entre 100 et 150 s
                experienceSupplementaireTemps = 10;
            }

            Modele.experienceTotale = experienceJeuGagne + experienceSupplementaireTemps;
            if (Modele.pasEncoreAjoutExperience) {
                Modele.experienceTotaleActuelle = Modele.experienceTotaleActuelle + Modele.experienceTotale;
                Modele.pasEncoreAjoutExperience = false;
            }

            tv1.setText( "Gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + Modele.experienceTotale + " xp");

            // Récupération du temps de jeu
            tv2.setText("Temps de jeu = " + String.valueOf(tempsJeu) + " secondes");

        }

    }

    public void afficheMessagePasGainExperienceSiPerdu() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        tv1.setText( "PAS de gain expérience");
        tv2.setText("Temps PAS PRIS en compte");
    }

    public void afficherLabelsExperienceTempsJeu() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        View[] views = {tv1, tv2};

        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void masquerLabelsExperienceTempsJeu() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        View[] views = {tv1, tv2};

        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

}

