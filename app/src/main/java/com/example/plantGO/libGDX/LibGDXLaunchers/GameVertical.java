package com.example.plantGO.libGDX.LibGDXLaunchers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.plantGO.libGDX.Screens.GameVertical.PlayScreenVertical;


public class GameVertical extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    // Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short ENNEMY_BIT = 64;
    public static final short ENNEMY_HEAD_BIT = 128;
    public static final short BLOQUEURENNEMITERRESTRE_BIT = 256;
    public static final short ITEM_BIT = 512;

    public static SpriteBatch batch;

    /* WARNING Using AssetManager in a static way can cause issues, especially on Android.
    Instead you may want to pass around Assetmanager to those the classes that need it.
    We will use it in the static context to save time for now. */
    public static AssetManager manager;

    @Override
    public void create () {
        batch = new SpriteBatch();

        manager = new AssetManager();
        manager.load("audio/music/music.ogg", Music.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/lose.wav", Sound.class);
        manager.load("audio/sounds/win.wav", Sound.class);
        manager.load("audio/sounds/dechet.wav", Sound.class);

        manager.finishLoading();

        setScreen(new PlayScreenVertical( this));
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render ()
    {
        super.render();
    }

    // Life cycle methods of ApplicationListener

}
