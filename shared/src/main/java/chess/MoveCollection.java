package chess;

import java.util.Collection;
import java.util.HashSet;

public interface MoveCollection {
    Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition);

    default void calculateMoves(ChessBoard board, ChessPosition myPosition, int xChange, int yChange,HashSet<ChessMove> set ){
        var x = myPosition.row()+xChange;
        var y = myPosition.col()+yChange;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            //logic for not capturing your own pieces

            x+=xChange;
            y+=yChange;
        }
    }
}

