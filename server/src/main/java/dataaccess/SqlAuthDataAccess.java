package dataaccess;

import exceptions.UnauthorizedException;
import model.AuthData;

import java.sql.SQLException;

import static dataaccess.DatabaseManager.getConnection;

public class SqlAuthDataAccess implements AuthDataAccess {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "TRUNCATE auth")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData saveAuth(AuthData authData) throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "INSERT INTO auth (authToken, username) VALUES (?, ?)")) {
                preparedStatement.setString(1, authData.authToken());
                preparedStatement.setString(2, authData.username());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authData;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        AuthData authData = null;

        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "SELECT authToken, username FROM auth WHERE authToken = ?")) {
                preparedStatement.setString(1, authToken);

                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    authData = new AuthData(
                            rs.getString("authToken"),
                            rs.getString("username")
                    );
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

        return authData;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException, UnauthorizedException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "DELETE FROM auth WHERE authToken = ?")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                throw new UnauthorizedException("Error: unauthorized");
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
