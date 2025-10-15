package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;

public class MemoryGameDataAccess implements GameDataAccess {
    private final HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public HashMap<Integer, GameData> listGames() {
        return games;
    }

    @Override
    public void createGame(String gameName) {
        var id = games.size();
        var chessGame = new ChessGame();
        var gameData = new GameData(id, null, null, gameName, chessGame);
        games.put(id, gameData);
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