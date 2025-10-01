package chess;

import java.util.HashSet;

public interface RepeatedMoves {

    default void calculateMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color,
                                int x_change, int y_change, HashSet<ChessMove> set){

        int x = pos.getRow() + x_change;
        int y = pos.getColumn() + y_change;
        while(x >= 1 && x <= 8 && y >= 1 && y <= 8){
            var new_pos = new ChessPosition(x,y);
            if (board.getPiece(new_pos) != null && board.getPiece(new_pos).pieceColor==color){
                break;
            }
            if (board.getPiece(new_pos) == null || board.getPiece(new_pos).pieceColor!=color){
                set.add(new ChessMove(pos,new_pos, null));
                if (board.getPiece(new_pos) != null && board.getPiece(new_pos).pieceColor!=color){
                    break;
                }
            }
            x+=x_change;
            y+=y_change;
        }
    }
}

