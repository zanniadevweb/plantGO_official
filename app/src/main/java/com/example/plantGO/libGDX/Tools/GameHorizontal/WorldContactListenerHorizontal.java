package com.example.plantGO.libGDX.Tools.GameHorizontal;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.libGDX.Sprites.Ennemies.EnnemiHorizontal;
import com.example.plantGO.libGDX.Sprites.GameHorizontal.MyCharacterHorizontal;
import com.example.plantGO.libGDX.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListenerHorizontal implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" ||fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case GameHorizontal.ENNEMY_HEAD_BIT | GameHorizontal.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == GameHorizontal.ENNEMY_HEAD_BIT)
                    ((EnnemiHorizontal)fixA.getUserData()).hitOnHead((MyCharacterHorizontal) fixB.getUserData());
                else
                    ((EnnemiHorizontal)fixB.getUserData()).hitOnHead((MyCharacterHorizontal) fixA.getUserData());
                break;
            case GameHorizontal.ENNEMY_BIT | GameHorizontal.BLOQUEURENNEMITERRESTRE_BIT:
                if(fixA.getFilterData().categoryBits == GameHorizontal.ENNEMY_HEAD_BIT)
                    ((EnnemiHorizontal)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((EnnemiHorizontal)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case GameHorizontal.MARIO_BIT | GameHorizontal.ENNEMY_BIT:
                if(fixA.getFilterData().categoryBits == GameHorizontal.MARIO_BIT)
                    ((MyCharacterHorizontal) fixA.getUserData()).hit();
                else
                    ((MyCharacterHorizontal) fixB.getUserData()).hit();
                break;
            case GameHorizontal.ENNEMY_BIT | GameHorizontal.ENNEMY_BIT:
                ((EnnemiHorizontal)fixA.getUserData()).reverseVelocity(true, false);
                ((EnnemiHorizontal)fixB.getUserData()).reverseVelocity(true, false);
                break;
    }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}