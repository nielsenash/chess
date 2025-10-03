package chess;

import java.util.HashSet;

public interface SingleMoves {
    default void calculateMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color,
                                int xChange, int yChange, HashSet<ChessMove> set){

        int x = pos.getRow() + xChange;
        int y = pos.getColumn() + yChange;
        if(x >= 1 && x <= 8 && y >= 1 && y <= 8) {
            var newPos = new ChessPosition(x, y);
            if (board.getPiece(newPos) == null || board.getPiece(newPos).pieceColor != color) {
                set.add(new ChessMove(pos, newPos, null));
            }
        }
    }
}
