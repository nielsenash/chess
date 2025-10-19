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
    public boolean saveUser(UserData user) {
        if (!users.containsKey(user.username())) {
            users.put(user.username(), user);
            return true;
        }
        return false;
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }
}
