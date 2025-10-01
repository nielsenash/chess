package chess;

import java.util.HashSet;

public class KingMoves implements SingleMoves{
    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        var set = new HashSet<ChessMove>();
        calculateMoves(board, pos, color, 1,1, set);
        calculateMoves(board, pos, color, 1,-1, set);
        calculateMoves(board, pos, color, -1,1, set);
        calculateMoves(board, pos, color, -1,-1, set);
        calculateMoves(board, pos, color, 1,0, set);
        calculateMoves(board, pos, color, -1,0, set);
        calculateMoves(board, pos, color, 0,1, set);
        calculateMoves(board, pos, color, 0,-1, set);
        return set;
    }
}
