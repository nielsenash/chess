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
        if (entries.length == 4) {
            var user = serverFacade.register(new UserData(entries[1], entries[2], entries[3]));
            if (user == null) {
                throw new Exception("Username Already Taken");
            }
            state = State.SIGNEDIN;
            return help();
        } else {
            throw new Exception("Invalid input"); // probs want to create a new exception
        }
    }

    public String login(String[] entries) throws Exception {
        if (entries.length == 3) {
            var user = serverFacade.login(new LoginRequest(entries[1], entries[2]));
            if (user == null) {
                throw new Exception("Invalid Credentials");
            }
            state = State.SIGNEDIN;
            return help();
        } else {
            throw new Exception("Invalid input"); // probs want to create a new exception
        }
    }
}
