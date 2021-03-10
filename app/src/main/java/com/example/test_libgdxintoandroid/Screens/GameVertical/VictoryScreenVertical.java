package com.example.test_libgdxintoandroid.Screens.GameVertical;

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
import com.example.test_libgdxintoandroid.LibGDXLaunchers.GameVertical;

public class VictoryScreenVertical implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public VictoryScreenVertical(Game game){
        this.game = game;
        viewport = new FitViewport(GameVertical.V_WIDTH, GameVertical.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((GameVertical) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("YOU WIN", font);
        Label playAgainLabel = new Label("Click to return to plantGO application", font);

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
