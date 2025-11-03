package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SqlGameDataAccessTests {
    SqlGameDataAccess sqlGameDataAccess = new SqlGameDataAccess();
    AuthData auth = new AuthData(";aoiehtgb", ";aieht");
    AuthData auth2 = new AuthData(null, ";aieht");

    @Test
    void createGame() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.createGame("newGame"));
    }

    @Test
    void badCreateGame() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlGameDataAccess.createGame(null));
    }

//    @Test
//    void getAuth() throws Exception {
//        assertNotNull(sqlAuthDataAccess.getAuth(auth.authToken()));
//    }
//
//    @Test
//    void badGetAuth() throws Exception {
//        assertNull(sqlAuthDataAccess.getAuth("aoeitha;nge"));
//    }
//
//    @Test
//    void deleteAuth() throws Exception {
//        assertDoesNotThrow(() -> sqlAuthDataAccess.deleteAuth(auth.authToken()));
//    }

//    @Test
//    void badDeleteAuth() throws Exception {
//        assertThrows(RuntimeException.class, () -> sqlAuthDataAccess.deleteAuth("eirtyhuie"));
//    }

    @Test
    void clear() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.clear());
    }
}
