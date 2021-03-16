package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantGO.databinding.ActivityJeucoffretresorBinding;

public class JeuCoffreTresor extends AppCompatActivity {
    public Integer timeCountInMilliSeconds = 5000;
    public static boolean jeuPasEncoreGagne = true;

    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    ActivityJeucoffretresorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout as per google doc instructions
        binding = ActivityJeucoffretresorBinding.inflate(getLayoutInflater());
        // add the inflated view to the Included view.
        setContentView(binding.getRoot());
        //supprimer l'animation au changement d'activité
        overridePendingTransition(0,0);

        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.gifChest.setVisibility(View.INVISIBLE);
        binding.gifShovel.setVisibility(View.INVISIBLE);
        binding.idle.setVisibility(View.INVISIBLE);
        binding.imageViewTorso2.setVisibility(View.INVISIBLE);
        binding.imageViewHat2.setVisibility(View.INVISIBLE);
        binding.buttonSuite.setVisibility(View.INVISIBLE);
        binding.buttonRemplir.setVisibility(View.INVISIBLE);
        binding.textViewTorso2.setVisibility(View.INVISIBLE);
        binding.textViewHat2.setVisibility(View.INVISIBLE);
    }

    public void remplirBarre(View view) {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.incrementProgressBy(12); // Remplir la barre de progression
    }

    public void viderBarre(View view) {
        startCountDownTimer();
        binding.buttonRemplir.setVisibility(View.VISIBLE);
    }

    private void setProgressBarValues() {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setMax((int) timeCountInMilliSeconds / 50);
        pb.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private void startCountDownTimer() {
        final ProgressBar pb = findViewById(R.id.progressBar);
        CountDownTimer countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 50) {

            @Override
            public void onTick(long millisUntilFinished) {
                Integer tempsRestant = Math.toIntExact(millisUntilFinished / 1000);
                pb.incrementProgressBy(-1); // Quand le timer est actif

                if ((millisUntilFinished / 1000) > 0 && pb.getProgress() < 99 && jeuPasEncoreGagne) { // Quand le timer est actif

                    binding.buttonVider.setVisibility(View.INVISIBLE);
                    binding.idle.setVisibility(View.INVISIBLE);
                    binding.closedChest.setVisibility(View.INVISIBLE);

                    binding.gifShovel.setVisibility(View.VISIBLE);
                    binding.chrono.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);

                    TextView tv2 = findViewById(R.id.chrono);
                    tv2.setText(String.valueOf(tempsRestant));

                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("Appuyez le plus vite possible avant la fin du temps imparti !!!");
                }

                if (pb.getProgress() >= 99) { // Quand le joueur a gagné (barre remplie)
                    jeuPasEncoreGagne = false; // Signale que le joueur a gagné

                    binding.gifShovel.setVisibility(View.INVISIBLE);
                    binding.buttonVider.setVisibility(View.INVISIBLE);
                    binding.buttonRemplir.setVisibility(View.INVISIBLE);
                    binding.chrono.setVisibility(View.INVISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);

                    binding.gifChest.setVisibility(View.VISIBLE);
                    binding.imageViewTorso2.setVisibility(View.VISIBLE);
                    binding.imageViewHat2.setVisibility(View.VISIBLE);
                    binding.buttonSuite.setVisibility(View.VISIBLE);
                    binding.textViewTorso2.setVisibility(View.VISIBLE);
                    binding.textViewHat2.setVisibility(View.VISIBLE);

                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("C'est gagné. Vous avez débloqué les récompenses suivantes :");
                }
            }

            @Override
            public void onFinish() {
                if (pb.getProgress() < 99 && jeuPasEncoreGagne) { // Quand le timer est terminé
                    binding.gifShovel.setVisibility(View.INVISIBLE);
                    binding.buttonRemplir.setVisibility(View.INVISIBLE);
                    binding.chrono.setVisibility(View.INVISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);

                    binding.buttonVider.setVisibility(View.VISIBLE);
                    binding.idle.setVisibility(View.VISIBLE);

                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("Relancez le chronomètre");
                }
                setProgressBarValues(); // call to initialize the progress bar values
            }
        }.start();
        countDownTimer.start();


        if (!jeuPasEncoreGagne) { // Quand le joueur a gagné (barre remplie), on arrêter le timer (et donc de vider la barre)
            countDownTimer.cancel();
        }
    }

    public void quitterJeu(View view) {
        Modele.jeuCoffreTresorGagne = true;
        Intent intent = new Intent(JeuCoffreTresor.this, Inventaire.class);
        JeuCoffreTresor.this.startActivity(intent);
    }
}