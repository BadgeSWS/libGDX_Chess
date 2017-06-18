package pieces;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ChessGame;
import screens.GameScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacks on 2017-06-17.
 */
public class Piece {

    private Texture img;
    private String color;
    private String type;
    private int row;
    private int col;
    private int x;
    private int y;


    public Piece(String color, String type, int row, int col) {
        img = new Texture("1x1.png");
        this.color = color;
        this.type = type;
        this.row = row;
        this.col = col;
        this.x = colToX(col);
        this.y = rowToY(row);
        if(type.equals("KING"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackKnight.png");
            else
                img = new Texture("pieces/whiteKnight.png");
        else if(type.equals("QUEEN"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackQueen.png");
            else
                img = new Texture("pieces/whiteQueen.png");
        else if(type.equals("PAWN"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackPawn.png");
            else
                img = new Texture("pieces/whitePawn.png");
        else if(type.equals("BISHOP"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackBishop.png");
            else
                img = new Texture("pieces/whiteBishop.png");
        else if(type.equals("ROOK"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackRook.png");
            else
                img = new Texture("pieces/whiteRook.png");
        else if(type.equals("KNIGHT"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackKnight.png");
            else
                img = new Texture("pieces/whiteKnight.png");
    }

    public boolean canBishopMove(int row, int col){
        boolean isDiagonal;
        int diffCol = Math.abs(this.row - row);
        int diffEow = Math.abs(this.col - col);
        isDiagonal = diffCol == diffEow;

        boolean isAPeiceInThePath = false;

        if(isDiagonal){
            boolean goingUp = this.row > row;
            boolean goingLeft = this.col > col;

            int colMod = goingLeft?-1:1;
            int rowMod = goingUp?-1:1;
            Vector2 vec2ModOff = new Vector2(0, 0);
            List<Vector2> coords = new ArrayList<Vector2>();
            do{
                coords.add(new Vector2(this.col+vec2ModOff.x,this.row+vec2ModOff.y));
                vec2ModOff.x += colMod;
                vec2ModOff.y += rowMod;
            } while(coords.get(coords.size()-1).x != col && coords.get(coords.size()-1).y != row);

            int pieceToRemove = -1;
            for(int i = 1; i < coords.size(); i++){
                Vector2 coord = coords.get(i);
                for(int x = 0; x < GameScreen.getPieces().size(); x++){
                    Piece p = GameScreen.getPieces().get(x);
                    if (p.getCol() == coord.x && p.getRow() == coord.y) {
                        if(i == coords.size()-1){
                            if(!p.getColor().equals(getColor())) {
                                pieceToRemove = x;
                            } else
                                isAPeiceInThePath = true;
                        } else
                            isAPeiceInThePath = true;
                    }
                }
            }
            if(pieceToRemove != -1)
                GameScreen.getPieces().remove(pieceToRemove);
            pieceToRemove = -1;

        }

        return isDiagonal && !isAPeiceInThePath;
    }

    public boolean canMove(int row, int col){
        if(type.equals("BISHOP")) {
            return canBishopMove(row, col);
        } else if (type.equals("PAWN")){
            return true;
        }
        return false;
    }

    public void movePiece(int row, int col){
        if(canMove(row,col)) {
            this.row = row;
            this.col = col;
        }
        centerOnGrid();
    }

    public void centerOnGrid(){
        setX(colToX(col));
        setY(rowToY(row));
    }

    public Texture getImg() {
        return img;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCol(int col){
        this.col = col;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int rowToY(int row){
        return ChessGame.HEIGHT - ((row+1)*90);
    }

    public int colToX(int col){
        return col*90;
    }
}
