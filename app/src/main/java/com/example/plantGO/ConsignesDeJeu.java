package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.example.plantGO.databinding.ActivityConsignesjeuBinding;

public class ConsignesDeJeu extends AppCompatActivity {


    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    ActivityConsignesjeuBinding binding;

    // Avant le lancement d'un mini-jeu l'application est dans l'état "onCreate"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout as per google doc instructions
        binding = ActivityConsignesjeuBinding.inflate(getLayoutInflater());
        // add the inflated view to the Included view.
        setContentView(binding.getRoot());
        //supprimer l'animation au changement d'activité
        overridePendingTransition(0,0);

        if (Modele.resultatpartie.equals("Partie non déterminée") && !(Modele.partieDejaLance)) {
            masquerLabelsExperienceTempsJeu();
            binding.texteVictoireDefaite.setVisibility(View.INVISIBLE);
            binding.gifConsignesJeuVertical.setVisibility(View.VISIBLE);
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
            binding.texteVictoireDefaite.setVisibility(View.VISIBLE);
            afficherLabelsExperienceTempsJeu();

            if (Modele.resultatpartie.equals("Partie gagnée")) {
                binding.texteVictoireDefaite.setText("Vous avez gagné !");
                afficheExperienceTempsJeuSiGagne();
            }
            if (Modele.resultatpartie.equals("Partie perdue")) {
                binding.texteVictoireDefaite.setText("Vous avez perdu !");
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
        binding.titreJeu.setVisibility(View.VISIBLE);
        binding.consignesJeu.setVisibility(View.VISIBLE);
    }

    public void masquerLabelsConsignesJeu() {
        binding.titreJeu.setVisibility(View.INVISIBLE);
        binding.consignesJeu.setVisibility(View.INVISIBLE);
    }

    public void changerTexteLancerJeuEnRetourMenu() {
        binding.lancerJeu.setText("<== Retour au menu principal");
    }

    public void afficherTexteConsignesJeuHorizontal() {
        String titreJeuHorizontal = "Jeu Horizontal";
        String consignesJeuHorizontal = "Appuyez sur la touche haut pour sauter."
                + "\nAllez au bout du niveau pour gagner.";
        binding.titreJeu.setText(titreJeuHorizontal);
        binding.consignesJeu.setText(consignesJeuHorizontal);
    }

    public void afficherTexteConsignesJeuVertical() {
        String titreJeuVertical = "Jeu Vertical";
        String consignesJeuVertical = "Déplacez-vous avec les touches directionnelles gauche "
                + "\net droite. La touche haut permet de sauter."
                + "\nDétruisez tous les déchets en sautant dessus pour gagner.";
        binding.gifConsignesJeuVertical.setVisibility(View.VISIBLE);
        binding.titreJeu.setText(titreJeuVertical);
        binding.consignesJeu.setText(consignesJeuVertical);
    }

    public void afficheExperienceTempsJeuSiGagne() {
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

            binding.texteResultatMiniJeu.setText( "Gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + Modele.experienceTotale + " xp");

            // Récupération du temps de jeu
            binding.tempsDeJeu.setText("Temps de jeu = " + String.valueOf(tempsJeu) + " secondes");

        }

    }

    public void afficheMessagePasGainExperienceSiPerdu() {
        binding.texteResultatMiniJeu.setText( "PAS de gain expérience");
        binding.tempsDeJeu.setText("Temps PAS PRIS en compte");
    }

    public void afficherLabelsExperienceTempsJeu() {
        binding.texteResultatMiniJeu.setVisibility(View.VISIBLE);
        binding.tempsDeJeu.setVisibility(View.VISIBLE);
    }

    public void masquerLabelsExperienceTempsJeu() {
        binding.texteResultatMiniJeu.setVisibility(View.INVISIBLE);
        binding.tempsDeJeu.setVisibility(View.INVISIBLE);
    }

}

