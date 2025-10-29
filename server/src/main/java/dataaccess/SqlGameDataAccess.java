package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class SqlGameDataAccess implements GameDataAccess {
    @Override
    public void clear() {

    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public GameData createGame(String gameName) {
        return null;
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) {

    }

    @Override
    public GameData getGame(Integer gameId) {
        return null;
    }

    @Override
    public void updateGame(Integer gameId, GameData gameData) {

    }
}
