package dataaccess;

import model.UserData;

public interface UserDataAccess {
    void clear() throws DataAccessException;

    UserData saveUser(UserData user) throws DataAccessException;

    UserData getUser(String userName) throws DataAccessException;

}
