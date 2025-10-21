package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameDataAccess {
    void clear();

    ArrayList<GameData> listGames();

    GameData createGame(String gameName);

    void joinGame(ChessGame.TeamColor playerColor, String gameName);

    GameData getGame(Integer gameId);

    void updateGame(Integer gameId, GameData gameData);
}
