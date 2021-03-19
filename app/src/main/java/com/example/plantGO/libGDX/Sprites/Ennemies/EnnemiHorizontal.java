package com.example.plantGO.libGDX.Sprites.Ennemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.example.plantGO.libGDX.Screens.GameHorizontal.PlayScreenHorizontal;
import com.example.plantGO.libGDX.Sprites.GameHorizontal.MyCharacterHorizontal;

public abstract class EnnemiHorizontal extends Sprite {
    protected World world;
    protected PlayScreenHorizontal screen;
    public Body b2body;
    public Vector2 velocity;

    public EnnemiHorizontal(PlayScreenHorizontal screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnnemy();
        velocity = new Vector2(-1, -2);
    }

    protected abstract void defineEnnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead(MyCharacterHorizontal mario);

    public void reverseVelocity(boolean x, boolean y){
        if(x) {
            velocity.x = -velocity.x;
        }
        if(y) {
            velocity.y = -velocity.y;
        }
    }

}
