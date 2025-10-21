package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameDataAccess implements GameDataAccess {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private final ArrayList<GameData> gamesList = new ArrayList<>();
    private int gameId = 1;

    @Override
    public void clear() {
        games.clear();
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
    public void joinGame(ChessGame.TeamColor playerColor, String gameName) {
        ;
    }

    @Override
    public GameData getGame(Integer gameId) {
        return games.get(gameId);
    }

    @Override
    public void updateGame(Integer gameId, GameData gameData) {
        games.put(gameId, gameData);
    }
}