package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDataAccess {
    void clear();

    ArrayList<GameData> listGames();

    GameData createGame(String gameName);

    void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username);

    GameData getGame(Integer gameId);

    void updateGame(Integer gameId, GameData gameData);
}
