package websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDataAccess;
import dataaccess.GameDataAccess;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import service.GameService;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;


public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connectionManager = new ConnectionManager();
    private final AuthDataAccess authDao;
    private final GameDataAccess gameDao;

    private final GameService gameService;


    public WebSocketHandler(AuthDataAccess authDao, GameDataAccess gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.gameService = new GameService(gameDao);
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        //System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()) {
                case MAKE_MOVE -> makeMove(ctx);
                case LEAVE -> leave(command, ctx.session);
                case RESIGN -> resign(command, ctx.session);
                case CONNECT -> connect(command, ctx.session);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        //System.out.println("Websocket closed");
    }

    public boolean validate(Session session, AuthData user, GameData game) throws Exception {
        if (user == null) {
            sendError(session, "Error: Invalid Auth");
            return false;
        } else if (game == null) {
            sendError(session, "Error: Game not found");
            return false;
        }
        return true;
    }


    public void connect(UserGameCommand command, Session session) throws Exception {

        var user = authDao.getAuth(command.getAuthToken());
        var game = gameDao.getGame(command.getGameID());
        if (!validate(session, user, game)) {
            return;
        }

        connectionManager.add(command.getGameID(), session);
        sendLoadGame(session, game, user);


        String username = user.username();
        String notificationMessage;

        if (username.equals(game.whiteUsername())) {
            notificationMessage = username + " joined the game as white";
        } else if (username.equals(game.blackUsername())) {
            notificationMessage = username + " joined the game as black";
        } else {
            notificationMessage = username + " joined the game as an observer";
        }

        NotificationMessage notification = new NotificationMessage(notificationMessage);
        connectionManager.broadcast(command.getGameID(), session, notification);
    }

    public void leave(UserGameCommand command, Session session) throws Exception {
        var user = authDao.getAuth(command.getAuthToken());
        var game = gameDao.getGame(command.getGameID());
        if (!validate(session, user, game)) {
            return;
        }

        gameService.removePlayer(game.gameID(), user.username());
        connectionManager.remove(game.gameID(), session);

        String username = user.username();
        String notificationMessage = username + " left the game";

        NotificationMessage notification = new NotificationMessage(notificationMessage);
        connectionManager.broadcast(command.getGameID(), session, notification);
    }

    public void makeMove(WsMessageContext ctx) throws Exception {
        var session = ctx.session;
        MakeMoveCommand command = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
        var move = command.getMove();
        var user = authDao.getAuth(command.getAuthToken());
        var game = gameDao.getGame(command.getGameID());

        if (!validate(session, user, game)) {
            return;
        }

        String username = user.username();
        ChessGame.TeamColor color = username.equals(game.whiteUsername()) ? WHITE : BLACK;
        if (game.game().isGameOver()) {
            sendError(session, "Error: Game Over");
            return;
        } else if (!username.equals(game.whiteUsername()) && !username.equals(game.blackUsername())) {
            sendError(session, "Error: Observer cannot make moves");
            return;
        } else if (game.game().getTeamTurn() != color) {
            sendError(session, "Error: It's not your turn");
            return;
        } else if (color != game.game().getChessBoard().getPiece(move.getStartPosition()).getTeamColor()) {
            sendError(session, "Error: Cannot move opponent's piece");
            return;
        }


        try {
            gameService.updateBoard(command.getGameID(), move);
            game = gameDao.getGame(command.getGameID());

            connectionManager.broadcast(command.getGameID(), null, new LoadGameMessage(game.game(), color));
            connectionManager.broadcast(command.getGameID(), session, new NotificationMessage(username + " made the move: " + move.toString()));

            if (game.game().isInCheckmate(BLACK) || game.game().isInCheckmate(WHITE)) {
                sendNotification(command.getGameID(), username + " is in checkmate! GAME OVER ");
            } else if (game.game().isInCheck(BLACK) || game.game().isInCheck(WHITE)) {
                sendNotification(command.getGameID(), username + " is in check!");
            }

        } catch (InvalidMoveException e) {
            sendError(session, "Error: Invalid Move");
        }
    }

    public void resign(UserGameCommand command, Session session) throws Exception {
        var user = authDao.getAuth(command.getAuthToken());
        var game = gameDao.getGame(command.getGameID());
        if (!validate(session, user, game)) {
            return;
        }
        String username = user.username();
        if (!username.equals(game.whiteUsername()) && !username.equals(game.blackUsername())) {
            sendError(session, "Error: Observer cannot resign");
            return;
        }
        if (game.game().isGameOver()) {
            sendError(session, "Error: Game is already over");
            return;
        }
        gameService.setGameOver(game.gameID());
        sendNotification(command.getGameID(), username + " resigned the game");
    }

    private void sendError(Session session, String error) throws Exception {
        connectionManager.send(session, new ErrorMessage(error));
    }

    private void sendNotification(Integer gameID, String message) throws Exception {
        connectionManager.broadcast(gameID, null, new NotificationMessage(message));
    }

    private void sendLoadGame(Session session, GameData game, AuthData user) throws Exception {
        var color = WHITE;
        if (user.username().equals(game.blackUsername())) {
            color = BLACK;
        }
        connectionManager.send(session, new LoadGameMessage(game.game(), color));
    }
}