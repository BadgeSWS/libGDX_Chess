package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ChessGame;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.PickResult;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacks on 2017-06-16.
 */
public class GameScreen implements Screen{

    public static final int SPEED = 400;

    private Texture board;
    private ChessGame game;
    private static List<Piece> pieces = new ArrayList<Piece>();
    private int elementMoved;
    private int oldX, oldY;

    public GameScreen(ChessGame game){
        this.game = game;
    }

    @Override
    public void show() {
        elementMoved = -1;
        board  = new Texture("board.png");
        pieces.add(new Piece("BLACK", "ROOK", 0, 0));
        pieces.add(new Piece("BLACK", "KNIGHT", 0, 1));
        pieces.add(new Piece("BLACK", "BISHOP", 0, 2));
        pieces.add(new Piece("BLACK", "QUEEN", 0, 3));
        pieces.add(new Piece("BLACK", "KING", 0, 4));
        pieces.add(new Piece("BLACK", "BISHOP", 0, 5));
        pieces.add(new Piece("BLACK", "KNIGHT", 0, 6));
        pieces.add(new Piece("BLACK", "ROOK", 0, 7));
        for(int col = 0; col < 8; col++)
            pieces.add(new Piece("BLACK", "PAWN", 1, col));

        pieces.add(new Piece("WHITE", "ROOK", 7, 0));
        pieces.add(new Piece("WHITE", "KNIGHT", 7, 1));
        pieces.add(new Piece("WHITE", "BISHOP", 7, 2));
        pieces.add(new Piece("WHITE", "QUEEN", 7, 3));
        pieces.add(new Piece("WHITE", "KING", 7, 4));
        pieces.add(new Piece("WHITE", "BISHOP", 7, 5));
        pieces.add(new Piece("WHITE", "KNIGHT", 7, 6));
        pieces.add(new Piece("WHITE", "ROOK", 7, 7));
        for(int col = 0; col < 8; col++)
            pieces.add(new Piece("WHITE", "PAWN", 6, col));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched()){
            if(elementMoved == -1) {
                oldX = Gdx.input.getX();
                oldY = ChessGame.HEIGHT - Gdx.input.getY();
                int row, col;
                row = Gdx.input.getY()/90;
                col = Gdx.input.getX()/90;

                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).getRow() == row && pieces.get(i).getCol() == col) {
                        elementMoved = i;
                    }
                }
            } else {
                Piece piece = pieces.get(elementMoved);
                int changeInX = Gdx.input.getX() - oldX;
                int changeInY = (ChessGame.HEIGHT - Gdx.input.getY()) - oldY;
                piece.setX(piece.getX() + changeInX);
                piece.setY(piece.getY() + changeInY);

                oldX = Gdx.input.getX();
                oldY = ChessGame.HEIGHT - Gdx.input.getY();
            }
        } else {
            if(elementMoved != -1) {
                Piece piece = pieces.get(elementMoved);
                piece.movePiece(Gdx.input.getY() / 90, Gdx.input.getX() / 90);
            }
            elementMoved = -1;
        }

        game.getBatch().begin();
        game.getBatch().draw(board, 0, 0, ChessGame.WIDTH, ChessGame.HEIGHT);
        for(int i = 0; i < pieces.size(); i++){
            Piece piece = pieces.get(i);
            game.getBatch().draw(piece.getImg(), piece.getX(), piece.getY(), 90,90);
        }
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

    public static List<Piece> getPieces() {
        return pieces;
    }
}
