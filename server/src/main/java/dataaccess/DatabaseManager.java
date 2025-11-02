package dataaccess;


import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static String databaseName;
    private static String dbUsername;
    private static String dbPassword;
    private static String connectionUrl;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        loadPropertiesFromResources();
    }

    public static void configureDatabase() throws Exception {
        createDatabase();
        for (String statement : CREATE_TABLES) {
            try (var conn = DatabaseManager.getConnection()) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    //not really sure where this goes, but I might as well create it somewhere
    private static final String[] CREATE_TABLES = {
            """
        CREATE TABLE IF NOT EXISTS user (
          `username` varchar(128) NOT NULL,
          `password` varchar(128) NOT NULL,
          `email` varchar(128) NOT NULL,
          PRIMARY KEY (`username`),
          INDEX(password)
        )
        """,
            """
        CREATE TABLE IF NOT EXISTS auth (
          `authToken` varchar(128) NOT NULL,
          `username` varchar(128) NOT NULL,
          PRIMARY KEY (`authToken`),
          INDEX(username)
        )
        """,
            """
        CREATE TABLE IF NOT EXISTS game (
          `gameID` int NOT NULL AUTO_INCREMENT,
          `whiteUsername` varchar(128),
          `blackUsername` varchar(128),
          `gameName` varchar(128) NOT NULL,
          `game` LONGTEXT NOT NULL,
          PRIMARY KEY (`gameID`),
          INDEX(whiteUsername),
          INDEX(blackUsername),
          INDEX(gameName)
        )
        """
    };

    /**
     * Creates the database if it does not already exist.
     */
    static public void createDatabase() throws DataAccessException {
        var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        try (var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error: failed to create database", ex);
        }
    }


    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DatabaseManager.getConnection()) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            //do not wrap the following line with a try-with-resources
            var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException ex) {
            throw new DataAccessException("Error: failed to get connection", ex);
        }
    }

    private static void loadPropertiesFromResources() {
        try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (propStream == null) {
                throw new Exception("Unable to load db.properties");
            }
            Properties props = new Properties();
            props.load(propStream);
            loadProperties(props);
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties", ex);
        }
    }

    private static void loadProperties(Properties props) {
        databaseName = props.getProperty("db.name");
        dbUsername = props.getProperty("db.user");
        dbPassword = props.getProperty("db.password");

        var host = props.getProperty("db.host");
        var port = Integer.parseInt(props.getProperty("db.port"));
        connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
    }
}
