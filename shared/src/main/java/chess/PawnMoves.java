package chess;

import java.util.HashSet;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;

public class PawnMoves {
    HashSet<ChessMove> set = new HashSet<>();

    boolean isValid(ChessPosition pos){
        return pos.getRow()>=1 && pos.getRow()<=8 && pos.getColumn()>=1 && pos.getColumn()<=8;
    }

    void addMoves(ChessPosition start, ChessPosition end, ChessGame.TeamColor color){
        if ((color == WHITE && end.getRow() == 8) || (color == BLACK && end.getRow() == 1)){
            set.add(new ChessMove(start,end,KNIGHT));
            set.add(new ChessMove(start,end,BISHOP));
            set.add(new ChessMove(start,end,ROOK));
            set.add(new ChessMove(start,end,QUEEN));
        }
        else{
            set.add(new ChessMove(start,end,null));
        }
    }

    void addForward(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        int change = 1;
        if (color == BLACK){
            change = -1;
        }
        var newPos = new ChessPosition(pos.row+change, pos.col);
        if (isValid(newPos) && board.getPiece(newPos) == null){
            addMoves(pos, new ChessPosition(pos.row+change, pos.col),color);
        }
    }

    void add2Forward(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        int change = 1;
        if (color == BLACK){
            change = -1;
        }
        var newPos1 = new ChessPosition(pos.row+change, pos.col);
        var newPos2 = new ChessPosition(pos.row+change*2, pos.col);
        if (board.getPiece(newPos1) == null && board.getPiece(newPos2) ==null){
            addMoves(pos, new ChessPosition(pos.row+change*2, pos.col),color);
        }
    }

    void addCaptureMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        int change = 1;
        if (color == BLACK){
            change = -1;
        }
        var leftAttack = new ChessPosition(pos.row+change, pos.col-1);
        var rightAttack = new ChessPosition(pos.row+change, pos.col+1);

        if (isValid(leftAttack) && board.getPiece(leftAttack)!=null && board.getPiece(leftAttack).pieceColor!=color){
            addMoves(pos,leftAttack,color);
        }
        if (isValid(rightAttack) && board.getPiece(rightAttack)!=null && board.getPiece(rightAttack).pieceColor!=color){
            addMoves(pos,rightAttack,color);
        }
    }

    HashSet<ChessMove> returnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color){
        addForward(board,pos,color);
        if ((color == WHITE && pos.getRow()==2) ||(color == BLACK && pos.getRow()==7)){
            add2Forward(board,pos,color);
        }
        addCaptureMoves(board,pos,color);
        return set;
    }
}
