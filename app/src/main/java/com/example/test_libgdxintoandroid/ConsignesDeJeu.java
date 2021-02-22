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
    }


    public void launchSelectedGame(View view) {
        if (Modele.resultatpartie == "Partie gagnée" || Modele.resultatpartie == "Partie perdue") {
            Intent intent = new Intent(ConsignesDeJeu.this, MainActivity.class);
            ConsignesDeJeu.this.startActivity(intent);
        }
        else {
            // -------------- Effacer affichage titre et consignes de jeu (+bouton d'affichage) une fois qu'on lance le jeu sélectionné --------------
            rendreInvisibleTitreEtConsignesJeu();
            rendreInvisibleBoutonConsignesJeu();
            // -------------- Effacer affichage titre et consignes de jeu (+bouton d'affichage) une fois qu'on lance le jeu sélectionné --------------

            changerTexteLancerJeuEnRetourMenu(); // Ce bouton est inactif avant lancement du jeu (else) puisque l'action spécifiée n'est effective qu'au retour du jeu (if)

            Intent intent = new Intent(ConsignesDeJeu.this, AndroidLauncherRandom.class);
            new DelayAction(5); // Il semble que ça crashe si on ne met pas un délai avant d'exécuter le processus (logique car on passe d'un système openGL à un autre)
            ConsignesDeJeu.this.startActivityForResult(intent, 1);
        }
    }

    public void setInfoGame(View view) {
        // -------------- Rendre à nouveau visible l'affichage du titre et consignes de jeu qui ont pu préalablement être effacées au lancement du jeu --------------
        rendreVisibleTitreEtConsignesJeu();
        // -------------- Rendre à nouveau visible l'affichage du titre et consignes de jeu qui ont pu préalablement être effacées au lancement du jeu --------------

        if ( Modele.random == 1 || Modele.testBoolean == false)    {
            afficherInfosJeuHorizontal();
        }

        if ( Modele.random == 2 || Modele.testBoolean == true)    {
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

    public void rendreInvisibleBoutonConsignesJeu() {
        Button bt1 = findViewById(R.id.affichageConsignes);
        bt1.setVisibility(View.INVISIBLE);
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

}

