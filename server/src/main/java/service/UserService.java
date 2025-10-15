package service;

import dataaccess.UserDataAccess;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    private final UserDataAccess userDataAccess;

    public UserService(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    public String getAuthToken() {
        return UUID.randomUUID().toString();
    }

    public AuthData register(UserData user) {
        userDataAccess.saveUser(user);
        return new AuthData(user.username(), getAuthToken());
    }

    public void login(String username, String password) {

    }

    public void logout() {

    }
}
