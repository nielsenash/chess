package service;

import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryUserDataAccess;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthServiceTests {
    MemoryAuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    MemoryUserDataAccess userDataAccess = new MemoryUserDataAccess();
    UserService userService = new UserService(userDataAccess);
    AuthService authService = new AuthService(authDataAccess);

    @Test
    void logout() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        assertNotNull(authDataAccess.getAuth(authData.authToken()));
        authService.logout(authData.authToken());
        assertNull(authDataAccess.getAuth(authData.authToken()));
    }

    @Test
    void unauthorizedLogout() throws Exception {
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        assertNotNull(authDataAccess.getAuth(authData.authToken()));
        Assertions.assertThrows(UnauthorizedException.class, () -> authService.logout("something random"));
    }

    @Test
    void clearDatabase() throws Exception {
        var authData = new AuthData("secret", "ashley");
        authService.saveAuthData(authData);
        authService.clearAuthDatabase();
        assertNull(authDataAccess.getAuth("secret"));
    }
}
