package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    final private ChessPiece[][] board = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }
    public void removePiece (ChessPosition position) {
        board[position.getRow()-1][position.getColumn()-1] = null;
    }

    public void removeAllPieces() {
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                removePiece(new ChessPosition(i,j));
            }
        }
    }

    public void setPawns(){
        for (int i = 1; i< 9; i++) {
            addPiece(new ChessPosition(2, i), new ChessPiece(WHITE, PAWN));
        }
        for (int j = 1; j < 9; j++){
            addPiece(new ChessPosition(7,j), new ChessPiece(BLACK, PAWN));
        }
    }

    public void setRooks(){
        addPiece(new ChessPosition(1, 1), new ChessPiece(WHITE, ROOK));
        addPiece(new ChessPosition(1, 8), new ChessPiece(WHITE, ROOK));
        addPiece(new ChessPosition(8, 1), new ChessPiece(BLACK, ROOK));
        addPiece(new ChessPosition(8, 8), new ChessPiece(BLACK, ROOK));
    }

    public void setKnights(){
        addPiece(new ChessPosition(1, 2), new ChessPiece(WHITE, KNIGHT));
        addPiece(new ChessPosition(1, 7), new ChessPiece(WHITE, KNIGHT));
        addPiece(new ChessPosition(8, 2), new ChessPiece(BLACK, KNIGHT));
        addPiece(new ChessPosition(8, 7), new ChessPiece(BLACK, KNIGHT));
    }

    public void setBishops(){
        addPiece(new ChessPosition(1, 3), new ChessPiece(WHITE, BISHOP));
        addPiece(new ChessPosition(1, 6), new ChessPiece(WHITE, BISHOP));
        addPiece(new ChessPosition(8, 3), new ChessPiece(BLACK, BISHOP));
        addPiece(new ChessPosition(8, 6), new ChessPiece(BLACK, BISHOP));
    }

    public void setQueens(){
        addPiece(new ChessPosition(1, 4), new ChessPiece(WHITE, QUEEN));
        addPiece(new ChessPosition(8, 4), new ChessPiece(BLACK, QUEEN));
    }

    public void setKings(){
        addPiece(new ChessPosition(1, 5), new ChessPiece(WHITE, KING));
        addPiece(new ChessPosition(8, 5), new ChessPiece(BLACK, KING));
    }




    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        removeAllPieces();
        setPawns();
        setRooks();
        setKnights();
        setBishops();
        setQueens();
        setQueens();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        return Arrays.toString(board);
    }
}
