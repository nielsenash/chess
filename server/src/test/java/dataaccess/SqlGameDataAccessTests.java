package dataaccess;

import org.junit.jupiter.api.Test;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SqlGameDataAccessTests {
    SqlGameDataAccess sqlGameDataAccess = new SqlGameDataAccess();

    @Test
    void createGame() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.createGame("newGame"));
    }

    @Test
    void badCreateGame() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlGameDataAccess.createGame(null));
    }

    @Test
    void joinGame() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.joinGame(WHITE, 1, "ashley"));
    }

    @Test
    void badJoinGame() throws Exception {
        assertThrows(RuntimeException.class, () -> sqlGameDataAccess.joinGame(null, null, null));
    }

    @Test
    void listGames() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.listGames());
    }

    @Test
    void badListGames() throws Exception {
        sqlGameDataAccess.createGame("yay");
        assertNotNull(sqlGameDataAccess.listGames());
    }


    @Test
    void clear() throws Exception {
        assertDoesNotThrow(() -> sqlGameDataAccess.clear());
    }
}
