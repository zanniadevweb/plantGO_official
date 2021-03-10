package com.example.test_libgdxintoandroid.Sprites.GameVertical;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameVertical;
import com.example.test_libgdxintoandroid.Screens.GameVertical.PlayScreenVertical;
//import com.example.test_libgdxintoandroid.Sprites.Other.FireBall;


/**
 * Created by brentaureli on 8/27/15.
 */
public class MyCharacterVertical extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private TextureRegion marioDead;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsDead;

    public MyCharacterVertical(PlayScreenVertical screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        // Define run animation
        for (int frame = 1; frame < 4; frame++) {
            frames.add(new TextureRegion(getTexture(), frame * 16, 11, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);

        // clear frames for next animation
        frames.clear();

        // define jump animation
        for (int frame = 1; frame < 6; frame++) {
            frames.add(new TextureRegion(getTexture(), frame * 16, 11, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);

        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16);

        //create dead mario texture region
        marioDead = new TextureRegion(getTexture(), 96, 0, 16, 16); //-- NEW

        defineGdx();
        setBounds(0, 0, 16 / GameVertical.PPM, 16 / GameVertical.PPM);
        setRegion(marioStand);
    }

    public void update (float dt) {
        setPosition (b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();
        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState) {
            case DEAD:
                region = marioDead;
                break;
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        //if mario is running right and the texture isnt facing right... flip it.
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }


    public State getState() {
        if (marioIsDead) {
            return State.DEAD;
        }
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }
        else {
            return State.STANDING;
        }
    }

        public void hit(){
        die();
        }

    public void die() {

        if (!isDead()) {

            GameVertical.manager.get("audio/music/music.ogg", Music.class).stop();
            GameVertical.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = GameVertical.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void defineGdx(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(15 / GameVertical.PPM, 15 / GameVertical.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GameVertical.PPM);

        fdef.filter.categoryBits = GameVertical.MARIO_BIT;
        fdef.filter.maskBits = GameVertical.GROUND_BIT |
                GameVertical.ENNEMY_BIT |
                GameVertical.ENNEMY_HEAD_BIT |
                GameVertical.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this); // ALTERNATIVE SI ON NE FAIT PAS GRANDIR MARIO

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GameVertical.PPM, 6 / GameVertical.PPM), new Vector2(2 / GameVertical.PPM, 6 / GameVertical.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }

    }

