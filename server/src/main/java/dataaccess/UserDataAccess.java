package dataaccess;

import model.UserData;

public interface UserDataAccess {
    void clear();

    void saveUser(UserData user);

    UserData getUser(String userName);

}
