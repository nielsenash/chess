package client;

import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        serverFacade = new ServerFacade("http://localhost:8080");
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterAll
    static void clear() {
        //serverFacade.clear();
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

}