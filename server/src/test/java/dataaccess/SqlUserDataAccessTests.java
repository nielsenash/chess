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
        var userData = sqlUserDataAccess.saveUser(user);
        assertNotNull(userData);
    }

    @Test
    void badSaveUser() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlUserDataAccess.saveUser(user2));
    }


}

