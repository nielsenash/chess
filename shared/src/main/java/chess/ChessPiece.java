package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessPiece.PieceType.BISHOP;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public record ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type)  {

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public HashSet<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition){
        var set = new HashSet<ChessMove>();
        var x = myPosition.row();
        var y = myPosition.col();
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x-1,y-1), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x--;
            y--;
        }
        x = myPosition.row();
        y = myPosition.col();
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x-1,y+1), null));
            if (board.getBoard()[x-1][y+1]!= null){
                break;
            }
            x--;
            y++;
        }
        x = myPosition.row();
        y = myPosition.col();
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x+1,y-1), null));
            if (board.getBoard()[x+1][y-1]!= null){
                break;
            }
            x++;
            y--;
        }
        x = myPosition.row();
        y = myPosition.col();
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x+1,y+1), null));
            if (board.getBoard()[x+1][y+1]!= null){
                break;
            }
            x++;
            y++;
        }
        return set;
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch(type) {
                    case BISHOP -> getBishopMoves(board, myPosition);
                    case KNIGHT -> null;
                    case ROOK -> null;
                    case QUEEN -> null;
                    case KING -> null;
                    case PAWN -> null;
                };
    }
}
