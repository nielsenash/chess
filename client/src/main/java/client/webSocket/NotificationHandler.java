package client.webSocket;

import websocket.messages.ServerMessage;

public interface NotificationHandler {
    void notify(ServerMessage message);
}

