package com.example.plantGO.libGDX.Tools.GameVertical;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameVertical;
import com.example.plantGO.libGDX.Sprites.Ennemies.EnnemiVertical;
import com.example.plantGO.libGDX.Sprites.GameVertical.MyCharacterVertical;
import com.example.plantGO.libGDX.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListenerVertical implements ContactListener {
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
            case GameVertical.ENNEMY_HEAD_BIT | GameVertical.CHARACTER_BIT:
                if(fixA.getFilterData().categoryBits == GameVertical.ENNEMY_HEAD_BIT)
                    ((EnnemiVertical)fixA.getUserData()).hitOnHead((MyCharacterVertical) fixB.getUserData());
                else
                    ((EnnemiVertical)fixB.getUserData()).hitOnHead((MyCharacterVertical) fixA.getUserData());
                break;
            case GameVertical.ENNEMY_BIT | GameVertical.BLOQUEURENNEMITERRESTRE_BIT:
                if(fixA.getFilterData().categoryBits == GameVertical.ENNEMY_HEAD_BIT)
                    ((EnnemiVertical)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((EnnemiVertical)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case GameVertical.CHARACTER_BIT | GameVertical.ENNEMY_BIT:
                if(fixA.getFilterData().categoryBits == GameVertical.CHARACTER_BIT)
                    ((MyCharacterVertical) fixA.getUserData()).hit();
                else
                    ((MyCharacterVertical) fixB.getUserData()).hit();
                break;
            case GameVertical.ENNEMY_BIT | GameVertical.ENNEMY_BIT:
                ((EnnemiVertical)fixA.getUserData()).reverseVelocity(true, false);
                ((EnnemiVertical)fixB.getUserData()).reverseVelocity(true, false);
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