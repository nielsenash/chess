package service;

import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryUserDataAccess;
import model.LoginRequest;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserServiceTests {

    MemoryAuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    MemoryUserDataAccess userDataAccess = new MemoryUserDataAccess();
    UserService userService = new UserService(userDataAccess);
    AuthService authService = new AuthService(authDataAccess);

    @Test
    void register() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var user2 = new UserData("andrew", "ao;fjeo8", "aoiwth;");
        var authData2 = userService.register(user2);
        authService.saveAuthData(authData2);
        assertNotNull(userDataAccess.getUser(user.username()));
        assertNotNull(authDataAccess.getAuthTokens());
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
    void clearDatabase() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var user2 = new LoginRequest("ashley", "nielsen");
        var authData2 = userService.login(user2);
        authService.saveAuthData(authData2);
        assertNotNull(userDataAccess.getUser(user.username()));
        assertNotNull(authDataAccess.getAuthTokens());
    }


}

