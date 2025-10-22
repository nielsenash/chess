package server;

import com.google.gson.Gson;
import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import io.javalin.*;
import io.javalin.http.Context;
import model.*;
import service.AuthService;
import service.GameService;
import service.UserService;

import java.util.Map;

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

        server.delete("db", this::clear);
        server.post("user", this::register);
        server.post("session", this::login);
        server.delete("session", this::logout);
        server.get("game", this::listGames);
        server.post("game", this::createGame);
        server.put("game", this::joinGame);

    }

    private void clear(Context ctx) {
        authService.clearAuthDatabase();
        userService.clearUserDatabase();
        gameService.clearGameDatabase();
        ctx.result("{}");
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
        var serializer = new Gson();
        var request = serializer.fromJson(ctx.body(), LoginRequest.class);
        try {
            var response = userService.login(request);
            authService.saveAuthData(response);
            ctx.json(serializer.toJson(response));
        } catch (BadRequestException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (AlreadyTakenException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (UnauthorizedException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }

    private void logout(Context ctx) {
        var serializer = new Gson();
        var auth = ctx.header("authorization");
        try {
            authService.logout(auth);
            ctx.result("{}");
        } catch (UnauthorizedException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }

    private void listGames(Context ctx) {
        var serializer = new Gson();
        var auth = ctx.header("authorization");
        try {
            authService.getAuthData(auth);
            var response = gameService.listGames();
            ctx.json(serializer.toJson(Map.of("games", response)));
        } catch (UnauthorizedException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }

    private void createGame(Context ctx) {
        var serializer = new Gson();
        var auth = ctx.header("authorization");
        var request = serializer.fromJson(ctx.body(), CreateGameRequest.class);
        try {
            authService.getAuthData(auth);
            var game = gameService.createGame(request.gameName());
            var response = new CreateGameResponse(game.gameID());
            ctx.json(serializer.toJson(response));
        } catch (BadRequestException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (UnauthorizedException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }

    private void joinGame(Context ctx) {
        var serializer = new Gson();
        var auth = ctx.header("authorization");
        var request = serializer.fromJson(ctx.body(), JoinGameRequest.class);
        try {
            var authData = authService.getAuthData(auth);
            gameService.joinGame(request.playerColor(), request.gameID(), authData.username());
            ctx.result("{}");
        } catch (BadRequestException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (UnauthorizedException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            //do stuff
        }
    }


    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
