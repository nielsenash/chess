package dataaccess;

import model.User;

import java.util.HashMap;

public class MemoryUserDataAccess implements UserDataAccess {
    private final HashMap<String, User> users = new HashMap<>();

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void saveUser(User user) {
        users.put(user.username(), user);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }
}
