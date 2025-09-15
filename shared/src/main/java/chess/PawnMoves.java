package chess;

import java.util.Collection;
import java.util.List;

public class PawnMoves implements MoveCollection{

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}
