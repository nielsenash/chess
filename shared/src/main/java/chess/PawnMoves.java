package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;


public class PawnMoves implements MoveCollection{
    HashSet<ChessMove> set = new HashSet<>();

    public void addMoves(ChessMove move, ChessGame.TeamColor color){
        if (move.getEndPosition().row() == 8 && color == WHITE){
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), BISHOP));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), KNIGHT));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ROOK));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), QUEEN));
        }
        else if (move.getEndPosition().row() == 1 && color == BLACK){
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), BISHOP));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), KNIGHT));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ROOK));
            set.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), QUEEN));
        }
        else{
            set.add(move);
        }

    }

    public void addMovesForStartingSquare(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        int x = myPosition.row()-1;
        int y = myPosition.col()-1;

        int change = 2;
        if (color == BLACK){
            change = -2;
        }
        if (board.getBoard()[x+change][y] == null && board.getBoard()[x+(change/2)][y] == null){
            addMoves(new ChessMove(myPosition, new ChessPosition(x+change+1,y+1), null),color);
        }
    }

    public void addMoveForward(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        int x = myPosition.row()-1;
        int y = myPosition.col()-1;

        int change = 1;
        if (color == BLACK){
            change = -1;
        }
        if (x >= 1 && x <= 6 && board.getBoard()[x+change][y] == null){
            addMoves(new ChessMove(myPosition, new ChessPosition(x+change+1,y+1), null), color);
        }
    }

    public void addDiagonalMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        int x = myPosition.row();
        int y = myPosition.col();

        if (y>0){
            if (color == WHITE && board.getBoard()[x][y-2]!= null && board.getPiece(new ChessPosition(x+1, y-1)).pieceColor()==BLACK){
                addMoves(new ChessMove(myPosition, new ChessPosition(x+1,y-1), null),color);
            }
            if (color == BLACK && board.getBoard()[x-2][y-2]!= null && board.getPiece(new ChessPosition(x-1, y-1)).pieceColor()==WHITE){
                addMoves(new ChessMove(myPosition, new ChessPosition(x-1,y-1), null),color);
            }
        if (y<7){
            if (color == WHITE && board.getBoard()[x][y]!= null && board.getPiece(new ChessPosition(x+1, y+1)).pieceColor()==BLACK){
                addMoves(new ChessMove(myPosition, new ChessPosition(x+1,y+1), null),color);
            }
            if (color == BLACK && board.getBoard()[x-2][y]!= null && board.getPiece(new ChessPosition(x-1, y+1)).pieceColor()==WHITE){
                addMoves(new ChessMove(myPosition, new ChessPosition(x-1,y+1), null),color);
            }
        }
        }
    }

    @Override
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        addMoveForward(board,myPosition, color);
        if ((myPosition.row() == 2 && color == WHITE) || (myPosition.row() == 7 && color == BLACK)){
            addMovesForStartingSquare(board, myPosition, color);
        }
        addDiagonalMoves(board,myPosition,color);

        return set;
    }
}
