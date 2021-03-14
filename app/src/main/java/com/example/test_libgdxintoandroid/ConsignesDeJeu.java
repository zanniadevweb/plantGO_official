package com.example.test_libgdxintoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

import java.util.Random;

public class ConsignesDeJeu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (Modele.resultatpartie == "Partie gagnée" || Modele.resultatpartie == "Partie perdue") { // La condition ne fonctionne pas (liée à onCreate ?)
            //finish(); // Retourner à Main Activity (fonctionne)
            //--------------------------- OU ---------------------------
            //If your main activity launches a stack of Activities and you want to provide
            //an easy way to get back to the main activity without repeatedly pressing the back button,
            //then you want to call startActivity after setting the flag Intent.FLAG_ACTIVITY_CLEAR_TOP which will close all
            //the Activities in the call stack which are above your main activity and bring your main activity to the top of the call stack.

         // Retourner à Main Activity (fonctionne)
            /*Intent intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
        //}
        setContentView(R.layout.activity_consignesjeu);

        if (Modele.resultatpartie.equals("Partie non déterminée")) {
            //masquerResultatMiniJeu(); ---------------- A CORRIGER ---------------------------------------
            setInfoGame();
        }

        /* ---------------- A CORRIGER ---------------------------------------
        if (Modele.resultatpartie.equals("Partie gagnée") || Modele.resultatpartie.equals("Partie perdue")) { // Si on revient d'une partie, on affiche le résultat de celle-ci (temps de jeu et expérience gagnée)
            Log.d("gagne ou perd", "gagne ou perd" + Modele.resultatpartie);
            afficherResultatMiniJeu();
            testValeurRetourJeu();
        }
        */

    }

    public void launchSelectedGame(View view) { // Bouton qui permet de lancer le jeu choisi par le lancer de dé
        if (Modele.resultatpartie.equals("Partie gagnée") || Modele.resultatpartie.equals("Partie perdue")) { // Si on revient d'une partie, le bouton affiche "Retour au Menu principal" au lieu de "Lancer le jeu"
            Intent intent = new Intent(ConsignesDeJeu.this, MainActivity.class);
            ConsignesDeJeu.this.startActivity(intent);
        }
        else { // Si on n'a pas encore joué une partie, le fait de la lancer prépare l'affichage pour un retour de partie
            // -------------- Effacer affichage titre et consignes de jeu une fois qu'on lance le jeu sélectionné --------------
            rendreInvisibleTitreEtConsignesJeu();
            // -------------- Effacer affichage titre et consignes de jeu une fois qu'on lance le jeu sélectionné --------------

            changerTexteLancerJeuEnRetourMenu(); // Ce bouton est inactif avant lancement du jeu (else) puisque l'action spécifiée n'est effective qu'au retour du jeu (if)

            Intent intent = new Intent(ConsignesDeJeu.this, AndroidLauncherRandom.class);
            new DelayAction(5); // Il semble que ça crashe si on ne met pas un délai avant d'exécuter le processus (logique car on passe d'un système openGL à un autre)
            ConsignesDeJeu.this.startActivity(intent);
        }
    }

    public void setInfoGame() {
        // -------------- Rendre à nouveau visible l'affichage du titre et consignes de jeu qui ont pu préalablement être effacées au lancement du jeu --------------
        rendreVisibleTitreEtConsignesJeu();
        // -------------- Rendre à nouveau visible l'affichage du titre et consignes de jeu qui ont pu préalablement être effacées au lancement du jeu --------------

        if ( Modele.randomMiniJeu == 1 || !Modele.estLanceJeuVertical)    {
            afficherInfosJeuHorizontal();
        }

        if ( Modele.randomMiniJeu == 2 || Modele.estLanceJeuVertical)    {
            afficherInfosJeuVertical();
        }
    }

    public void rendreVisibleTitreEtConsignesJeu() {
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
    }

    public void rendreInvisibleTitreEtConsignesJeu() {
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
    }

    public void changerTexteLancerJeuEnRetourMenu() {
        Button bt2 = findViewById(R.id.lancerJeu);
        bt2.setText("<== Retour au menu principal");
    }

    public void afficherInfosJeuHorizontal() {
        String titreJeuHorizontal = "Jeu Horizontal";
        String consignesJeuHorizontal = "Appuyez sur la touche haut pour sauter."
                + "\nAllez au bout du niveau pour gagner.";
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setText(titreJeuHorizontal);
        tv2.setText(consignesJeuHorizontal);
    }

    public void afficherInfosJeuVertical() {
        String titreJeuVertical = "Jeu Vertical";
        String consignesJeuVertical = "Déplacez-vous avec les touches directionnelles gauche "
                + "\net droite. La touche haut permet de sauter."
                + "\nCollectez tous les déchets pour gagner.";
        TextView tv1 = findViewById(R.id.titreJeu);
        TextView tv2 = findViewById(R.id.consignesJeu);
        tv1.setText(titreJeuVertical);
        tv2.setText(consignesJeuVertical);
    }

    /*
    public void testValeurRetourJeu() {
        TextView tv2 = findViewById(R.id.tempsDeJeu);
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);

        Integer tempsJeu = Modele.tempsPartie;

        if (Modele.resultatpartie.equals("Partie gagnée")) {
            Integer experienceJeuGagne = 50;
            Integer experienceSupplementaireTemps = 0;

            // Récupération du temps de jeu
            tv2.setText("Temps de jeu = " + String.valueOf(tempsJeu) + " secondes");

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

            tv1.setText( "Partie gagnée. Action : gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + Modele.experienceTotale + " xp");

        }
        if (Modele.resultatpartie.equals("Partie perdue")) {
            tv1.setText( "Partie perdue. Action : PAS de gain expérience");
            tv2.setText("Temps PAS PRIS en compte");
        }
    }

    public void afficherResultatMiniJeu() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        View[] views = {tv1, tv2};

        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void masquerResultatMiniJeu() {
        TextView tv1 = findViewById(R.id.texteResultatMiniJeu);
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        View[] views = {tv1, tv2};

        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }*/

}

