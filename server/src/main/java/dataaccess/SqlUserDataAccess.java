package dataaccess;

import model.UserData;

import java.sql.SQLException;

import static dataaccess.DatabaseManager.getConnection;

public class SqlUserDataAccess implements UserDataAccess {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "TRUNCATE user")) {
                preparedStatement.executeUpdate();
                System.out.println("Deleted user table");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData saveUser(UserData user) throws DataAccessException {
        try (var conn = getConnection()) {
            if (getUser(user.username()) != null) {
                return null;
            }
            try (var preparedStatement = conn.prepareStatement(
                    "INSERT INTO user (username, password, email) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, user.password());
                preparedStatement.setString(3, user.email());

                System.out.println("Inserted user: " + preparedStatement.executeUpdate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public UserData getUser(String username) throws DataAccessException {
        UserData user = null;

        try (var conn = getConnection()) {
            try (var preparedStatement = conn.prepareStatement(
                    "SELECT username, password, email FROM user WHERE username = ?")) {
                preparedStatement.setString(1, username);

                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    user = new UserData(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

        return user;
    }

}
