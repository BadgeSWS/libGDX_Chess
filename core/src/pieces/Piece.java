package pieces;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ChessGame;
import javafx.scene.paint.Color;
import screens.GameScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacks on 2017-06-17.
 */
public class Piece {

    private ChessGame game;

    private Texture img;
    private String color;
    private String type;
    private int row;
    private int col;
    private int x;
    private int y;
    private int numberOfMoves;
    public static boolean hasToWait;

    private static boolean isWhiteTurn = true;


    public Piece(ChessGame game, String color, String type, int row, int col) {
        this.game = game;
        img = new Texture("1x1.png");
        this.color = color;
        this.type = type;
        this.row = row;
        this.col = col;
        this.x = colToX(col);
        this.y = rowToY(row);
        this.numberOfMoves = 0;
        this.hasToWait = false;
        if(type.equals("KING"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackKing.png");
            else
                img = new Texture("pieces/whiteKing.png");
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

    private boolean canBishopMove(int row, int col){
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
            if(pieceToRemove != -1 &&(isDiagonal && !isAPeiceInThePath))
                GameScreen.getPieces().remove(pieceToRemove);
            pieceToRemove = -1;

        }

        return isDiagonal && !isAPeiceInThePath;
    }

    private boolean canRookMove(int row, int col){
        boolean isStraightLine = this.row == row || this.col == col;
        boolean isAPeiceInThePath = false;

        if(isStraightLine) {
            boolean isMovingCol = this.row == row;

            int colMod = 0;
            int rowMod = 0;

            if(isMovingCol)
                colMod = this.col<col?1:-1;
            else
                rowMod = this.row<row?1:-1;

            Vector2 vec2ModOff = new Vector2(0, 0);
            List<Vector2> coords = new ArrayList<Vector2>();
            do{
                coords.add(new Vector2(this.col+vec2ModOff.x,this.row+vec2ModOff.y));
                vec2ModOff.x += colMod;
                vec2ModOff.y += rowMod;
            } while(coords.get(coords.size()-1).x != col || coords.get(coords.size()-1).y != row);

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

            if(pieceToRemove != -1 && (isStraightLine && !isAPeiceInThePath))
                GameScreen.getPieces().remove(pieceToRemove);
            pieceToRemove = -1;

        }

        return isStraightLine && !isAPeiceInThePath;
    }

    private boolean canQueenMove(int row, int col){
        return canBishopMove(row, col)
                || canRookMove(row, col);
    }

    private boolean canKnightMove(int row, int col){
        int rowDist = Math.abs(this.row-row)+1;
        int colDist = Math.abs(this.col-col)+1;

        boolean isCorrectMovement = (rowDist == 2 && colDist == 3) || (rowDist == 3 && colDist == 2);
        boolean onOwnPiece = false;

        int pieceToRemove = -1;
        if(isCorrectMovement){
            for(int x = 0; x < GameScreen.getPieces().size(); x++) {
                Piece p = GameScreen.getPieces().get(x);
                if(p.getRow() == row && p.getCol() == col){
                    if(p.getColor().equals(getColor()))
                        onOwnPiece = true;
                    pieceToRemove = x;
                }
            }
        }

        if(pieceToRemove != -1 && (isCorrectMovement && !onOwnPiece))
            GameScreen.getPieces().remove(pieceToRemove);
        pieceToRemove = -1;

        return isCorrectMovement && !onOwnPiece;
    }

    private boolean canKingMove(int row, int col){
        int rowDist = Math.abs(this.row - row);
        int colDist = Math.abs(this.col - col);

        boolean validSpot = rowDist <= 1 && colDist <= 1;

        boolean isOwnPiece = false;

        int pieceToRemove = -1;
        if(validSpot){
            for(int x = 0; x < GameScreen.getPieces().size(); x++) {
                Piece p = GameScreen.getPieces().get(x);
                if(p.getRow() == row && p.getCol() == col){
                    if(p.getColor().equals(getColor()))
                        isOwnPiece = true;
                    pieceToRemove = x;
                }
            }
        }

        if(pieceToRemove != -1 && (validSpot && !isOwnPiece))
            GameScreen.getPieces().remove(pieceToRemove);
        pieceToRemove = -1;


        if(getColor().equals("WHITE") && this.col == 4 && this.row == 7 && col == 6 && row == 7 && numberOfMoves == 0){
            Vector2 spotToCheck1 = new Vector2(5,7);
            Vector2 spotToCheck2 = new Vector2(6,7);
            for(Piece p : GameScreen.getPieces()){
                if((p.getRow() == spotToCheck1.y && p.getCol() == spotToCheck1.x) || (p.getRow() == spotToCheck2.y && p.getCol() == spotToCheck2.x)){
                    return false;
                }
            }
            for(Piece p : GameScreen.getPieces()){
                if(p.getRow() == 7 && p.getCol() == 7 && p.getType().equals("ROOK") && p.numberOfMoves == 0){
                    p.setCol(5);
                    p.setRow(7);
                    p.centerOnGrid();
                    return true;
                }
            }
            return false;
        } else if (getColor().equals("WHITE") && this.col == 4 && this.row == 7 && col == 2 && row == 7 && numberOfMoves == 0){
            Vector2 spotToCheck1 = new Vector2(3,7);
            Vector2 spotToCheck2 = new Vector2(2,7);
            for(Piece p : GameScreen.getPieces()){
                if((p.getRow() == spotToCheck1.y && p.getCol() == spotToCheck1.x) || (p.getRow() == spotToCheck2.y && p.getCol() == spotToCheck2.x)){
                    return false;
                }
            }
            for(Piece p : GameScreen.getPieces()){
                if(p.getRow() == 7 && p.getCol() == 0 && p.getType().equals("ROOK") && p.numberOfMoves == 0){
                    p.setCol(3);
                    p.setRow(7);
                    p.centerOnGrid();
                    return true;
                }
            }
        } else if(getColor().equals("BLACK") && this.col == 4 && this.row == 0 && col == 6 && row == 0 && numberOfMoves == 0){
            Vector2 spotToCheck1 = new Vector2(5,0);
            Vector2 spotToCheck2 = new Vector2(6,0);
            for(Piece p : GameScreen.getPieces()){
                if((p.getRow() == spotToCheck1.y && p.getCol() == spotToCheck1.x) || (p.getRow() == spotToCheck2.y && p.getCol() == spotToCheck2.x)){
                    return false;
                }
            }
            for(Piece p : GameScreen.getPieces()){
                if(p.getRow() == 0 && p.getCol() == 7 && p.getType().equals("ROOK") && p.numberOfMoves == 0){
                    p.setCol(5);
                    p.setRow(0);
                    p.centerOnGrid();
                    return true;
                }
            }
            return false;
        } else if (getColor().equals("BLACK") && this.col == 4 && this.row == 0 && col == 2 && row == 0 && numberOfMoves == 0){
            Vector2 spotToCheck1 = new Vector2(3,0);
            Vector2 spotToCheck2 = new Vector2(2,0);
            for(Piece p : GameScreen.getPieces()){
                if((p.getRow() == spotToCheck1.y && p.getCol() == spotToCheck1.x) || (p.getRow() == spotToCheck2.y && p.getCol() == spotToCheck2.x)){
                    return false;
                }
            }
            for(Piece p : GameScreen.getPieces()){
                if(p.getRow() == 0 && p.getCol() == 0 && p.getType().equals("ROOK") && p.numberOfMoves == 0){
                    p.setCol(3);
                    p.setRow(0);
                    p.centerOnGrid();
                    return true;
                }
            }
        }

        return validSpot && !isOwnPiece;
    }

    private boolean canPawnMove(int row, int col){
        int rowMove = Math.abs(row - this.row);
        int colMove = Math.abs(col - this.col);

        if(getColor().equals("WHITE")){
            if(this.row-row < 0)
                return false;
        } else {
            if(this.row-row > 0)
                return false;
        }

        if(colMove > 1 || (colMove == 1 && rowMove != 1))
            return false;

        if(rowMove == 2 && numberOfMoves != 0)
            return false;

        if(rowMove > 2)
            return false;

        boolean isAPeiceInThePath = false;

        if(colMove == 0) {
            List<Vector2> coords = new ArrayList<Vector2>();
            if(getColor().equals("BLACK")) {
                for (int i = 1; i < rowMove + 1; i++) {
                    coords.add(new Vector2(col, this.row + i));
                }
            } else {
                for (int i = 1; i < rowMove + 1; i++) {
                    coords.add(new Vector2(col, this.row - i));
                }
            }

            for(Vector2 coord : coords){
                for(Piece p : GameScreen.getPieces()){
                    if(coord.x == p.getCol() && coord.y == p.getRow()){
                        isAPeiceInThePath = true;
                    }
                }
            }
        }

        if(colMove == 1 && rowMove == 1){
            for(int i = 0; i < GameScreen.getPieces().size(); i++){
                Piece p = GameScreen.getPieces().get(i);
                if(p.getCol() == col && p.getRow() == row && !p.getColor().equals(getColor())) {
                    GameScreen.getPieces().remove(i);
                    return true;
                }
            }
        }

        if(colMove == 1)
            return false;

        return !isAPeiceInThePath;
    }

    private boolean canMove(int row, int col){

        if(getColor().equals("WHITE") && !isIsWhiteTurn())
            return false;

        if(getColor().equals("BLACK") && isIsWhiteTurn())
            return false;

        if(row < 0 || row > 7 || col < 0 || col > 7)
            return false;

        if(this.row == row && this.col == col)
            return false;

        if(hasToWait)
            return false;

        if(type.equals("BISHOP")) {
            return canBishopMove(row, col);
        } else if (type.equals("ROOK")){
            return canRookMove(row, col);
        } else if (type.equals("QUEEN")){
            return canQueenMove(row, col);
        } else if (type.equals("KNIGHT")){
            return canKnightMove(row, col);
        } else if (type.equals("KING")){
            return canKingMove(row, col);
        } else if (type.equals("PAWN")){
            return canPawnMove(row, col);
        }
        return false;
    }

    public void movePiece(int row, int col){
        if(canMove(row,col)) {
            this.row = row;
            this.col = col;
            this.numberOfMoves++;
            setIsWhiteTurn(!isIsWhiteTurn());
        }
        centerOnGrid();
    }

    public void centerOnGrid(){
        setX(colToX(col));
        setY(rowToY(row));
    }

    public void drawPicker(){
        if(getType().equals("PAWN")) {
            if(getColor().equals("BLACK") && getRow() == 7 || getColor().equals("WHITE") && getRow() == 0) {
                game.getBatch().begin();

                Vector2 queen = new Vector2((ChessGame.WIDTH - 90 * 4) / 2 + 90 * 0, (ChessGame.HEIGHT - 90) / 2);
                Vector2 rook = new Vector2((ChessGame.WIDTH - 90 * 4) / 2 + 90 * 1, (ChessGame.HEIGHT - 90) / 2);
                Vector2 bishop = new Vector2((ChessGame.WIDTH - 90 * 4) / 2 + 90 * 2, (ChessGame.HEIGHT - 90) / 2);
                Vector2 knight = new Vector2((ChessGame.WIDTH - 90 * 4) / 2 + 90 * 3, (ChessGame.HEIGHT - 90) / 2);

                if (getColor().equals("BLACK") && getRow() == 7) {
                    hasToWait = true;
                    game.getBatch().draw(new Texture("board.png"), (90) / 2, (ChessGame.HEIGHT - (int) (90 * 1.5)) / 2, 720 - 90, (int) (90 * 1.5));
                    game.getBatch().draw(new Texture("pieces/blackQueen.png"), queen.x, queen.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/blackRook.png"), rook.x, rook.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/blackBishop.png"), bishop.x, bishop.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/blackKnight.png"), knight.x, knight.y, 90, 90);
                } else if (getColor().equals("WHITE") && getRow() == 0) {
                    hasToWait = true;
                    game.getBatch().draw(new Texture("board.png"), (90) / 2, (ChessGame.HEIGHT - (int) (90 * 1.5)) / 2, 720 - 90, (int) (90 * 1.5));
                    game.getBatch().draw(new Texture("pieces/whiteQueen.png"), queen.x, queen.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/whiteRook.png"), rook.x, rook.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/whiteBishop.png"), bishop.x, bishop.y, 90, 90);
                    game.getBatch().draw(new Texture("pieces/whiteKnight.png"), knight.x, knight.y, 90, 90);
                }
                int mouseX = Gdx.input.getX();
                int mouseY = ChessGame.HEIGHT - Gdx.input.getY();
                if (mouseX > queen.x && mouseX < queen.x + 90 && mouseY > queen.y && mouseY  < queen.y + 90){
                    game.getBatch().draw(new Texture("pieces/selectedQueen.png"), queen.x, queen.y, 90, 90);
                    if(Gdx.input.isTouched()) {
                        setType("QUEEN");
                        hasToWait = false;
                    }
                }
                if (mouseX > rook.x && mouseX < rook.x + 90 && mouseY > rook.y && mouseY  < rook.y + 90){
                    game.getBatch().draw(new Texture("pieces/selectedRook.png"), rook.x, rook.y, 90, 90);
                    if(Gdx.input.isTouched()){
                        setType("ROOK");
                        hasToWait = false;
                    }
                }
                if (mouseX > bishop.x && mouseX < bishop.x + 90 && mouseY > bishop.y && mouseY  < bishop.y + 90){
                    game.getBatch().draw(new Texture("pieces/selectedBishop.png"), bishop.x, bishop.y, 90, 90);
                    if(Gdx.input.isTouched()){
                        setType("BISHOP");
                        hasToWait = false;
                    }
                }
                if (mouseX > knight.x && mouseX < knight.x + 90 && mouseY > knight.y && mouseY  < knight.y + 90){
                    game.getBatch().draw(new Texture("pieces/selectedKnight.png"), knight.x, knight.y, 90, 90);
                    if(Gdx.input.isTouched()){
                        setType("KNIGHT");
                        hasToWait = false;
                    }
                }

                game.getBatch().end();
            }
        }
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

    public void setType(String type){
        this.type = type;
        if(type.equals("KING"))
            if(getColor().equals("BLACK"))
                img = new Texture("pieces/blackKing.png");
            else
                img = new Texture("pieces/whiteKing.png");
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

    public static boolean isIsWhiteTurn() {
        return isWhiteTurn;
    }

    public static void setIsWhiteTurn(boolean isWhiteTurn) {
        Piece.isWhiteTurn = isWhiteTurn;
    }
}
