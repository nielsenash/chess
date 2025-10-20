package service;

import dataaccess.AuthDataAccess;
import model.AuthData;

public class AuthService {
    private final AuthDataAccess authDataAccess;

    public AuthService(AuthDataAccess authDataAccess) {
        this.authDataAccess = authDataAccess;
    }

    public void saveAuthData(AuthData authData) {
        authDataAccess.saveAuth(authData);
    }

}
