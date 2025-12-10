
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import ui.ChessBoardLayout;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("♕ 240 Chess Client\n");

        var chessGame = new ChessGame();
        chessGame.makeMove(new ChessMove(new ChessPosition(2, 5), new ChessPosition(4, 5), null));
        var chessBoardLayout = new ChessBoardLayout(chessGame.getChessBoard().getBoard(), BLACK);
        chessBoardLayout.printBoard();

//        String serverUrl = "http://localhost:8080";
//        if (args.length == 1) {
//            serverUrl = args[0];
//        }
//
//        try {
//            var chessClient = new ChessClient(serverUrl);
//            chessClient.run();
//
//        } catch (Throwable ex) {
//            System.out.printf("Unable to start server: %s%n", ex.getMessage());
//        }
    }
}