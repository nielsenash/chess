package dataaccess;

import model.User;

public interface UserDataAccess {
    void clear();

    void saveUser(User user);

    User getUser(String userName);
}
