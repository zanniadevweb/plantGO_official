package com.example.plantGO.libGDX.Sprites.TileObjects;

import com.badlogic.gdx.math.Rectangle;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameVertical;
import com.example.plantGO.libGDX.Screens.GameHorizontal.PlayScreenHorizontal;

public class BloqueurEnnemiTerrestre extends InteractiveTileObject {
    public BloqueurEnnemiTerrestre(PlayScreenHorizontal screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameHorizontal.BLOQUEURENNEMITERRESTRE_BIT);
        setCategoryFilter(GameVertical.BLOQUEURENNEMITERRESTRE_BIT);
    }

    @Override
    public void onHeadHit() {
    }

}
