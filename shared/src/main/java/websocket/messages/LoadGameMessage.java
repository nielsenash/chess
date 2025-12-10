package websocket.messages;

import chess.ChessGame;

import static websocket.messages.ServerMessage.ServerMessageType.LOAD_GAME;

public class LoadGameMessage extends ServerMessage {
    private final ChessGame game;
    private final ChessGame.TeamColor color;

    public LoadGameMessage(ChessGame game, ChessGame.TeamColor color) {
        super(LOAD_GAME);
        this.game = game;
        this.color = color;
    }

    public ChessGame getGame() {
        return game;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "";
    }
}
