package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoves implements MoveCollection{
    HashSet<ChessMove> set = new HashSet<>();


    public void calculateMoves(ChessBoard board, ChessPosition myPosition, int xChange, int yChange,HashSet<ChessMove> set ){
        var x = myPosition.row()+xChange;
        var y = myPosition.col()+yChange;
        if (board.getBoard()[x-1][y-1] == null){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
        }
            //logic for not capturing your own pieces

    }

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        calculateMoves(board,myPosition, -1,0,set);
        calculateMoves(board,myPosition, -1,1,set);
        calculateMoves(board,myPosition, 0,1,set);
        calculateMoves(board,myPosition, 1,1,set );
        calculateMoves(board,myPosition, 1,0,set);
        calculateMoves(board,myPosition, 1,-1,set);
        calculateMoves(board,myPosition, 0,-1,set);
        calculateMoves(board,myPosition, -1,-1,set );

        return set;
    }
}
