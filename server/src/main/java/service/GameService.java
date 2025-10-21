package service;

import dataaccess.GameDataAccess;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    private final GameDataAccess gameDataAccess;

    public GameService(GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public void clearGameDatabase() {
        gameDataAccess.clear();
    }

    public ArrayList<GameData> listGames() {
        return gameDataAccess.listGames();
    }

    public GameData createGame(String gameName) {
        return gameDataAccess.createGame(gameName);
    }
}
