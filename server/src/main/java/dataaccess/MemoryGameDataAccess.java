package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class MemoryGameDataAccess implements GameDataAccess {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private Integer gameId = 1;

    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public ArrayList<GameData> listGames() {
        return new ArrayList<>(games.values());
    }

    @Override
    public GameData createGame(String gameName) {
        var id = gameId;
        var chessGame = new ChessGame();
        var gameData = new GameData(id, null, null, gameName, chessGame);
        games.put(id, gameData);
        gameId++;
        return gameData;
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) {
        var gameData = getGame(gameID);
        var whiteUsername = playerColor == WHITE ? username : gameData.whiteUsername();
        var blackUsername = playerColor == BLACK ? username : gameData.blackUsername();
        var newGameData = new GameData(gameData.gameID(), whiteUsername, blackUsername, gameData.gameName(), gameData.game());
        games.put(gameID, newGameData);
    }

    @Override
    public GameData getGame(Integer gameID) {
        return games.get(gameID);
    }

    @Override
    public void updateBoard(Integer gameID, ChessMove move) throws DataAccessException, InvalidMoveException {
        var oldGame = games.get(gameID);
        var newGame = oldGame.game().makeMove(move);
        var gameData = new GameData(gameID, oldGame.whiteUsername(), oldGame.blackUsername(), oldGame.gameName(), newGame);
        games.put(gameID, gameData);
    }

    @Override
    public void removePlayer(Integer gameID, String username) throws DataAccessException {
        var game = games.get(gameID);
        var whiteUsername = username.equals(game.whiteUsername()) ? null : game.whiteUsername();
        var blackUsername = username.equals(game.blackUsername()) ? null : game.blackUsername();
        var gameData = new GameData(gameID, whiteUsername, blackUsername, game.gameName(), game.game());
        games.put(gameID, gameData);
    }

    @Override
    public void setGameOver(Integer gameID) throws DataAccessException {
        var game = games.get(gameID);
        var newGame = game.game().setGameOver();
        var gameData = new GameData(gameID, game.whiteUsername(), game.blackUsername(), game.gameName(), newGame);
        games.put(gameID, gameData);
    }
}