package service;

import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryGameDataAccess;
import dataaccess.MemoryUserDataAccess;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.LoginRequest;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void createGame() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        gameService.createGame("newGame");
        assertNotNull(gameService.listGames());
    }

    @Test
    void badRegister() throws Exception {
        var user = new UserData("ashley", null, "jfa;8oeih");
        Assertions.assertThrows(BadRequestException.class, () -> userService.register(user));
    }

    @Test
    void login() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var user2 = new LoginRequest("ashley", "nielsen");
        var authData2 = userService.login(user2);
        authService.saveAuthData(authData2);
        assertNotNull(userDataAccess.getUser(user.username()));
        assertNotNull(authDataAccess.getAuthTokens());
    }

    @Test
    void unauthorizedLogin() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var loginRequest = new LoginRequest("ashley", "ashley");
        Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(loginRequest));
    }

    @Test
    void clearDatabase() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        userService.clearUserDatabase();
        assertNull(userDataAccess.getUser(user.username()));
    }
}
