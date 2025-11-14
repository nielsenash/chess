package client;

import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.LoginRequest;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import static chess.ChessGame.TeamColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    public void clear() throws Exception {
        serverFacade.clear();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void register() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        assertEquals(user1.username(), auth.username());
    }

    @Test
    public void badRegister() {
        var user1 = new UserData("michael", null, "@");
        assertThrows(BadRequestException.class, () -> serverFacade.register(user1));
    }

    @Test
    public void login() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        serverFacade.register(user1);
        assertDoesNotThrow(() -> serverFacade.login(new LoginRequest(user1.username(), user1.password())));
    }

    @Test
    public void badLogin() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        serverFacade.register(user1);
        assertThrows(UnauthorizedException.class, () -> serverFacade.login(new LoginRequest("anna", "ein;aoien")));
    }

    @Test
    public void logout() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        assertDoesNotThrow(() -> serverFacade.logout(auth.authToken()));
    }

    @Test
    public void badLogout() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        serverFacade.register(user1);
        assertThrows(UnauthorizedException.class, () -> serverFacade.logout("random auth token"));
    }

    @Test
    public void listGames() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        assertDoesNotThrow(() -> serverFacade.listGames(auth.authToken()));
    }

    @Test
    public void unauthorizedListGames() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        serverFacade.register(user1);
        assertThrows(UnauthorizedException.class, () -> serverFacade.listGames("random"));
    }


    @Test
    public void createGame() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        assertDoesNotThrow(() -> serverFacade.createGame(":)", auth.authToken()));
    }

    @Test
    public void badCreateGame() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        assertThrows(BadRequestException.class, () -> serverFacade.createGame(null, auth.authToken()));
    }

    @Test
    public void joinGame() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        serverFacade.createGame(":)", auth.authToken());
        assertDoesNotThrow(() -> serverFacade.joinGame(1, WHITE, auth.authToken()));
    }

    @Test
    public void badJoinGame() throws Exception {
        var user1 = new UserData("michael", "star", "@");
        var auth = serverFacade.register(user1);
        serverFacade.createGame(":)", auth.authToken());
        assertThrows(UnauthorizedException.class, () -> serverFacade.joinGame(1, WHITE, "something"));
    }


    @Test
    public void clearWorks() throws Exception {
        var user2 = new UserData("andrew", "loves", "ashley");
        serverFacade.register(user2);
        serverFacade.clear();
        assertDoesNotThrow(() -> serverFacade.register(user2));
    }
}