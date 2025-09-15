package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves implements MoveCollection{

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        var set = new HashSet<ChessMove>();
        var x = myPosition.row()-1;
        var y = myPosition.col()-1;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x--;
            y--;
        }
        x = myPosition.row()-1;
        y = myPosition.col()+1;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x--;
            y++;
        }
        x = myPosition.row()+1;
        y = myPosition.col()-1;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x++;
            y--;
        }
        x = myPosition.row()+1;
        y = myPosition.col()+1;
        while (x >= 1 && y >= 1 && x <= 8 && y <= 8){
            set.add(new ChessMove(myPosition, new ChessPosition(x,y), null));
            if (board.getBoard()[x-1][y-1]!= null){
                break;
            }
            x++;
            y++;
        }
        return set;
    }
}
