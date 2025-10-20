package service;

import dataaccess.UserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
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
        if (this.userDataAccess.saveUser(user) == 200) {
            return new AuthData(getAuthToken(), user.username());
        } else if (this.userDataAccess.saveUser(user) == 401) {
            throw new BadRequestException("Error: bad request");
        } else {
            throw new AlreadyTakenException("Error: already taken");
        }
    }

    public AuthData login(LoginRequest loginRequest) throws Exception {
        var user = userDataAccess.getUser(loginRequest.username());
        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new BadRequestException("Error: bad request");
        } else if (user == null || !user.password().equals(loginRequest.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            return new AuthData(getAuthToken(), user.username());
        }
    }

    public void clearUserDatabase() {
        userDataAccess.clear();
    }
}
