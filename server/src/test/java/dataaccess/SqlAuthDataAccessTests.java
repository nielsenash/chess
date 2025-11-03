package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SqlAuthDataAccessTests {

    SqlAuthDataAccess sqlAuthDataAccess = new SqlAuthDataAccess();
    UserData user = new UserData("ashley", "nielsen", "jfa;8oeih");
    UserData user2 = new UserData(null, "nielsen", "jfa;8oeih");
    AuthData auth = new AuthData(";aoiehtgb", ";aieht");
    AuthData auth2 = new AuthData(null, ";aieht");

    @Test
    void saveAuth() throws Exception {
        assertNotNull(sqlAuthDataAccess.saveAuth(auth));
    }

    @Test
    void badSaveAuth() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlAuthDataAccess.saveAuth(auth2));
    }

//    @Test
//    void getUser() throws Exception {
//        sqlUserDataAccess.saveUser(user);
//        assertNotNull(sqlUserDataAccess.getUser(user.username()));
//    }
//
//    @Test
//    void badGetUser() throws Exception {
//        sqlUserDataAccess.saveUser(user);
//        assertNull(sqlUserDataAccess.getUser("aoeitha;nge"));
//    }

    @Test
    void clear() throws Exception {
        assertDoesNotThrow(() -> sqlAuthDataAccess.clear());
    }

}
