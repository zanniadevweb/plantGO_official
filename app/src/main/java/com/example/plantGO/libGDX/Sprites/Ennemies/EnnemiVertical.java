package com.example.plantGO.libGDX.Sprites.Ennemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.example.plantGO.libGDX.Screens.GameVertical.PlayScreenVertical;
import com.example.plantGO.libGDX.Sprites.GameVertical.MyCharacterVertical;

public abstract class EnnemiVertical extends Sprite {
    protected World world;
    protected PlayScreenVertical screen;
    public Body b2body;
    public Vector2 velocity;

    public EnnemiVertical(PlayScreenVertical screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnnemy();
        velocity = new Vector2(-1, -3);
    }

    protected abstract void defineEnnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead(MyCharacterVertical mario);

    public void reverseVelocity(boolean x, boolean y){
        if(x) {
            velocity.x = -velocity.x;
        }
        if(y) {
            velocity.y = -velocity.y;
        }
    }

}
