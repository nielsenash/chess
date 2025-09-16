package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves implements MoveCollection{

    HashSet<ChessMove> set = new HashSet<>();

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        calculateMoves(board,myPosition, 1,1,set, color);
        calculateMoves(board,myPosition, 1,-1,set, color);
        calculateMoves(board,myPosition, -1,1,set, color);
        calculateMoves(board,myPosition, -1,-1,set, color);

        return set;
    }
}
