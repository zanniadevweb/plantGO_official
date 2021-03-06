package com.example.plantGO;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameVertical;

public class AndroidLauncherRandom extends AndroidApplication {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( Modele.randomMiniJeu == 1 || !Modele.estLanceJeuVertical) {
            Modele.pasEncoreAjoutExperience = true;
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            initialize(new GameHorizontal(), config);
        }
        if ( Modele.randomMiniJeu == 2 || Modele.estLanceJeuVertical) {
            Modele.pasEncoreAjoutExperience = true;
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            initialize(new GameVertical(), config);
        }
    }
}
