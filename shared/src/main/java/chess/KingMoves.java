package chess;

import java.util.Collection;
import java.util.List;

public class KingMoves implements MoveCollection{
    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}
