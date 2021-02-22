package com.example.test_libgdxintoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité
    }

    public void launchGameHorizontal(View view) {
        Modele.testBoolean = false;
        remettreZeroParametresJeu();
        lancerConsignesJeu();
    }

    public void launchGameVertical(View view) {
        Modele.testBoolean = true;
        remettreZeroParametresJeu();
        lancerConsignesJeu();
    }

    public void launchRandomGame(View view) {
        // Lance un nombre aléatoire compris entre 1 (Jeu Horizontal) et 2 (Jeu Vertical)
        Modele.random = new Random().nextInt(2) + 1; // [0, 1] + 1 => [1, 2] : Minimum 1 (si [0] + 1) et maximum 2 (si [1] + 1)
        remettreZeroParametresJeu();
        lancerConsignesJeu();
    }

    public void testValeurRetourJeu(View view) {
        String resultatWinLose = Modele.resultatpartie;
        TextView tv1 = findViewById(R.id.textView);
        ProgressBar pb = findViewById(R.id.progressBar);

        Integer tempsJeu = Modele.tempsPartie;
        TextView tv2 = findViewById(R.id.tempsDeJeu);

        if (TextUtils.equals(resultatWinLose, "Partie gagnée")) {
            Integer experienceJeuGagne = 50;
            Integer experienceSupplementaireTemps = 0;
            Integer experienceTotale = 0;

            // Récupération du temps de jeu
            tv2.setText("Temps de jeu = " + String.valueOf(tempsJeu) + " secondes");

            if (tempsJeu > 200) { // Temps Jeu compris entre 200 et 300 s
                experienceSupplementaireTemps = 50;
            }
            if (tempsJeu > 150 && tempsJeu < 200) { // Temps Jeu compris entre 150 et 200 s
                experienceSupplementaireTemps = 25;
            }
            if (tempsJeu > 250 && tempsJeu < 150) { // Temps Jeu compris entre 150 et 250 s
                experienceSupplementaireTemps = 10;
            }

            experienceTotale = experienceJeuGagne + experienceSupplementaireTemps;

            tv1.setText( "Partie gagnée. Action : gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + experienceTotale + " xp");
            pb.setProgress(experienceTotale);

        }
        if (TextUtils.equals(resultatWinLose,"Partie perdue")) {
            tv1.setText( "Partie perdue. Action : PAS de gain expérience");
            tv2.setText("Temps PAS PRIS en compte");
            pb.setProgress(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("testresultatpartie", Modele.resultatpartie);
    }

    public void remettreZeroParametresJeu() {
        Modele.resultatpartie = "Partie non déterminée"; // Permet au retour d'une partie (gagnée ou perdue) de remettre à zéro l'activité "ConsignesDeJeu"
        Modele.jetonRejouer = 1; // Permet au retour d'une partie (gagnée ou perdue) de remettre à zéro le jeton pour rejouer à un jeu
    }

    public void lancerConsignesJeu() {
        Intent intent = new Intent(MainActivity.this, ConsignesDeJeu.class);
        MainActivity.this.startActivityForResult(intent, 1);
    }

    // afficher profil
    public void displayProfile(View view) {
        Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
        MainActivity.this.startActivity(intent);
    }

    // afficher app. photo
    public void displayAppPhoto(View view) {
        Intent intent = new Intent(MainActivity.this, AppPhotoActivity.class);
        MainActivity.this.startActivity(intent);
    }

}

