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
    public int saveUser(UserData user) {
        if (user.username() == null || user.password() == null || user.email() == null) {
            return 401;
        }
        if (!users.containsKey(user.username())) {
            users.put(user.username(), user);
            return 200;
        }
        return 403;
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }
}
