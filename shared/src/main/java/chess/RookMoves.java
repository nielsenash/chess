package chess;

import java.util.HashSet;

public class RookMoves implements MoveGenerator {
    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        var set = new HashSet<ChessMove>();
        calculateMoves(board, pos, color, 1,0, set, true);
        calculateMoves(board, pos, color, -1,0, set, true);
        calculateMoves(board, pos, color, 0,1, set, true);
        calculateMoves(board, pos, color, 0,-1, set, true);
        return set;
    }
}
