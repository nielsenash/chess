package chess;

import java.util.HashSet;

public class KnightMoves implements MoveGenerator{
    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        var set = new HashSet<ChessMove>();
        calculateMoves(board, pos, color, 2,1, set, false);
        calculateMoves(board, pos, color, 2,-1, set, false);
        calculateMoves(board, pos, color, -2,1, set, false);
        calculateMoves(board, pos, color, -2,-1, set, false);
        calculateMoves(board, pos, color, 1,2, set, false);
        calculateMoves(board, pos, color, 1,-2, set, false);
        calculateMoves(board, pos, color, -1,2, set, false);
        calculateMoves(board, pos, color, -1,-2, set, false);
        return set;
    }
}
