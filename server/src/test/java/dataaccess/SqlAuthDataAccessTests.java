package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SqlAuthDataAccessTests {

    SqlAuthDataAccess sqlAuthDataAccess = new SqlAuthDataAccess();
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

    @Test
    void getAuth() throws Exception {
        assertNotNull(sqlAuthDataAccess.getAuth(auth.authToken()));
    }

    @Test
    void badGetAuth() throws Exception {
        assertNull(sqlAuthDataAccess.getAuth("aoeitha;nge"));
    }

    @Test
    void deleteAuth() throws Exception {
        assertDoesNotThrow(() -> sqlAuthDataAccess.deleteAuth(auth.authToken()));
    }

//    @Test
//    void badDeleteAuth() throws Exception {
//        assertThrows(RuntimeException.class, () -> sqlAuthDataAccess.deleteAuth("eirtyhuie"));
//    }

    @Test
    void clear() throws Exception {
        assertDoesNotThrow(() -> sqlAuthDataAccess.clear());
    }

}
