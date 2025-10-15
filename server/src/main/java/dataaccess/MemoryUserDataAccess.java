package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDataAccess implements UserDataAccess {
    private final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void saveUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }
}
