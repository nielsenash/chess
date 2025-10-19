package service;

import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryUserDataAccess;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserServiceTests {

    @Test
    void register() throws Exception {
        var authDataAccess = new MemoryAuthDataAccess();
        var userDataAccess = new MemoryUserDataAccess();
        var userService = new UserService(userDataAccess);
        var authService = new AuthService(authDataAccess);
        var user = new UserData("ashley", "nielsen", "jfa;8oeih");
        var authData = userService.register(user);
        authService.saveAuthData(authData);
        var user2 = new UserData("andrew", "ao;fjeo8", "aoiwth;");
        var authData2 = userService.register(user2);
        authService.saveAuthData(authData2);
        assertNotNull(userDataAccess.getUser(user.username()));
        assertNotNull(authDataAccess.getAuthTokens());
    }


}

