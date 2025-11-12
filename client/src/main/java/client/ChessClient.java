package client;

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
                result = eval("register");
                System.out.println(result);
            } catch (Exception e) {
                //do something
            }

        }
        System.out.println();
    }

    public String eval(String input) throws Exception {
        return switch (input) {
            case "help" -> help();
            case "register" -> register();
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

    public String register() throws Exception {
        System.out.println("inside register");
        serverFacade.register(new UserData("ashley", "nielsen", "something"));
        System.out.println("after server facade register");
        state = State.SIGNEDIN;
        return help();
    }
}
