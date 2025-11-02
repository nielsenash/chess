package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SqlUserDataAccessTests {

    SqlUserDataAccess sqlUserDataAccess = new SqlUserDataAccess();
    UserData user = new UserData("ashley", "nielsen", "jfa;8oeih");
    UserData user2 = new UserData(null, "nielsen", "jfa;8oeih");

    @Test
    void saveUser() throws Exception {
        assertNotNull(sqlUserDataAccess.saveUser(user));
    }

    @Test
    void badSaveUser() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlUserDataAccess.saveUser(user2));
    }

    @Test
    void getUser() throws Exception {
        sqlUserDataAccess.saveUser(user);
        assertNotNull(sqlUserDataAccess.getUser(user.username()));
    }

    @Test
    void badGetUser() throws Exception {
        sqlUserDataAccess.saveUser(user);
        assertNull(sqlUserDataAccess.getUser("aoeitha;nge"));
    }
}

