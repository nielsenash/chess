package service;

import dataaccess.GameDataAccess;

public class GameService {
    private final GameDataAccess gameDataAccess;

    public GameService(GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }
}
