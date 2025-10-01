package chess;

import java.util.HashSet;

public class BishopMoves implements RepeatedMoves{

    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        var set = new HashSet<ChessMove>();
        calculateMoves(board, pos, color, 1,1, set);
        calculateMoves(board, pos, color, 1,-1, set);
        calculateMoves(board, pos, color, -1,1, set);
        calculateMoves(board, pos, color, -1,-1, set);
        return set;
    }
}
