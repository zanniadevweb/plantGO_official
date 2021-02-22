package com.example.test_libgdxintoandroid.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameHorizontal;

public class HudPause implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    //private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    public HudPause(SpriteBatch sb){

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(GameHorizontal.V_WIDTH, GameHorizontal.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        worldLabel = new Label("PAUSE. Appuyez sur l'écran pour jouer", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(worldLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
    }

    public static void addScore(int value){
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

    /*public boolean isTimeUp() { return timeUp; }*/
}