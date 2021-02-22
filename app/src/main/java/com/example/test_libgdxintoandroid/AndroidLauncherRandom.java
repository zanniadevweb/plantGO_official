package com.example.test_libgdxintoandroid;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameHorizontal;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameVertical;

public class AndroidLauncherRandom extends AndroidApplication {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( Modele.random == 1 || Modele.testBoolean == false) {
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            initialize(new GameHorizontal(), config);
        }
        if ( Modele.random == 2 || Modele.testBoolean == true) {
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            initialize(new GameVertical(), config);
        }
    }
}
