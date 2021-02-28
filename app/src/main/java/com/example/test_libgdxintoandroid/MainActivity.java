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

//* Import propre à la lecture / écriture de fichiers *//
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String SAVE = "experience_actuelle.txt"; // resultatJeuCoffreTresor[0] | experienceActuelle[1] | queteAcceptee[2]
    private String resultatJeuCoffreTresor = String.valueOf(Modele.jeuCoffreTresorGagne);
    private String queteEstAcceptee = String.valueOf(Modele.queteAcceptee);
    private String experienceActuelle = String.valueOf(Modele.experienceTotaleActuelle);
    private final String pipeSeparation = "|";
    private File mFile = null;
    private Button mWrite = null;
    private Button mRead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité
        Button bt4 = findViewById(R.id.button);
        bt4.setVisibility(View.INVISIBLE);
        Button bt5 = findViewById(R.id.button2);
        bt5.setVisibility(View.INVISIBLE);
        Button bt6 = findViewById(R.id.gameHorizontal);
        bt6.setVisibility(View.INVISIBLE);
        Button bt7 = findViewById(R.id.gameVertical);
        bt7.setVisibility(View.INVISIBLE);
        Button bt8 = findViewById(R.id.randomGame);
        bt8.setVisibility(View.INVISIBLE);
        Button bt3 = findViewById(R.id.buttonQueteAcceptee);
        bt3.setVisibility(View.INVISIBLE);

        TextView tv0 = findViewById(R.id.textView);
        tv0.setVisibility(View.INVISIBLE);
        TextView tv2 = findViewById(R.id.textView2);
        tv2.setVisibility(View.INVISIBLE);
        TextView tv3 = findViewById(R.id.tempsDeJeu);
        tv3.setVisibility(View.INVISIBLE);
        TextView tv4 = findViewById(R.id.planteAChercher);
        tv4.setVisibility(View.INVISIBLE);
        TextView tv5 = findViewById(R.id.planteQuete1);
        tv5.setVisibility(View.INVISIBLE);
        TextView tv6 = findViewById(R.id.textViewQueteEnCours);
        tv6.setVisibility(View.INVISIBLE);

        if (Modele.firstLoadingApplication) {
            Button bt1 = findViewById(R.id.write);
            bt1.setVisibility(View.INVISIBLE);
        }
        if (!Modele.firstLoadingApplication) {
            Button bt1 = findViewById(R.id.read);
            bt1.setVisibility(View.INVISIBLE);
            bt4.setVisibility(View.VISIBLE);
            bt5.setVisibility(View.VISIBLE);
            bt6.setVisibility(View.VISIBLE);
            bt7.setVisibility(View.VISIBLE);
            bt8.setVisibility(View.VISIBLE);

            tv0.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
        }

        if (Modele.queteAcceptee == true) {
            bt3.setVisibility(View.INVISIBLE);
            TextView tv1 = findViewById(R.id.textViewQueteEnCours);
            tv1.setText("La quête est en cours");
        }

        // On crée un fichier qui correspond à l'emplacement extérieur
        File directory = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists())
            directory.mkdirs();

        /* --------------------- BUT QUI DEVRA ETRE ATTEINT : CHARGER LA SAUVEGARDE UNE SEULE FOIS AU LANCEMENT DE MAINACTIVITY -------------------- */
            mRead = findViewById(R.id.read);
            mRead.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            try {
                if (Modele.firstLoadingApplication) {
                Button bt1 = findViewById(R.id.write);
                bt1.setVisibility(View.VISIBLE);
                Button bt2 = findViewById(R.id.read);
                Button bt3 = findViewById(R.id.buttonQueteAcceptee);
                bt3.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt4.setVisibility(View.VISIBLE);
                bt5.setVisibility(View.VISIBLE);
                bt6.setVisibility(View.VISIBLE);
                bt7.setVisibility(View.VISIBLE);
                bt8.setVisibility(View.VISIBLE);

                    TextView tv0 = findViewById(R.id.textView);
                    tv0.setVisibility(View.VISIBLE);
                    TextView tv2 = findViewById(R.id.textView2);
                    tv2.setVisibility(View.VISIBLE);
                    TextView tv3 = findViewById(R.id.tempsDeJeu);
                    tv3.setVisibility(View.VISIBLE);
                    TextView tv4 = findViewById(R.id.planteAChercher);
                    tv4.setVisibility(View.VISIBLE);
                    TextView tv5 = findViewById(R.id.planteQuete1);
                    tv5.setVisibility(View.VISIBLE);
                    TextView tv6 = findViewById(R.id.textViewQueteEnCours);
                    tv6.setVisibility(View.VISIBLE);
                }

                FileInputStream input = openFileInput(SAVE);
                int value;
                // On utilise un StringBuffer pour construire la chaîne au fur et à mesure
                StringBuffer lu = new StringBuffer();
                // On lit les caractères les uns après les autres
                while ((value = input.read()) != -1) {
                    // On écrit dans le fichier le caractère lu
                    lu.append((char) value);
                }
                String strFields0 = lu.toString().split("\\|")[0];
                String strFields1 = lu.toString().split("\\|")[1];
                String strFields2 = lu.toString().split("\\|")[2];

                Toast.makeText(MainActivity.this, "Lecture sauvegarde Interne : " + lu.toString(), Toast.LENGTH_SHORT).show();
                Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Log.d("s0", "split0 : " + strFields0);
                Log.d("s1", "split1 : " + strFields1);
                Log.d("s2", "split2 : " + strFields2);
                Log.d("q", "quete en cours ? " +  Modele.queteAcceptee);
                Modele.firstLoadingApplication = false;
                if (input != null)
                    input.close();

                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    lu = new StringBuffer();
                    input = new FileInputStream(mFile);
                    while ((value = input.read()) != -1)
                        lu.append((char) value);
                    Toast.makeText(MainActivity.this, "Lecture sauvegarde Externe : " + lu.toString(), Toast.LENGTH_SHORT).show();
                    Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Log.d("s0", "split0 : " + strFields0);
                    Log.d("s1", "split1 : " + strFields1);
                    Log.d("s2", "split2 : " + strFields2);
                    Log.d("q", "quete en cours ? " +  Modele.queteAcceptee);
                    Modele.firstLoadingApplication = false;
                    if (input != null)
                        input.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
            });

        /* -------------------- BUT QUI DEVRA ETRE ATTEINT : SAUVEGARDER CHAQUE FOIS QUE L'ON REVIENT SUR MAINACTIVITY (APRES LE PREMIER LANCEMENT) -------------------- */
        mFile = new File(directory.getPath() + SAVE);
        mWrite = findViewById(R.id.write);

        mWrite.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    // Flux interne
                    FileOutputStream output = openFileOutput(SAVE, MODE_PRIVATE);

                    // On écrit dans le flux interne
                    output.write(resultatJeuCoffreTresor.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                    output.write(pipeSeparation.getBytes());
                    output.write(experienceActuelle.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                    output.write(pipeSeparation.getBytes());
                    output.write(queteEstAcceptee.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                    Log.d("q", "quete en cours ? " +  Modele.queteAcceptee);

                    if(output != null)
                        output.close();

                    // Si le fichier est lisible et qu'on peut écrire dedans
                    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                            && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                        // On crée un nouveau fichier. Si le fichier existe déjà, il ne sera pas créé
                        mFile.createNewFile();
                        output = new FileOutputStream(mFile);
                        output.write(resultatJeuCoffreTresor.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                        output.write(pipeSeparation.getBytes());
                        output.write(experienceActuelle.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                        output.write(pipeSeparation.getBytes());
                        output.write(queteEstAcceptee.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                        Log.d("q", "quete en cours ? " +  Modele.queteAcceptee);
                        if(output != null)
                            output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

        if (TextUtils.equals(resultatWinLose, "Partie gagnée") || Modele.resultatpartie == "Partie gagnée") {
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
            if (tempsJeu > 250 && tempsJeu < 150) { // Temps Jeu compris entre 150 et 250 s
                experienceSupplementaireTemps = 10;
            }

            Modele.experienceTotale = experienceJeuGagne + experienceSupplementaireTemps;
            if (Modele.pasEncoreAjoutExperience == true) {
                Modele.experienceTotaleActuelle = Modele.experienceTotaleActuelle + Modele.experienceTotale;
                Modele.pasEncoreAjoutExperience = false;
            }

            tv1.setText( "Partie gagnée. Action : gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + Modele.experienceTotale + " xp");
            pb.setProgress(Modele.experienceTotale);

        }
        if (TextUtils.equals(resultatWinLose,"Partie perdue") || Modele.resultatpartie == "Partie perdue") {
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


    public void accepterQuete(View view) {
        Modele.queteAcceptee = true;
        Log.d("q", "quete en cours ? " +  Modele.queteAcceptee);
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

