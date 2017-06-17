package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ChessGame;

/**
 * Created by jacks on 2017-06-17.
 */
public class MainMenuScreen implements Screen{

    private static final int EXIT_BUTTON_WIDTH = 133;
    private static final int EXIT_BUTTON_HEIGHT = 66;
    private static final int EXIT_BUTTON_Y = 200;
    private static final int PLAY_BUTTON_WIDTH = 150;
    private static final int PLAY_BUTTON_HEIGHT = 74;
    private static final int PLAY_BUTTON_Y = 500;


    ChessGame game;

    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;

    public MainMenuScreen(ChessGame game){
        this.game = game;
        exitButtonActive = new Texture("exitButtonActive.png");
        exitButtonInactive = new Texture("exitButtonInactive.png");
        playButtonActive = new Texture("playButtonActive.png");
        playButtonInactive = new Texture("playButtonInactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        {
          int x = ChessGame.WIDTH / 2 - PLAY_BUTTON_WIDTH/2;
          if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && ChessGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && ChessGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y ){
              game.getBatch().draw(playButtonActive, ChessGame.WIDTH / 2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
          } else {
              game.getBatch().draw(playButtonInactive, ChessGame.WIDTH / 2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
          }
        }
        {
            int x = ChessGame.WIDTH / 2 - EXIT_BUTTON_WIDTH/2;
            if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && ChessGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && ChessGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y ){
                game.getBatch().draw(exitButtonActive, ChessGame.WIDTH / 2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            } else {
                game.getBatch().draw(exitButtonInactive, ChessGame.WIDTH / 2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            }
        }

        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}
