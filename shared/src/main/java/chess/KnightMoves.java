package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoves implements MoveCollection{
    HashSet<ChessMove> set = new HashSet<>();

    public void calculateMoves(ChessBoard board, ChessPosition myPosition, int xChange, int yChange,HashSet<ChessMove> set,ChessGame.TeamColor color ){
        var x = myPosition.row()+xChange;
        var y = myPosition.col()+yChange;
        if (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            if (board.getBoard()[x-1][y-1] == null || board.getPiece(new ChessPosition(x,y)).pieceColor() != color){
                set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            }
        }
    }

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        calculateMoves(board,myPosition, -1,2,set, color);
        calculateMoves(board,myPosition, -1,-2,set, color);
        calculateMoves(board,myPosition, 1,2,set, color);
        calculateMoves(board,myPosition, 1,-2,set, color );
        calculateMoves(board,myPosition, -2,1,set, color);
        calculateMoves(board,myPosition, -2,-1,set, color);
        calculateMoves(board,myPosition, 2,1,set, color);
        calculateMoves(board,myPosition, 2,-1,set, color);
        return set;
    }
}
