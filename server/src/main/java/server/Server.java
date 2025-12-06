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
import webSocket.WebSocketHandler;
import websocket.commands.UserGameCommand;

import java.util.Map;

public class Server {

    private final Javalin server;

    //private final UserDataAccess userDataAccess = new MemoryUserDataAccess();
    private final UserDataAccess userDataAccess = new SqlUserDataAccess();
    private final UserService userService = new UserService(userDataAccess);

    //private final GameDataAccess gameDataAccess = new MemoryGameDataAccess();
    private final GameDataAccess gameDataAccess = new SqlGameDataAccess();
    private final GameService gameService = new GameService(gameDataAccess);

    //private final AuthDataAccess authDataAccess = new MemoryAuthDataAccess();
    private final AuthDataAccess authDataAccess = new SqlAuthDataAccess();
    private final AuthService authService = new AuthService(authDataAccess);

    private final WebSocketHandler webSocketHandler;


    public Server() {
        server = Javalin.create(config -> config.staticFiles.add("web"));
        webSocketHandler = new WebSocketHandler(authDataAccess, gameDataAccess, userDataAccess);
        try {
            DatabaseManager.configureDatabase();
        } catch (Exception e) {
            System.err.println("Failed to configure database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database configuration failed", e);
        }
        // Register your endpoints and exception handlers here.

        server.delete("db", this::clear);
        server.post("user", this::register);
        server.post("session", this::login);
        server.delete("session", this::logout);
        server.get("game", this::listGames);
        server.post("game", this::createGame);
        server.put("game", this::joinGame);
        server.ws("/ws", ws -> {
            ws.onConnect(webSocketHandler);
            ws.onMessage(ctx -> {
                UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
                if (command.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                    webSocketHandler.connect(command, ctx.session);
                } else {
                    webSocketHandler.handleMessage(ctx);
                }
            });
            ws.onClose(webSocketHandler);
        });
    }

    private void clear(Context ctx) {
        var serializer = new Gson();
        try {
            authService.clearAuthDatabase();
            userService.clearUserDatabase();
            gameService.clearGameDatabase();
            ctx.result("{}");
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
        }

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
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
        } catch (AlreadyTakenException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (DataAccessException e) {
            ctx.status(e.getStatusCode());
            ctx.json(serializer.toJson(e.getErrorResponse()));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: server error"));
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
