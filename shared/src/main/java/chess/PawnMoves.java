package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;


public class PawnMoves implements MoveCollection{
    HashSet<ChessMove> set = new HashSet<>();

    public void addMovesForStartingSquare(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        int x = myPosition.row()-1;
        int y = myPosition.col()-1;

        int change = 2;
        if (color == BLACK){
            change = -2;
        }
        if (board.getBoard()[x+change][y] == null){
            set.add(new ChessMove(myPosition, new ChessPosition(x+change+1,y+1), null));
        }
    }

    public void addMoveForward(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        int x = myPosition.row()-1;
        int y = myPosition.col()-1;

        int change = 1;
        if (color == BLACK){
            change = -1;
        }
        if (x >= 1 && x <= 6 && board.getBoard()[x+change][y] == null){
            set.add(new ChessMove(myPosition, new ChessPosition(x+change+1,y+1), null));
        }
    }

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        addMoveForward(board,myPosition, color);
        if ((myPosition.row() == 2 && color == WHITE) || (myPosition.row() == 7 && color == BLACK)){
            addMovesForStartingSquare(board, myPosition, color);
        }
        return set;
    }
}
