package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves implements MoveCollection{

    HashSet<ChessMove> set = new HashSet<>();

    public void calculateMoves(ChessBoard board, ChessPosition myPosition, int xChange, int yChange){
        var x = myPosition.row()+xChange;
        var y = myPosition.col()+yChange;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x+=xChange;
            y+=yChange;
        }
    }

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {

        calculateMoves(board,myPosition, 1,1);
        calculateMoves(board,myPosition, 1,-1);
        calculateMoves(board,myPosition, -1,1);
        calculateMoves(board,myPosition, -1,-1);

        return set;
    }
}
