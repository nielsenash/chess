package dataaccess;

import model.UserData;

public interface UserDataAccess {
    void clear();

    UserData saveUser(UserData user);

    UserData getUser(String userName);

}
