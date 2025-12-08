package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.sql.Statement;
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
        ArrayList<GameData> games = new ArrayList<>();
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "SELECT * FROM game;")) {
                var rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    var gameID = rs.getInt("gameID");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    var game = rs.getString("game") != null ? new Gson().fromJson(rs.getString("game"), ChessGame.class) : null;
                    games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        GameData gameData;
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "INSERT INTO game (gameName, game) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, new Gson().toJson(new ChessGame()));
                preparedStatement.executeUpdate();
                var rs = preparedStatement.getGeneratedKeys();
                rs.next();
                gameData = new GameData(rs.getInt(1), null, null, gameName, new ChessGame());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gameData;
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String username) throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    playerColor == ChessGame.TeamColor.WHITE ? "UPDATE game SET whiteUsername = ? WHERE gameID = ?;" :
                            "UPDATE game SET blackUsername = ? WHERE gameID = ?;")) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        GameData gameData = null;
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "SELECT * FROM game WHERE gameID = ?;")) {
                preparedStatement.setInt(1, gameID);
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameName = rs.getString("gameName");
                    var game = rs.getString("game") != null ? new Gson().fromJson(rs.getString("game"), ChessGame.class) : null;
                    gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gameData;
    }

    @Override
    public void updateBoard(Integer gameID, ChessMove move) throws DataAccessException, InvalidMoveException {
        try (var conn = getConnection()) {
            var gameData = getGame(gameID);
            var newGame = gameData.game().makeMove(move);
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET game = ? WHERE gameID = ?;")) {
                preparedStatement.setString(1, new Gson().toJson(newGame, ChessGame.class));
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePlayer(Integer gameID, String username) throws DataAccessException {
        try (var conn = getConnection()) {
            var gameData = getGame(gameID);
            var whiteUsername = username.equals(gameData.whiteUsername()) ? null : gameData.whiteUsername();
            var blackUsername = username.equals(gameData.blackUsername()) ? null : gameData.blackUsername();
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername = ?, blackUsername = ? WHERE gameID = ?;")) {
                preparedStatement.setString(1, whiteUsername);
                preparedStatement.setString(2, blackUsername);
                preparedStatement.setInt(3, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
