import client.ChessClient;
import ui.ChessBoardLayout;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ 240 Chess Client\n");

        String serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        try {

            var chessClient = new ChessClient(serverUrl);
            chessClient.clear();
            chessClient.run();

        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }
    }
}