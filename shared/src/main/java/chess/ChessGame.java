package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static chess.ChessGame.TeamColor.WHITE;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team = WHITE;
    private ChessBoard chessBoard;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.chessBoard.resetBoard();
    }

    public ChessGame copy() {
        var newGame = new ChessGame();
        for (int i = 0; i <= 7; i++) {
            System.arraycopy(this.chessBoard.board[i], 0, newGame.chessBoard.board[i], 0, 8);
        }
        newGame.team = this.team;
        return newGame;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
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
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    //ensures a move doesn't put the king in check
    public boolean isValid(ChessMove move) {
        var pretendGame = copy();
        var color = pretendGame.chessBoard.getPiece(move.startPosition).pieceColor;
        pretendGame.chessBoard.makeMove(move);
        return !pretendGame.isInCheck(color);
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
        if (piece != null) {
            for (ChessMove move : piece.pieceMoves(chessBoard, startPosition)) {
                if (isValid(move)) {
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
    public ChessGame makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = chessBoard.getPiece(move.startPosition);
        if (piece.pieceColor != team) {
            throw new InvalidMoveException("Not your team's piece!");
        }
        if (!validMoves(move.startPosition).contains(move)) {
            throw new InvalidMoveException("Invalid Move");
        }
        chessBoard.makeMove(move);
        var newTeam = team == WHITE ? TeamColor.BLACK : WHITE;
        setTeamTurn(newTeam);
        return this;
    }

    /**
     * Loops through moves of enemy piece to see if it can attack king
     */

    public boolean pieceAttacksKing(ChessPiece piece, ChessPosition piecePosition, ChessPosition kingPosition) {
        var set = piece.pieceMoves(chessBoard, piecePosition);
        for (ChessMove move : set) {
            if (move.getEndPosition().row == kingPosition.row && move.getEndPosition().col == kingPosition.col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Loops through board, checking if any opposing pieces can move to the square where the king is
     */

    public boolean opposingPiecesAttackKing(ChessPosition kingPosition, TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                var piece = chessBoard.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.pieceColor != teamColor) {
                    if (pieceAttacksKing(piece, new ChessPosition(i, j), kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ChessPosition findKing(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                var piece = chessBoard.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.type == ChessPiece.PieceType.KING && piece.pieceColor == teamColor) {
                    return new ChessPosition(i, j);
                }
            }
        }
        return new ChessPosition(0, 0);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    public boolean isInCheck(TeamColor teamColor) {
        var pos = findKing(teamColor);
        return opposingPiecesAttackKing(pos, teamColor);
    }

    /**
     * Determines if the given team has no legal moves
     *
     * @param teamColor which team to check for hasNoMoves
     * @return True if the specified team has no legal moves
     */
    public boolean hasNoMoves(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                var piece = chessBoard.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.pieceColor == teamColor) {
                    if (!validMoves(new ChessPosition(i, j)).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (isInCheck(teamColor) && hasNoMoves(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return (!isInCheck(teamColor) && hasNoMoves(teamColor));
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
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
