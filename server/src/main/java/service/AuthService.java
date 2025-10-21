package service;

import dataaccess.AuthDataAccess;
import exceptions.UnauthorizedException;
import model.AuthData;

public class AuthService {
    private final AuthDataAccess authDataAccess;

    public AuthService(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
    }

    public void saveAuthData(AuthData authData) {
        authDataAccess.saveAuth(authData);
    }

    public void logout(String authToken) throws Exception {
        if (getAuthData(authToken) != null) {
            authDataAccess.deleteAuth(authToken);
        } else {
            throw new UnauthorizedException("Error: unauthorized");
        }
    }

    public AuthData getAuthData(String authToken) {
        return authDataAccess.getAuth(authToken);
    }

    public void clearAuthDatabase() {
        authDataAccess.clear();
    }

}
