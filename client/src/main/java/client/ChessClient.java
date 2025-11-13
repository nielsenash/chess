package client;

import model.LoginRequest;
import model.UserData;

import java.util.Scanner;

public class ChessClient {
    private State state = State.SIGNEDOUT;
    private final ServerFacade serverFacade;

    public ChessClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to chess. Type help to get started!");

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
            case "create" -> createGame(entries);
            default -> input;
        };
    }

    public String help() {
        if (state == State.SIGNEDIN) {
            return """
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
        serverFacade.register(new UserData(entries[1], entries[2], entries[3]));
        state = State.SIGNEDIN;
        return help();
    }

    public String login(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        serverFacade.login(new LoginRequest(entries[1], entries[2]));
        state = State.SIGNEDIN;
        return help();
    }

    public String createGame(String[] entries) throws Exception {
        if (entries.length != 2) {
            throw new Exception("Invalid input");
        }
        serverFacade.createGame(entries[1]);
        return "Game with name " + entries[1] + "has been created";
    }
}
