package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import model.GameData;

import java.util.ArrayList;

public interface GameDataAccess {
    void clear() throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    GameData createGame(String gameName) throws DataAccessException;

    void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws DataAccessException;

    GameData getGame(Integer gameID) throws DataAccessException;

    void updateBoard(Integer gameID, ChessMove move) throws DataAccessException, InvalidMoveException;

    void removePlayer(Integer gameID, String username) throws DataAccessException;

    void setGameOver(Integer gameID) throws DataAccessException;
}
