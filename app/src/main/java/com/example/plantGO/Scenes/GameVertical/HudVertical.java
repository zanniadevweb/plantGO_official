package com.example.plantGO.Scenes.GameVertical;

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
import com.example.plantGO.LibGDXLaunchers.GameVertical;
import com.example.plantGO.Modele;
import com.example.plantGO.Screens.GameVertical.PlayScreenVertical;

public class HudVertical implements Disposable{

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
    private Label worldLabel1;
    private Label worldLabel2;
    private Label marioLabel;

    public HudVertical(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(GameVertical.V_WIDTH, GameVertical.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TEMPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel1 = new Label("Ecrase tous", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel2 = new Label("les dechets", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("DECHETS RESTANTS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel1).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(worldLabel2).expandX();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
       timeCount += dt;
        if(timeCount >= 1 && !PlayScreenVertical.enJeu){
            worldTimer--;
            Modele.tempsPartie = worldTimer; // Permet de récupérer le temps de jeu
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void dechetsRestants(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void ecraseUnDechet(int value){
        score -= value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

    /*public boolean isTimeUp() { return timeUp; }*/
}