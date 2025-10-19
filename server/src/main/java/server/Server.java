package server;

import com.google.gson.Gson;
import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import io.javalin.*;
import io.javalin.http.Context;
import model.UserData;
import service.AuthService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin server;
    private final UserDataAccess userDataAccess = new MemoryUserDataAccess();
    private final UserService userService = new UserService(userDataAccess);
    private final GameDataAccess gameDataAccess = new MemoryGameDataAccess();
    private final GameService gameService = new GameService(gameDataAccess);
    private final AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    private final AuthService authService = new AuthService(authDataAccess);


    public Server() {
        server = Javalin.create(config -> config.staticFiles.add("web"));
        // Register your endpoints and exception handlers here.

        server.delete("db", ctx -> ctx.result("{}"));
        server.post("user", this::register);
        server.post("session", this::login);
        server.delete("session", this::logout);
        server.get("game", this::listGames);
        server.post("game", this::createGame);
        server.put("game", this::joinGame);

    }

    private void register(Context ctx) {
        var serializer = new Gson();
        var request = serializer.fromJson(ctx.body(), UserData.class);
        try {
            var response = userService.register(request);
            authService.saveAuthData(response);
            ctx.json(serializer.toJson(response));
        } catch (BadRequestException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (AlreadyTakenException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }

    private void login(Context ctx) {
    }

    private void logout(Context ctx) {
    }

    private void listGames(Context ctx) {
    }

    private void createGame(Context ctx) {
    }

    private void joinGame(Context ctx) {
    }


    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
