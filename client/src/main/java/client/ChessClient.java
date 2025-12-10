package client;

import chess.*;
import client.websocket.NotificationHandler;
import client.websocket.WebSocketFacade;
import model.LoginRequest;
import model.UserData;
import ui.ChessBoardLayout;
import websocket.messages.ServerMessage;

import java.util.Map;
import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static client.State.*;

public class ChessClient implements NotificationHandler {
    private State state = SIGNEDOUT;
    private final ServerFacade serverFacade;
    private final WebSocketFacade webSocketFacade;
    private String authToken;
    private Integer gameID;
    private ChessGame.TeamColor color = WHITE;


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
                        (state == INGAME) ?
                                switch (command) {
                                    case "help" -> help();
                                    case "redraw" -> redraw(entries);
                                    case "leave" -> leave(entries);
                                    case "move" -> makeMove(entries);
                                    case "resign" -> resign(entries);
                                    case "highlight" -> null; //highlight(entries);
                                    default -> "Invalid Option";
                                } :
                                switch (command) {
                                    case "help" -> help();
                                    case "redraw" -> redraw(entries);
                                    case "leave" -> leave(entries);
                                    case "highlight" -> null; //highlight(entries);
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
        } else if (state == INGAME) {
            return """
                    \nOPTIONS
                    - redraw
                    - leave
                    - move <START> <END>
                    - resign
                    - highlight legal moves
                    """;
        } else {
            return """
                    \nOPTIONS
                    - redraw
                    - leave
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
        var games = serverFacade.listGames(authToken);
        if (games == null || games.isEmpty()) {
            return "No games found";
        }
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
        return "Game with name " + entries[1] + " has been created";
    }

    public String joinGame(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        try {
            gameID = Integer.parseInt(entries[1]);
            color = ChessGame.TeamColor.valueOf(entries[2].toUpperCase());
        } catch (Exception e) {
            throw new Exception("Game ID must be a number and color must be of the form [WHITE/BLACK]");
        }
        var games = serverFacade.listGames(authToken);
        if (gameID < 1 || gameID > games.size()) {
            throw new Exception("Game " + gameID + " does not exist");
        }
        serverFacade.joinGame(gameID, color, authToken);
        webSocketFacade.setClientColor(color);
        webSocketFacade.sendConnectMessage(gameID, authToken);
        state = INGAME;
        return "Joined Game as " + entries[2] + " player";
    }

    public String observeGame(String[] entries) throws Exception {
        if (entries.length != 2) {
            throw new Exception("Invalid input");
        }
        try {
            gameID = Integer.parseInt(entries[1]);
        } catch (Exception e) {
            throw new Exception("Game ID must be a number");
        }
        var games = serverFacade.listGames(authToken);
        if (gameID < 1 || gameID > games.size()) {
            throw new Exception("Game " + gameID + " does not exist");
        }
        webSocketFacade.setClientColor(WHITE);
        webSocketFacade.sendConnectMessage(gameID, authToken);
        state = INGAMEOBSERVER;
        return "Observing game " + gameID;
    }

    public String leave(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        webSocketFacade.sendLeaveMessage(gameID, authToken);
        state = SIGNEDIN;
        return "Leaving the game";
    }

    public String resign(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        System.out.println("Please confirm your resignation: [yes/no] \n");

        Scanner scanner = new Scanner(System.in);
        var result = eval(scanner.nextLine());
        if (result.equals("yes")) {
            webSocketFacade.sendResignMessage(gameID, authToken);
        } else {
            return "resignation cancelled";
        }

        return "";
    }

    public ChessMove convertToMove(String start, String end) throws Exception {
        Map<String, Integer> conversions = Map.of(
                "a", 1, "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8
        );
        char[] startCoordinates = start.toCharArray();
        char[] endCoordinates = end.toCharArray();
        if (startCoordinates.length != 2 || endCoordinates.length != 2) {
            throw new InvalidMoveException("Start and end must be of this form: e4");
        }

        var startRow = Integer.parseInt(String.valueOf(startCoordinates[1]));
        var startCol = conversions.get(String.valueOf(startCoordinates[0]));
        var endRow = Integer.parseInt(String.valueOf(endCoordinates[1]));
        var endCol = conversions.get(String.valueOf(endCoordinates[0]));
        var startPosition = new ChessPosition(startRow, startCol);
        var endPosition = new ChessPosition(endRow, endCol);
        return new ChessMove(startPosition, endPosition, null);
    }

    public String makeMove(String[] entries) throws Exception {
        if (entries.length != 3) {
            throw new Exception("Invalid input");
        }
        try {
            var move = convertToMove(entries[1], entries[2]);
            var game = serverFacade.listGames(authToken).get(gameID - 1).game();
            var start = move.getStartPosition();
            var end = move.getEndPosition();
            var piece = game.getChessBoard().getPiece(start);

            if (piece != null && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                boolean isPromotion = (piece.getTeamColor() == WHITE && end.getRow() == 8) ||
                        (piece.getTeamColor() == BLACK && end.getRow() == 1);

                if (isPromotion) {
                    System.out.println("Pawn promotion! Choose piece [QUEEN/ROOK/BISHOP/KNIGHT]: ");
                    Scanner scanner = new Scanner(System.in);
                    String promotionChoice = scanner.nextLine();

                    ChessPiece.PieceType promotionPiece;
                    try {
                        promotionPiece = ChessPiece.PieceType.valueOf(promotionChoice);
                        if (promotionPiece != ChessPiece.PieceType.QUEEN &&
                                promotionPiece != ChessPiece.PieceType.ROOK &&
                                promotionPiece != ChessPiece.PieceType.BISHOP &&
                                promotionPiece != ChessPiece.PieceType.KNIGHT) {
                            throw new Exception("Invalid promotion piece");
                        }
                    } catch (IllegalArgumentException e) {
                        return "Error: Invalid promotion piece. Must be QUEEN, ROOK, BISHOP, or KNIGHT";
                    }
                    move = new ChessMove(start, end, promotionPiece);
                }
            }

            webSocketFacade.sendMakeMoveMessage(move, gameID, authToken);
            return "";

        } catch (InvalidMoveException e) {
            return "Error: Invalid Move";
        }

    }

    public String redraw(String[] entries) throws Exception {
        if (entries.length != 1) {
            throw new Exception("Invalid input");
        }
        var game = serverFacade.listGames(authToken).get(gameID - 1).game();


        var chessBoardLayout = new ChessBoardLayout(game.getChessBoard().getBoard(), color);
        chessBoardLayout.printBoard();
        return "";
    }

    @Override
    public void notify(ServerMessage message) {
        System.out.println(message);
    }

}
