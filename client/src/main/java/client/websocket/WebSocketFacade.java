package client.websocket;

import chess.ChessMove;
import com.google.gson.Gson;

import jakarta.websocket.*;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.net.URI;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws Exception {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String json) {
                    Gson gson = new Gson();

                    ServerMessage message = gson.fromJson(json, ServerMessage.class);
                    ServerMessage fullMessage;

                    switch (message.getServerMessageType()) {
                        case NOTIFICATION ->
                                fullMessage = gson.fromJson(json, websocket.messages.NotificationMessage.class);
                        case LOAD_GAME -> fullMessage = gson.fromJson(json, websocket.messages.LoadGameMessage.class);
                        case ERROR -> fullMessage = gson.fromJson(json, websocket.messages.ErrorMessage.class);
                        default -> fullMessage = message;
                    }

                    notificationHandler.notify(fullMessage);
                }
            });
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void sendLeaveMessage(int gameID, String authToken) throws Exception {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.LEAVE,
                authToken,
                gameID
        );
        String json = new Gson().toJson(command);
        session.getBasicRemote().sendText(json);
    }

    public void sendResignMessage(int gameID, String authToken) throws Exception {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.RESIGN,
                authToken,
                gameID
        );
        String json = new Gson().toJson(command);
        session.getBasicRemote().sendText(json);
    }

    public void sendMakeMoveMessage(ChessMove move, int gameID, String authToken) throws Exception {
        MakeMoveCommand command = new MakeMoveCommand(move, authToken, gameID);
        String json = new Gson().toJson(command);
        session.getBasicRemote().sendText(json);
    }

    public void sendConnectMessage(int gameID, String authToken) throws Exception {
        UserGameCommand command = new UserGameCommand(
                UserGameCommand.CommandType.CONNECT,
                authToken,
                gameID
        );

        String json = new Gson().toJson(command);
        session.getBasicRemote().sendText(json);
    }

}

