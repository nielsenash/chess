package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.PAWN;

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

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        removeAllPieces();
        setPawns();
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
