package com.example.test_libgdxintoandroid.Screens.GameVertical;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameVertical;
import com.example.test_libgdxintoandroid.Modele;
import com.example.test_libgdxintoandroid.Scenes.GameVertical.HudVertical;
import com.example.test_libgdxintoandroid.Scenes.HudPause;
import com.example.test_libgdxintoandroid.Sprites.Ennemies.EnnemiVertical;
import com.example.test_libgdxintoandroid.Sprites.GameVertical.MyCharacterVertical;
import com.example.test_libgdxintoandroid.Tools.GameVertical.B2WorldCreatorVertical;
import com.example.test_libgdxintoandroid.Tools.GameVertical.WorldContactListenerVertical;
import com.example.test_libgdxintoandroid.Scenes.GameVertical.GameControllerVertical;

public class PlayScreenVertical implements Screen{

    GameControllerVertical gameController;

    //Reference to our Game, used to set Screens
    public static GameVertical game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private HudVertical hud;
    private HudPause hudPause;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreatorVertical creator;

    public static boolean enJeu = false; // si false, le jeu est en pause, sinon il ne l'est pas : true

    //sprites
    private MyCharacterVertical player; // Character class object

    private Music music;

    public PlayScreenVertical(GameVertical game){
        Modele.compteurDechetCollecte = 0; // Met à 0 pour chaque nouvelle partie
        Modele.tempsPartie = 0; // Met à 0 pour chaque nouvelle partie

        gameController = new GameControllerVertical();

        atlas = new TextureAtlas("MainChar_and_Enemies_Vertical.pack");

        this.game = game;

        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(GameVertical.V_WIDTH / GameVertical.PPM, GameVertical.V_HEIGHT / GameVertical.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new HudVertical(game.batch);

        if(!enJeu) { // Si le jeu est en pause
            hudPause = new HudPause(game.batch);
        }

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level_Vertical.tmx");
       renderer = new OrthogonalTiledMapRenderer(map, 1  / GameVertical.PPM);

        //initially set our gamcam to 6be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        /*---------------------------- REND LIGNE bodies INVISIBLES--------------------*/
        b2dr.setDrawBodies(false); // REND LIGNE bodies INVISIBLES
        /*---------------------------- REND LIGNE bodies INVISIBLES--------------------*/

        creator = new B2WorldCreatorVertical(this);

        //create mario in our game world
        player = new MyCharacterVertical(this);

        world.setContactListener(new WorldContactListenerVertical());

        music = GameVertical.manager.get("audio/music/music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {

        // Valeurs de test
        //Gdx.app.log("Dechets", "Nombre dechets restants: " + Modele.compteurDechetCollecte);
        //Gdx.app.log("Temps", "temps restant: " + Modele.tempsPartie);
        //Gdx.app.log("Camera y", "Cam position is: " + gamecam.position.y);
        //Gdx.app.log("Camera x", "Cam position is: " + gamecam.position.x);

        /*************************************** PERMET DE METTRE EN PAUSE  ******************************************/
            if ((Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) || gameController.isPausePressed()) && enJeu == false && player.currentState != MyCharacterVertical.State.DEAD) {
            /*Modele.resultatpartie = "Partie perdue";
            Gdx.app.exit();*/
                music.stop();
                enJeu = true;
            }

        // PERMET DANNULER LA PAUSE
        if ((Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && enJeu == true) {
            music.play();
            enJeu = false;
        }

        // Condition victoire collecter tous les déchets
        if(Modele.compteurDechetCollecte < 1) {
            Modele.resultatpartie = "Partie gagnée";
            PlayScreenVertical.game.setScreen(new VictoryScreenVertical(PlayScreenVertical.game));
        }

        //control our player using immediate impulses
        if(player.currentState != MyCharacterVertical.State.DEAD && !enJeu) {
            if ((gameController.isRightPressed() || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && player.b2body.getLinearVelocity().x <= 2) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(),true);
            }
            if ((gameController.isLeftPressed() || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && player.b2body.getLinearVelocity().x >= -2) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(),true);
            }
            if ((gameController.isUpPressed() || Gdx.input.isKeyPressed(Input.Keys.UP)) && player.b2body.getLinearVelocity().y == 0) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }
        }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        if(!enJeu) {
        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
        }

        player.update(dt);

        for(EnnemiVertical enemy : creator.getEnnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224/ GameVertical.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        hud.update(dt);
        if(!enJeu) {
            hudPause.update(dt);
        }

        //attach our gamecam to our players.x coordinate
        if(player.currentState != MyCharacterVertical.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (EnnemiVertical enemy : creator.getEnnemies()) {
            enemy.draw(game.batch);
        }

        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();

        if(enJeu) {
            hudPause.stage.draw();
        }

        if(gameOver()){
            if (Modele.jetonRejouer > 0) {
                Modele.jetonRejouer--;
                game.setScreen(new GameOverScreenVertical(game));
                dispose();
            }
            else {
                music.stop();
                Modele.resultatpartie = "Partie perdue";
                Gdx.app.exit();
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            if(!enJeu) { // Si le jeu est en pause
                gameController.draw();
            }
        }

    }

    public boolean gameOver(){
        if(player.currentState == MyCharacterVertical.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

   @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        hudPause.dispose();
    }
}