package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class MemoryGameDataAccess implements GameDataAccess {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private final ArrayList<GameData> gamesList = new ArrayList<>();
    private Integer gameId = 1;

    @Override
    public void clear() {
        games.clear();
        gamesList.clear();
    }

    @Override
    public ArrayList<GameData> listGames() {
        return gamesList;
    }

    @Override
    public GameData createGame(String gameName) {
        var id = gameId;
        var chessGame = new ChessGame();
        var gameData = new GameData(id, null, null, gameName, chessGame);
        games.put(id, gameData);
        gamesList.add(gameData);
        gameId++;
        return gameData;
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) {
        var gameData = getGame(gameID);
        var whiteUsername = playerColor == WHITE ? username : gameData.whiteUsername();
        var blackUsername = playerColor == BLACK ? username : gameData.blackUsername();
        var newGameData = new GameData(gameData.gameID(), whiteUsername, blackUsername, gameData.gameName(), gameData.game());
        updateGame(gameID, newGameData);
    }

    @Override
    public GameData getGame(Integer gameID) {
        return games.get(gameID);
    }

    @Override
    public void updateGame(Integer gameID, GameData gameData) {
        games.put(gameID, gameData);
        for (GameData data : gamesList) {
            if ((data.gameID().equals(gameID))) {
                gamesList.remove(data);
                gamesList.add(gameData);
            }
        }

    }
}