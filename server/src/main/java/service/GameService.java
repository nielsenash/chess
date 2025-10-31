package service;

import chess.ChessGame;
import dataaccess.GameDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import model.GameData;

import java.util.Collection;


public class GameService {
    private final GameDataAccess gameDataAccess;

    public GameService(GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public void clearGameDatabase() throws Exception {
        gameDataAccess.clear();
    }

    public Collection<GameData> listGames() throws Exception {
        return gameDataAccess.listGames();
    }

    public GameData createGame(String gameName) throws Exception {
        var gameData = gameDataAccess.createGame(gameName);
        if (gameData.gameName() == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        return gameData;
    }

    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws Exception {
        if (playerColor == null || gameID == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        //user tries to join with a color that's already taken
        if (gameDataAccess.getGame(gameID).whiteUsername() != null && playerColor == ChessGame.TeamColor.WHITE) {
            throw new AlreadyTakenException("Error: already taken");
        } else if (gameDataAccess.getGame(gameID).blackUsername() != null && playerColor == ChessGame.TeamColor.BLACK) {
            throw new AlreadyTakenException("Error: already taken");
        }
        gameDataAccess.joinGame(playerColor, gameID, username);
    }

}
