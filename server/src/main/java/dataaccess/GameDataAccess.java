package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;

public interface GameDataAccess {
    void clear();

    HashMap<Integer, GameData> listGames();

    void createGame(String gameName);

    void joinGame(ChessGame.TeamColor playerColor, String gameName);

    GameData getGame(Integer gameId);

    void updateGame(Integer gameId, GameData gameData);
}
