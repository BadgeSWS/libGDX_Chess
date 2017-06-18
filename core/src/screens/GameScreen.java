package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ChessGame;
import pieces.Piece;

/**
 * Created by jacks on 2017-06-16.
 */
public class GameScreen implements Screen{

    public static final int SPEED = 400;

    Texture board;
    ChessGame game;
    Piece[][] pieces = new Piece[8][8];

    public GameScreen(ChessGame game){
        this.game = game;
    }

    @Override
    public void show() {
        board  = new Texture("board.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched())
            System.out.println(Gdx.input.getX()/90 + ", " + Gdx.input.getY()/90);

        game.getBatch().begin();
        game.getBatch().draw(board, 0, 0, ChessGame.WIDTH, ChessGame.HEIGHT);
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
