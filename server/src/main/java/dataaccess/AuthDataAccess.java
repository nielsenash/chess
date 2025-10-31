package dataaccess;

import model.AuthData;

public interface AuthDataAccess {
    void clear() throws DataAccessException;

    void saveAuth(AuthData authData) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;
}
