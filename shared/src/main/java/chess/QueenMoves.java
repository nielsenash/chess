package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenMoves implements MoveCollection{
    HashSet<ChessMove> set = new HashSet<>();

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        calculateMoves(board,myPosition, 1,0,set);
        calculateMoves(board,myPosition, -1,0,set);
        calculateMoves(board,myPosition, 0,1,set);
        calculateMoves(board,myPosition, 0,-1,set );
        calculateMoves(board,myPosition, 1,1,set);
        calculateMoves(board,myPosition, 1,-1,set);
        calculateMoves(board,myPosition, -1,1,set);
        calculateMoves(board,myPosition, -1,-1,set );

        return set;
    }
}