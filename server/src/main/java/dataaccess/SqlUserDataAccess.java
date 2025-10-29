package dataaccess;

import model.UserData;

public class SqlUserDataAccess implements UserDataAccess {
    @Override
    public void clear() {

    }

    @Override
    public int saveUser(UserData user) {
        return 0;
    }

    @Override
    public UserData getUser(String userName) {
        return null;
    }
}
