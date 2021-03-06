package com.example.plantGO.libGDX.Screens.GameHorizontal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.plantGO.libGDX.LibGDXLaunchers.GameHorizontal;

public class VictoryScreenHorizontal implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public VictoryScreenHorizontal(Game game){
        this.game = game;
        viewport = new FitViewport(GameHorizontal.V_WIDTH, GameHorizontal.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((GameHorizontal) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("VICTOIRE", font);
        Label playAgainLabel = new Label("Touche l'écran pour quitter le jeu", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        Image WinImg = new Image(new Texture("Cup_Win.png"));
        WinImg.setSize(50, 50);
        table.row().pad(30, 5, 5, 5);;
        table.add(WinImg).size(WinImg.getWidth(), WinImg.getHeight());

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            dispose();
            Gdx.app.exit();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
