package dataaccess;

import chess.ChessGame;
import model.GameInfo;

import java.util.List;

public interface GameDataAccess {
    List<GameInfo> listGames();

    void createGame(String gameName);

    void joinGame(ChessGame.TeamColor playerColor, String gameName);
}
