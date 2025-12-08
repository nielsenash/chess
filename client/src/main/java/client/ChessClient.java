package client;

import chess.ChessGame;
import client.websocket.NotificationHandler;
import client.websocket.WebSocketFacade;
import model.LoginRequest;
import model.UserData;
import ui.ChessBoardLayout;
import websocket.messages.ServerMessage;

import java.util.Scanner;

import static chess.ChessGame.TeamColor.WHITE;
import static client.State.*;

public class ChessClient implements NotificationHandler {
    private State state = SIGNEDOUT;
    private final ServerFacade serverFacade;
    private final WebSocketFacade webSocketFacade;
    private String authToken;
    private int numGames = 0;

    public ChessClient(String serverUrl) throws Exception {
        serverFacade = new ServerFacade(serverUrl);
        webSocketFacade = new WebSocketFacade(serverUrl, this);
    }

    public void clear() throws Exception {
        serverFacade.clear();
    }

    public void run() {
        System.out.println("Welcome to chess. Type help to get started!\n");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            try {
                result = eval(scanner.nextLine());
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
    }

    public String eval(String input) throws Exception {
        var entries = input.split(" ");
        var command = entries[0];
        return (state == SIGNEDOUT) ?
                switch (command) {
                    case "help" -> help();
                    case "register" -> register(entries);
                    case "login" -> login(entries);
                    case "quit" -> "quit";
                    default -> "Invalid Option";
                } :
                (state == SIGNEDIN) ?
                        switch (command) {
                            case "help" -> help();
                            case "logout" -> logout(entries);
                            case "create" -> createGame(entries);
                            case "list" -> listGames(entries);
                            case "join" -> joinGame(entries);
                            case "observe" -> observeGame(entries);
                            case "quit" -> "quit";
                            default -> "Invalid Option";
                        } :
                        switch (command) {
                            case "help" -> help();
                            case "redraw chess board" -> null; //redraw(entries);
                            case "leave" -> "leaving the game"; //leave(entries);
                            case "make move" -> null; //makeMove(entries);
                            case "resign" -> null; //resign(entries);
                            case "highlight legal moves" -> null; //highlight(entries);
                            default -> "Invalid Option";
                        };


    }

    public String help() {
        if (state == SIGNEDIN) {
            return """
                    \nOPTIONS
                    - create <NAME>
                    - list
                    - join <ID> [WHITE/BLACK]
                    - observe <ID>
                    - logout
                    - quit
                    - help
                    """;
        } else if (state == SIGNEDOUT) {
            return """
                    \nOPTIONS
                    - register <USERNAME> <PASSWORD> <EMAIL>
                    - login <USERNAME> <PASSWORD>
                    - quit
                    - help
                    """;
        } else {
            return """
                    \nOPTIONS
                    - redraw chess board
                    - leave
                    - make move <START> <END>
                    - resign
                    - highlight legal moves
                    """;
        }

    }

    public String register(String[] entries) throws Exception {
        if (entries.length != 4) {
            throw new Exception("Invalid input");
        }
        authToken = serverFacade.register(new UserData(entries[1], entries[2], entries[3])).authToken();
        state = SIGNEDIN;
        return "Successfully registered user " + entries[1] + "\n" + help();
    }

    public String login(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        authToken = serverFacade.login(new LoginRequest(entries[1], entries[2])).authToken();
        state = SIGNEDIN;
        return "Successfully logged in user " + entries[1] + "\n" + help();
    }

    public String logout(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        serverFacade.logout(authToken);
        state = SIGNEDOUT;
        return "Successfully logged out user\n" + help();
    }

    public String listGames(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        if (numGames == 0) {
            return "No games found";
        }

        var games = serverFacade.listGames(authToken);
        StringBuilder gamesList = new StringBuilder();
        for (int i = 0; i < games.size(); i++) {
            String gameInfo = String.format("Game: %d\n - Name: %s\n - White User: %s\n - Black User: %s\n",
                    i + 1, games.get(i).gameName(), games.get(i).whiteUsername(), games.get(i).blackUsername());
            gamesList.append(gameInfo);
        }
        return gamesList.toString();
    }

    public String createGame(String[] entries) throws Exception {
        if (entries.length != 2) {
            throw new Exception("Invalid input");
        }
        serverFacade.createGame(entries[1], authToken);
        numGames++;
        return "Game with name " + entries[1] + " has been created";
    }

    public String joinGame(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        int gameId;
        ChessGame.TeamColor color;
        try {
            gameId = Integer.parseInt(entries[1]);
            color = ChessGame.TeamColor.valueOf(entries[2].toUpperCase());
        } catch (Exception e) {
            throw new Exception("Game ID must be a number and color must be of the form [WHITE/BLACK]");
        }
        if (gameId > numGames) {
            throw new Exception("Game " + gameId + " does not exist");
        }

        serverFacade.joinGame(gameId, color, authToken);
        state = INGAME;
        var chessBoardLayout = new ChessBoardLayout(color);
        chessBoardLayout.printBoard();
        return "Joined Game as " + entries[2] + " player";
    }

    public String observeGame(String[] entries) throws Exception {
        if (entries.length != 2) {
            throw new Exception("Invalid input");
        }
        int gameId;
        try {
            gameId = Integer.parseInt(entries[1]);
        } catch (Exception e) {
            throw new Exception("Game ID must be a number");
        }
        if (gameId > numGames) {
            throw new Exception("Game " + gameId + " does not exist");
        }

        var chessBoardLayout = new ChessBoardLayout(WHITE);
        chessBoardLayout.printBoard();
        return "Observing game " + gameId;
    }

    @Override
    public void notify(ServerMessage message) {
        System.out.println(message);
    }
}
