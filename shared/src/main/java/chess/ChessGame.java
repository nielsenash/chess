package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team = TeamColor.WHITE;
    private ChessBoard chessBoard;

    public ChessGame(){
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team=team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }


    public boolean isValid(ChessMove move){
        return true; //doesn't put you in check
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */


    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        var piece = chessBoard.getPiece(startPosition);
        var set = new HashSet<ChessMove>();
        if (piece!=null){
            for (ChessMove move: piece.pieceMoves(chessBoard, startPosition)){
                if (isValid(move)){
                    set.add(move);
                }
            }
        }
        return set;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //if it doesn't put your own king in check:
        var piece = chessBoard.getPiece(move.startPosition);
        if (piece!=null){
            chessBoard.removePiece(move.startPosition);
            chessBoard.addPiece(move.endPosition, piece);
        }
        var newTeam = team==TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        setTeamTurn(newTeam);
    }



    public ChessPosition findKing(TeamColor teamColor){
        for (int i = 1; i<=8; i++ ){
            for (int j = 1; j <=8; j++){
                var piece = chessBoard.getPiece(new ChessPosition(i,j));
                if (piece!= null && piece.type== ChessPiece.PieceType.KING && piece.pieceColor==teamColor){
                    return new ChessPosition(i,j);
                }
            }
        }
        return new ChessPosition(0,0);
    }


    public boolean opposingPiecesAttackKing(ChessPosition kingPosition,TeamColor teamColor ){
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                var piece = chessBoard.getPiece(new ChessPosition(i,j));
                if (piece!= null && piece.pieceColor!=teamColor){
                    if (pieceAttacksKing(piece,new ChessPosition(i,j), kingPosition)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean pieceAttacksKing(ChessPiece piece, ChessPosition PiecePosition, ChessPosition kingPosition){
        for (ChessMove move: piece.pieceMoves(chessBoard,PiecePosition)){
            if (move.endPosition == kingPosition){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    public boolean isInCheck(TeamColor teamColor) {
        var pos = findKing(teamColor);
        return opposingPiecesAttackKing(pos,teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor); //&& nobody has moves
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor); //&& nobody has moves
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard=board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, chessBoard);
    }
}
