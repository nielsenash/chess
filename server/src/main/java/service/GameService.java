package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.GameDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    private final GameDataAccess gameDataAccess;

    public GameService(GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public void clearGameDatabase() throws Exception {
        gameDataAccess.clear();
    }

    public ArrayList<GameData> listGames() throws Exception {
        return gameDataAccess.listGames();
    }

    public GameData createGame(String gameName) throws Exception {
        if (gameName == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        return gameDataAccess.createGame(gameName);
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

    public void updateBoard(Integer gameID, ChessMove move) throws Exception {
        try {
            gameDataAccess.updateBoard(gameID, move);
        } catch (InvalidMoveException e) {
            throw new InvalidMoveException("Error: Invalid Move");
        }
    }

    public void removePlayer(Integer gameID, String username) throws DataAccessException {
        gameDataAccess.removePlayer(gameID, username);
    }


}
