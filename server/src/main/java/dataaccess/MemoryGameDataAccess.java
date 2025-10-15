package dataaccess;

import chess.ChessGame;
import model.GameInfo;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDataAccess implements GameDataAccess {
    ArrayList<GameInfo> games = new ArrayList<GameInfo>();

    @Override
    public List<GameInfo> listGames() {
        return games;
    }

    @Override
    public void createGame(String gameName) {
        var id = Integer.toString(games.size());
        var game = new GameInfo(id, null, null, gameName);
        games.add(game);
    }

    @Override
    public void joinGame(ChessGame.TeamColor playerColor, String gameName) {

    }
}
