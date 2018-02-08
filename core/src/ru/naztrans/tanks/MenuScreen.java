package ru.naztrans.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MenuScreen implements Screen{
    private SpriteBatch batch;
    private BitmapFont font;
    private Rectangle startGame, exitGame;
    Texture bg;
    private ShapeRenderer shapeRenderer;
    public MenuScreen(SpriteBatch batch) {
        this.batch=batch;
    }

    @Override
    public void show() {
        font = Assets.getInstance().getAssetManager().get("zorque48.ttf", BitmapFont.class);
        bg=Assets.getInstance().getBackGround();
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        startGame = new Rectangle();
        startGame.set(500,500,300, 60);
        exitGame = new Rectangle();
        exitGame.set(600, 300, 120,60);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);

        font.draw(batch, "Start Game", startGame.x, startGame.y+55); // почему-то буквы рисуются ниже, чем хотелось бы
        font.draw(batch, "Exit", exitGame.x, exitGame.y+55);
        batch.end();
        shapeRenderer.begin();
        shapeRenderer.rect(startGame.x, startGame.y, startGame.width, startGame.height);
        shapeRenderer.rect(exitGame.x, exitGame.y, exitGame.width, exitGame.height);
        shapeRenderer.end();
    }

    public void update(float dt){
        if(Gdx.input.isTouched()){
            if (startGame.contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())){
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.GAME);
            }
            if (exitGame.contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())){
                Gdx.app.exit();
            }
        }
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

    }
}
