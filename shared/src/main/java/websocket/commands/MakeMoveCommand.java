package websocket.commands;

import chess.ChessMove;

import static websocket.commands.UserGameCommand.CommandType.MAKE_MOVE;

public class MakeMoveCommand extends UserGameCommand {
    private final ChessMove move;

    public MakeMoveCommand(ChessMove move, String authToken, Integer gameID) {
        super(MAKE_MOVE, authToken, gameID);
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }
}
