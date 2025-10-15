package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DataAccessTest {
    @Test
    void clear() {
        var user = new UserData("ashley", "123", "@2");
        var ua = new MemoryUserDataAccess();
        assertNull(ua.getUser(user.username()));
        ua.saveUser(user);
        assertNotNull(ua.getUser(user.username()));
        ua.clear();
        assertNull(ua.getUser(user.username()));
    }

    @Test
    void saveUser() {
        var user = new UserData("ashley", "123", "@2");
        var ua = new MemoryUserDataAccess();
        assertNull(ua.getUser(user.username()));
        ua.saveUser(user);
    }

    @Test
    void getUser() {
    }
}
