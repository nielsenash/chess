package webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private final Map<Integer, Set<Session>> sessions = new ConcurrentHashMap<>();
    private final Map<Session, Integer> gameConnections = new ConcurrentHashMap<>();


    public Integer getGameID(Session session) {
        return gameConnections.get(session);
    }

    public void add(Integer gameID, Session session) {
        sessions.computeIfAbsent(gameID, id -> ConcurrentHashMap.newKeySet()).add(session);
        gameConnections.put(session, gameID);
    }

    public void remove(Integer gameID, Session session) {
        var s = sessions.get(gameID);
        if (s != null) {
            s.remove(session);
        }
        gameConnections.remove(session);
    }

    public void broadcast(Integer gameID, Session exclude, Object message) throws IOException {
        Set<Session> s = sessions.get(gameID);
        if (s == null) {
            return;
        }

        var json = new Gson().toJson(message);
        for (var session : s) {
            if (session != exclude && session.isOpen()) {
                session.getRemote().sendString(json);
            }
        }
    }

    public void send(Session session, Object message) throws IOException {
        session.getRemote().sendString(new Gson().toJson(message));
    }

    public Map<Integer, Set<Session>> getSessions() {
        return sessions;
    }
}

