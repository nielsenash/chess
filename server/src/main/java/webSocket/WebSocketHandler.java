package webSocket;

import com.google.gson.Gson;
import dataaccess.AuthDataAccess;
import dataaccess.GameDataAccess;
import dataaccess.UserDataAccess;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;


public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connectionManager = new ConnectionManager();
    private final AuthDataAccess authDao;
    private final GameDataAccess gameDao;
    private final UserDataAccess userDao;


    public WebSocketHandler(AuthDataAccess authDao, GameDataAccess gameDao, UserDataAccess userDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }


    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()) {
//                case MAKE_MOVE ->
//                case LEAVE ->
//                case RESIGN ->
                case CONNECT -> connect(command, ctx.session);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    public void connect(UserGameCommand command, Session session) throws Exception {
        var user = authDao.getAuth(command.getAuthToken());
        if (user == null) {
            sendError(session, "Error: Invalid Auth");
            return;
        }

        var game = gameDao.getGame(command.getGameID());
        if (game == null) {
            sendError(session, "Error: Game not found");
            return;
        }

        connectionManager.add(command.getGameID(), session);
        LoadGameMessage message = new LoadGameMessage(game.game());
        session.getRemote().sendString(new Gson().toJson(message));


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
    
    private void sendError(Session session, String errorMessage) throws Exception {
        ErrorMessage error = new ErrorMessage(errorMessage);
        session.getRemote().sendString(new Gson().toJson(error));
    }
}