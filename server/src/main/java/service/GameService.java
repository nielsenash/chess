package service;

import chess.ChessGame;
import dataaccess.GameDataAccess;
import exceptions.BadRequestException;
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

    public GameData createGame(String gameName) throws Exception {
        var gameData = gameDataAccess.createGame(gameName);
        if (gameData.gameName() == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        return gameData;
    }

    public void joinGame(ChessGame.TeamColor playerColor, int gameID, String username) throws Exception {
        gameDataAccess.joinGame(playerColor, gameID, username);
    }

}
