package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ChessGame;

/**
 * Created by jacks on 2017-06-16.
 */
public class GameScreen implements Screen{

    public static final int SPEED = 120;


    Texture img;
    int x = 0, y = 0;

    ChessGame game;

    public GameScreen(ChessGame game){
        this.game = game;
    }

    @Override
    public void show() {
        img  = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * delta;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= SPEED * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += SPEED * delta;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= SPEED * delta;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(img, x, y);
        game.getBatch().end();
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
