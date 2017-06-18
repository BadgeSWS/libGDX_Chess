package pieces;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jacks on 2017-06-17.
 */
public class Piece {

    private Texture img;
    private String color;
    private String type;
    private int row;
    private int col;


    public Piece(String color, String type, int row, int col) {
        img = new Texture("1x1.png");
        this.color = color;
        this.type = type;
        this.row = row;
        this.col = col;
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

}
