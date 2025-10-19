package dataaccess;

import model.UserData;

public interface UserDataAccess {
    void clear();

    int saveUser(UserData user);

    UserData getUser(String userName);

}
