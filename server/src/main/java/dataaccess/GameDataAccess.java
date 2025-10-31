package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDataAccess {
    void clear() throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    GameData createGame(String gameName) throws DataAccessException;

    void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws DataAccessException;

    GameData getGame(Integer gameId) throws DataAccessException;

    void updateGame(Integer gameId, GameData gameData) throws DataAccessException;
}
