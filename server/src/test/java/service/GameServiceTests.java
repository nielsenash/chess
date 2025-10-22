package service;

import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryGameDataAccess;
import dataaccess.MemoryUserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.UserData;
import org.junit.jupiter.api.Test;

import static chess.ChessGame.TeamColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    MemoryAuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    MemoryUserDataAccess userDataAccess = new MemoryUserDataAccess();
    MemoryGameDataAccess gameDataAccess = new MemoryGameDataAccess();
    UserService userService = new UserService(userDataAccess);
    AuthService authService = new AuthService(authDataAccess);
    GameService gameService = new GameService(gameDataAccess);

    @Test
    void listGames() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        gameService.createGame("newGame");
        gameService.createGame("newGame2");
        assertNotNull(gameService.listGames());
        System.out.println(gameService.listGames());
    }

    @Test
    void listGamesUnauthorized() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        assertThrows(UnauthorizedException.class, () -> authService.getAuthData("def not the authToken"));
        gameService.listGames();
    }

    @Test
    void createGame() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        gameService.createGame("newGame");
        assertNotNull(gameService.listGames());
    }

    @Test
    void createGameBadRequest() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        assertThrows(BadRequestException.class, () -> gameService.createGame(null));
    }


    @Test
    void clearDatabase() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        userService.clearUserDatabase();
        assertNull(userDataAccess.getUser(user.username()));
    }

    @Test
    void joinGame() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        gameService.createGame("name");
        gameService.joinGame(WHITE, 1, user.username());
        assertNotNull(gameService.listGames());
    }

    @Test
    void joinGameAlreadyTaken() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var user2 = new UserData("andrew", "a;oeihta", "eoiathb");
        var authData2 = userService.register(user2);
        authService.saveAuthData(authData2);
        gameService.createGame("name");
        gameService.joinGame(WHITE, 1, user.username());
        assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(WHITE, 1, user2.username()));
    }


}
