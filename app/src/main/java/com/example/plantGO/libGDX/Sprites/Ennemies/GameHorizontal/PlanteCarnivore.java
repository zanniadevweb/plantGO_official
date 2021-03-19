package com.example.plantGO.libGDX.Sprites.Ennemies.GameHorizontal;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.libGDX.Screens.GameHorizontal.PlayScreenHorizontal;
import com.example.plantGO.libGDX.Sprites.Ennemies.EnnemiHorizontal;
import com.example.plantGO.libGDX.Sprites.GameHorizontal.MyCharacterHorizontal;

public class PlanteCarnivore extends EnnemiHorizontal
{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion goombaStand;
    private boolean setToDestroy;
    private boolean destroyed;
    //float angle;

    public PlanteCarnivore(PlayScreenHorizontal screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int frame = 0; frame < 2; frame++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("plantecarnivore"), 0, 0, 16, 24));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("plantecarnivore"), 16, 0, 16, 24));
        }
        walkAnimation = new Animation(0.2f, frames);
        stateTime = 0;

        setBounds(getX(), getY(), 16 / GameHorizontal.PPM, 16 / GameHorizontal.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameHorizontal.PPM);

        fdef.filter.categoryBits = GameHorizontal.ENNEMY_BIT;
        fdef.filter.maskBits = GameHorizontal.GROUND_BIT |
                GameHorizontal.ENNEMY_BIT |
                GameHorizontal.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / GameHorizontal.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / GameHorizontal.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / GameHorizontal.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / GameHorizontal.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GameHorizontal.ENNEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(MyCharacterHorizontal mario) {
        mario.hit();
    }
}
