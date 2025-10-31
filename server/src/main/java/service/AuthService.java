package service;

import dataaccess.AuthDataAccess;
import exceptions.UnauthorizedException;
import model.AuthData;

public class AuthService {
    private final AuthDataAccess authDataAccess;

    public AuthService(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
    }

    public void saveAuthData(AuthData authData) throws Exception {
        authDataAccess.saveAuth(authData);
    }

    public void logout(String authToken) throws Exception {
        if (getAuthData(authToken) != null) {
            authDataAccess.deleteAuth(authToken);
        } else {
            throw new UnauthorizedException("Error: unauthorized");
        }
    }

    public AuthData getAuthData(String authToken) throws Exception {
        var authData = authDataAccess.getAuth(authToken);
        if (authData == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        return authData;
    }

    public void clearAuthDatabase() throws Exception {
        authDataAccess.clear();
    }

}
