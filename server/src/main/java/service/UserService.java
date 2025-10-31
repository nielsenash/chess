package service;

import dataaccess.UserDataAccess;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.LoginRequest;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class UserService {

    private final UserDataAccess userDataAccess;

    public UserService(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    public String getAuthToken() {
        return UUID.randomUUID().toString();
    }

    public String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public AuthData register(UserData user) throws Exception {
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestException("Error: bad request");
        }
        //immediately encrypt the password
        var userEncryptedPassword = new UserData(user.username(), getHashedPassword(user.password()), user.email());
        if (this.userDataAccess.saveUser(userEncryptedPassword) == null) {
            throw new AlreadyTakenException("Error: already taken");
        } else {
            return new AuthData(getAuthToken(), user.username());
        }
    }

    public AuthData login(LoginRequest loginRequest) throws Exception {
        var user = userDataAccess.getUser(loginRequest.username());
        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new BadRequestException("Error: bad request");
        } else if (user == null || !BCrypt.checkpw(loginRequest.password(), user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        } else {
            return new AuthData(getAuthToken(), user.username());
        }
    }

    public void clearUserDatabase() throws Exception {
        userDataAccess.clear();
    }
}
