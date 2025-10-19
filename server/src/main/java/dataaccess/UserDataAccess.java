package dataaccess;

import model.UserData;

public interface UserDataAccess {
    void clear();

    boolean saveUser(UserData user);

    UserData getUser(String userName);

}
