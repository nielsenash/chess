package client;

import java.util.Scanner;

public class ChessClient {
    private final State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) {
        //create serverFacade
    }

    public void run() {
        System.out.println("Welcome to chess. Enter something ");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            try {
                result = eval(scanner.nextLine());
                System.out.println(result);
            } catch (Exception e) {
                //do something
            }

        }
        System.out.println();
    }

    public String eval(String input) {
        return switch (input) {
            case "help" -> help();
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
}
