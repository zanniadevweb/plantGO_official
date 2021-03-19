package com.example.plantGO.libGDX.Scenes.GameHorizontal;

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
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;
import com.example.plantGO.Modele;
import com.example.plantGO.libGDX.Screens.GameHorizontal.PlayScreenHorizontal;

public class HudHorizontal implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    //private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;

    //Scene2D widgets
    private Label countdownLabel;
    private Label timeLabel;
    private Label worldLabel;

    public HudHorizontal(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 300;
        timeCount = 0;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(GameHorizontal.V_WIDTH, GameHorizontal.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TEMPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Va au bout du niveau sans être touché", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(timeLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
       timeCount += dt;
        if(timeCount >= 1 && !PlayScreenHorizontal.enPause){
            worldTimer--;
            Modele.tempsPartie = worldTimer; // Permet de récupérer le temps de jeu
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}