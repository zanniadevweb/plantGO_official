package com.example.test_libgdxintoandroid;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
public class SauvegardeActivity extends AppCompatActivity {

    //public static SauvegardeActivity SvgAct;
    //private Context context;

    private String SAVE = "sauvegarde.txt"; // resultatJeuCoffreTresor[0] | experienceActuelle[1] | queteAcceptee[2]
    private String resultatJeuCoffreTresor = String.valueOf(Modele.jeuCoffreTresorGagne);
    private String queteEstAcceptee = String.valueOf(Modele.queteAcceptee);
    private String experienceActuelle = String.valueOf(Modele.experienceTotaleActuelle);
    private final String pipeSeparation = "|";
    private File mFile = null;
    private Button mWrite = null;
    private Button mRead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creerFichier();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //chargerFichier();
        //sauvegarderFichier();
        //context = this;
    }

    /*
    public void creerFichier() {
        // On crée un fichier qui correspond à l'emplacement extérieur
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists())
            directory.mkdirs();
    }*/

    /* --------------------- BUT QUI DEVRA ETRE ATTEINT : CHARGER LA SAUVEGARDE UNE SEULE FOIS AU LANCEMENT DE MAINACTIVITY -------------------- */
/*
    public void chargerFichier() {
        mRead = findViewById(R.id.read);
        mRead.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    if (Modele.firstLoadingApplication) {

                        Button bt1 = findViewById(R.id.write);
                        Button bt3 = findViewById(R.id.buttonQueteAcceptee);
                        Button bt4 = findViewById(R.id.button);
                        Button bt5 = findViewById(R.id.button2);
                        Button bt6 = findViewById(R.id.gameHorizontal);
                        Button bt7 = findViewById(R.id.gameVertical);
                        Button bt8 = findViewById(R.id.randomGame);
                        TextView tv0 = findViewById(R.id.textView);
                        TextView tv3 = findViewById(R.id.tempsDeJeu);
                        TextView tv4 = findViewById(R.id.planteAChercher);
                        TextView tv5 = findViewById(R.id.planteQuete1);
                        TextView tv6 = findViewById(R.id.textViewQueteEnCours);

                        View[] views = {bt1, bt3, bt4, bt5, bt6, bt7, bt8, tv0, tv3, tv4, tv5, tv6 };
                        for (View viewButton : views) {
                            viewButton.setVisibility(View.VISIBLE);
                        }

                        Button bt2 = findViewById(R.id.read);
                        bt2.setVisibility(View.INVISIBLE);
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

                    Toast.makeText(SauvegardeActivity.this, "Lecture sauvegarde Interne : " + lu.toString(), Toast.LENGTH_SHORT).show();
                    Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                    Log.d("s0", "split0 : " + strFields0);
                    Log.d("s1", "split1 : " + strFields1);
                    Log.d("s2", "split2 : " + strFields2);
                    Log.d("q", "quete en cours ? " + Modele.queteAcceptee);
                    Modele.firstLoadingApplication = false;
                    if (input != null)
                        input.close();

                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        lu = new StringBuffer();
                        input = new FileInputStream(mFile);
                        while ((value = input.read()) != -1)
                            lu.append((char) value);
                        Toast.makeText(SauvegardeActivity.this, "Lecture sauvegarde Externe : " + lu.toString(), Toast.LENGTH_SHORT).show();
                        Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                        Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                        Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                        Log.d("s0", "split0 : " + strFields0);
                        Log.d("s1", "split1 : " + strFields1);
                        Log.d("s2", "split2 : " + strFields2);
                        Log.d("q", "quete en cours ? " + Modele.queteAcceptee);
                        Modele.firstLoadingApplication = false;
                        if (input != null)
                            input.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void sauvegarderFichier() {
        // On crée un fichier qui correspond à l'emplacement extérieur
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists())
            directory.mkdirs();

        /* -------------------- BUT QUI DEVRA ETRE ATTEINT : SAUVEGARDER CHAQUE FOIS QUE L'ON REVIENT SUR MAINACTIVITY (APRES LE PREMIER LANCEMENT) -------------------- */
        /*mFile = new File(directory.getPath() + SAVE);
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
}*/

