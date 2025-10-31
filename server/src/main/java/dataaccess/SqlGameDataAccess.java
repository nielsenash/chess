package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;

import static dataaccess.DatabaseManager.getConnection;

public class SqlGameDataAccess implements GameDataAccess {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "TRUNCATE game")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "INSERT INTO game (gameName, game) VALUES (?, ?)")) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, new Gson().toJson(new ChessGame()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authData;
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws DataAccessException {

    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {

        return null;
    }

    @Override
    public void updateGame(Integer gameId, GameData gameData) throws DataAccessException {

    }
}
