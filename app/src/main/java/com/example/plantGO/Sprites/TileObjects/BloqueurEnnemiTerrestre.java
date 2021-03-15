package com.example.plantGO.Sprites.TileObjects;

import com.badlogic.gdx.math.Rectangle;
import com.example.plantGO.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.Screens.GameHorizontal.PlayScreenHorizontal;

public class BloqueurEnnemiTerrestre extends InteractiveTileObject {
    public BloqueurEnnemiTerrestre(PlayScreenHorizontal screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameHorizontal.BLOQUEURENNEMITERRESTRE_BIT);
    }

    @Override
    public void onHeadHit() {
    }

}
