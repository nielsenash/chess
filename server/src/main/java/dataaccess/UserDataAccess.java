package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDataAccess {
    void clear();

    UserData saveUser(UserData user) throws DataAccessException;

    UserData getUser(String userName) throws DataAccessException;

}
