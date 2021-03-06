package com.example.plantGO.libGDX.Screens.GameHorizontal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.example.plantGO.Modele;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.libGDX.Scenes.GameHorizontal.HudHorizontal;
import com.example.plantGO.libGDX.Scenes.HudPause;
import com.example.plantGO.libGDX.Sprites.GameHorizontal.MyCharacterHorizontal;
import com.example.plantGO.libGDX.Tools.GameHorizontal.B2WorldCreatorHorizontal;
import com.example.plantGO.libGDX.Tools.GameHorizontal.WorldContactListenerHorizontal;
import com.example.plantGO.libGDX.Sprites.Ennemies.EnnemiHorizontal;
import com.example.plantGO.libGDX.Scenes.GameHorizontal.GameControllerHorizontal;

public class PlayScreenHorizontal implements Screen {

    GameControllerHorizontal gameController;

    //Reference to our Game, used to set Screens
    private GameHorizontal game;
    private TextureAtlas atlas;
    private HudPause hudPause;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private HudHorizontal hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreatorHorizontal creator;

    public static boolean enPause = false; // si false, le jeu est en cours, sinon il ne l'est pas : false

    //sprites
    private MyCharacterHorizontal player; // Character class object

    private Music music;

    public PlayScreenHorizontal(GameHorizontal game){
        Modele.tempsPartie = 0; // Met à 0 pour chaque nouvelle partie

        gameController = new GameControllerHorizontal();

        atlas = new TextureAtlas("MainChar_and_Enemies_Horizontal.pack");

        this.game = game;

        //create cam used to follow the character through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(GameHorizontal.V_WIDTH / GameHorizontal.PPM, GameHorizontal.V_HEIGHT / GameHorizontal.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new HudHorizontal(game.batch);

        if(!enPause) { // Si le jeu est en pause
            hudPause = new HudPause(game.batch);
        }

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level_Horizontal.tmx");
       renderer = new OrthogonalTiledMapRenderer(map, 1  / GameHorizontal.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        b2dr.setDrawBodies(false); // REND LIGNE bodies INVISIBLES

        creator = new B2WorldCreatorHorizontal(this);

        //create the character in our game world
        player = new MyCharacterHorizontal(this);

        world.setContactListener(new WorldContactListenerHorizontal());

        music = GameHorizontal.manager.get("audio/music/music.ogg", Music.class);
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

    public void handleInput(float dt){

        /*************************************** PERMET DE METTRE EN PAUSE  ******************************************/
        if (((Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) || gameController.isPausePressed()) && !enPause && player.currentState != MyCharacterHorizontal.State.DEAD)) {
            music.stop();
            enPause = true;
        }

        // PERMET DANNULER LA PAUSE
        if ((Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && enPause) {
            music.play();
            enPause = false;
        }

        if (gamecam.position.x >32) {
            music.stop();
            Modele.resultatpartie = "Partie gagnée";
            GameHorizontal.manager.get("audio/sounds/win.wav", Sound.class).play();
            game.setScreen(new VictoryScreenHorizontal(game));
        }

        //control our player using immediate impulses
       if(player.currentState != MyCharacterHorizontal.State.DEAD && !enPause) {
            if ((Gdx.input.isKeyPressed(Input.Keys.UP) || gameController.isUpPressed()) && player.b2body.getLinearVelocity().y == 0) {
                player.b2body.applyLinearImpulse(new Vector2(0, 40f), player.b2body.getWorldCenter(), true); // 30f par défaut
                player.b2body.setGravityScale(5.0f);
            }
            else {
                // Le personnage court vers la droite indéfiniment par défaut
                player.b2body.setLinearVelocity(new Vector2(1, 0));
            }
       }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        //takes 1 step in the physics simulation(60 times per second)
        if(!enPause) {
            //takes 1 step in the physics simulation(60 times per second)
            world.step(1 / 60f, 6, 2);
        }
        player.update(dt);

        for(EnnemiHorizontal enemy : creator.getEnnemies()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / GameHorizontal.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        hud.update(dt);
        if(!enPause) {
            hudPause.update(dt);
        }

        //attach our gamecam to our players.x coordinate
        if(player.currentState != MyCharacterHorizontal.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
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

        for (EnnemiHorizontal enemy : creator.getEnnemies()) {
            enemy.draw(game.batch);
        }

        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(enPause) {
            hudPause.stage.draw();
        }

        if(gameOver()){
            if (Modele.jetonRejouer > 0) {
                Modele.jetonRejouer--;
                game.setScreen(new GameOverScreenHorizontal(game));
                dispose();
            }
            else {
                music.stop();
                Modele.resultatpartie = "Partie perdue";
                Gdx.app.exit();
            }
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            if(!enPause) { // Si le jeu est en pause
            gameController.draw();
            }
    }

    public boolean gameOver(){
        if(player.currentState == MyCharacterHorizontal.State.DEAD && player.getStateTimer() > 3){
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
    }
}