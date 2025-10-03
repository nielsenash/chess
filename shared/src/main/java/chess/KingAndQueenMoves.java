package chess;

import java.util.HashSet;

public class KingAndQueenMoves implements MoveGenerator{
    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color, boolean repeats){
        var set = new HashSet<ChessMove>();
        calculateMoves(board, pos, color, 1,1, set, repeats);
        calculateMoves(board, pos, color, 1,-1, set, repeats);
        calculateMoves(board, pos, color, -1,1, set, repeats);
        calculateMoves(board, pos, color, -1,-1, set, repeats);
        calculateMoves(board, pos, color, 1,0, set, repeats);
        calculateMoves(board, pos, color, -1,0, set, repeats);
        calculateMoves(board, pos, color, 0,1, set, repeats);
        calculateMoves(board, pos, color, 0,-1, set, repeats);
        return set;
    }
}