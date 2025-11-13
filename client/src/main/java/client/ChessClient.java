package client;

import model.LoginRequest;
import model.UserData;

import java.util.Scanner;

public class ChessClient {
    private State state = State.SIGNEDOUT;
    private final ServerFacade serverFacade;
    private String authToken;

    public ChessClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
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
        return switch (command) {
            case "help" -> help();
            case "register" -> register(entries);
            case "login" -> login(entries);
            case "logout" -> logout(entries);
            case "create" -> createGame(entries);
            default -> input;
        };
    }

    public String help() {
        if (state == State.SIGNEDIN) {
            return """
                    OPTIONS
                    - create <NAME>
                    - list
                    - join <ID> [WHITE/BLACK]
                    - observe <ID>
                    - logout
                    - quit
                    - help
                    """;
        }
        return """
                OPTIONS
                - register <USERNAME> <PASSWORD> <EMAIL>
                - login <USERNAME> <PASSWORD>
                - quit
                - help
                """;
    }

    public String register(String[] entries) throws Exception {
        if (entries.length != 4) {
            throw new Exception("Invalid input");
        }
        state = State.SIGNEDIN;
        return "Successfully registered user " + entries[1] + "\n" + help();
    }

    public String login(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        authToken = serverFacade.login(new LoginRequest(entries[1], entries[2])).authToken();
        state = State.SIGNEDIN;
        return "Successfully logged in user " + entries[1] + "\n" + help();
    }

    public String logout(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        serverFacade.logout(authToken);
        state = State.SIGNEDOUT;
        return "Successfully logged out user\n" + help();
    }

    public String createGame(String[] entries) throws Exception {
        if (entries.length != 2) {
            throw new Exception("Invalid input");
        }
        serverFacade.createGame(entries[1], authToken);
        return "Game with name " + entries[1] + "has been created";
    }
}
