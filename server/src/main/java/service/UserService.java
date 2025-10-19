package service;

import dataaccess.UserDataAccess;
import model.AuthData;
import model.LoginRequest;
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


    public AuthData register(UserData user) throws Exception {
        //somewhere check if username is already registered
        this.userDataAccess.saveUser(user);
        return new AuthData(getAuthToken(), user.username());
    }

    public void login(LoginRequest loginRequest) throws Exception {
        var user = userDataAccess.getUser(loginRequest.userName());
        if (user == null || !user.password().equals(loginRequest.passWord())) {
            throw new Exception("Incorrect username and/or password");
        }

    }

    public void logout() {

    }
}
