package service;

import dataaccess.UserDataAccess;
import model.RegistrationResult;
import model.User;

import java.util.UUID;

public class UserService {

    private final UserDataAccess userDataAccess;

    public UserService(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    public String getAuthToken() {
        return UUID.randomUUID().toString();
    }

    public RegistrationResult register(User user) {
        userDataAccess.saveUser(user);
        return new RegistrationResult(user.username(), getAuthToken());
    }

    public void login(String username, String password) {

    }

    public void logout() {

    }
}
