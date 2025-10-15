package dataaccess;

import model.AuthData;

public interface AuthDataAccess {
    void clear();

    void saveAuth(AuthData authData);

    AuthData getAuth(String authToken);

    void deleteAuth(String authToken);
}
