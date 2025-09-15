package chess;

import java.util.Collection;

public interface MoveCollection {
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition);
}

