package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JeuCoffreTresor extends AppCompatActivity {
    public Integer timeCountInMilliSeconds = 5000;
    public static boolean jeuPasEncoreGagne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeucoffretresor);

        ProgressBar pb = findViewById(R.id.progressBar);
        ImageView imgv1 = findViewById(R.id.gifChest);
        ImageView imgv2 = findViewById(R.id.gifShovel);
        ImageView imgv3 = findViewById(R.id.idle);
        ImageView imgv4 = findViewById(R.id.imageViewTorso2);
        ImageView imgv5 = findViewById(R.id.imageViewHat2);
        Button bt3 = findViewById(R.id.buttonSuite);
        Button bt2 = findViewById(R.id.buttonRemplir);
        TextView tv4 = findViewById(R.id.textViewTorso2);
        TextView tv5 = findViewById(R.id.textViewHat2);

        View[] views = { pb, imgv1, imgv2, imgv3, imgv4, imgv5, imgv5, bt2, bt3, tv4, tv5 };
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void remplirBarre(View view) {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.incrementProgressBy(12); // Remplir la barre de progression
    }

    public void viderBarre(View view) {
        startCountDownTimer();
        Button bt2 = findViewById(R.id.buttonRemplir);
        bt2.setVisibility(View.VISIBLE);
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
                Log.e("seconds remaining : ", "seconds remaining : " + millisUntilFinished / 1000);
                Integer tempsRestant = Math.toIntExact(millisUntilFinished / 1000);

                pb.incrementProgressBy(-1); // Quand le timer est actif

                if ((millisUntilFinished / 1000) > 0 && pb.getProgress() < 99 && jeuPasEncoreGagne == true) { // Quand le timer est actif

                    Button bt1 = findViewById(R.id.buttonVider);
                    ImageView imgv3 = findViewById(R.id.idle);
                    ImageView imgv4 = findViewById(R.id.closedChest);

                    View[] views1 = { bt1, imgv3, imgv4 };
                    for (View view : views1) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    TextView tv2 = findViewById(R.id.chrono);

                    View[] views2 = { pb, imgv1, tv2 };
                    for (View view : views2) {
                        view.setVisibility(View.VISIBLE);
                    }

                    tv2.setText(String.valueOf(tempsRestant));

                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("Appuyez le plus vite possible avant la fin du temps imparti !!!");
                }

                if (pb.getProgress() >= 99) { // Quand le joueur a gagné (barre remplie)
                    jeuPasEncoreGagne = false; // Signale que le joueur a gagné

                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    Button bt1 = findViewById(R.id.buttonVider);
                    Button bt2 = findViewById(R.id.buttonRemplir);
                    TextView tv1 = findViewById(R.id.textView);
                    TextView tv2 = findViewById(R.id.chrono);

                    View[] views1 = { pb, imgv1, bt1, bt2, tv2 };
                    for (View view : views1) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    ImageView imgv2 = findViewById(R.id.gifChest);
                    ImageView imgv4 = findViewById(R.id.imageViewTorso2);
                    ImageView imgv5 = findViewById(R.id.imageViewHat2);
                    Button bt3 = findViewById(R.id.buttonSuite);
                    TextView tv4 = findViewById(R.id.textViewTorso2);
                    TextView tv5 = findViewById(R.id.textViewHat2);

                    View[] views2 = { imgv2, imgv4, imgv5, bt3, tv4, tv5 };
                    for (View view : views2) {
                        view.setVisibility(View.VISIBLE);
                    }

                    tv1.setText("C'est gagné. Vous avez débloqué les récompenses suivantes :");
                }
            }

            @Override
            public void onFinish() {
                if (pb.getProgress() < 99 && jeuPasEncoreGagne == true) { // Quand le timer est terminé
                    Button bt2 = findViewById(R.id.buttonRemplir);
                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    ImageView imgv3 = findViewById(R.id.idle);
                    Button bt1 = findViewById(R.id.buttonVider);
                    TextView tv1 = findViewById(R.id.textView);
                    TextView tv2 = findViewById(R.id.chrono);

                    View[] views1 = { pb, imgv1, bt2, tv2 };
                    for (View view : views1) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    View[] views2 = { bt1, imgv3 };
                    for (View view : views2) {
                        view.setVisibility(View.VISIBLE);
                    }

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