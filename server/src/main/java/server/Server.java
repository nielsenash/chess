package server;

import com.google.gson.Gson;
import dataaccess.UserDataAccess;
import io.javalin.*;
import io.javalin.http.Context;
import model.UserData;
import service.UserService;

public class Server {

    private final Javalin server;
    private UserDataAccess userDataAccess;
    private final UserService userService = new UserService(userDataAccess);

    public Server() {
        server = Javalin.create(config -> config.staticFiles.add("web"));
        // Register your endpoints and exception handlers here.
        server.delete("db", ctx -> ctx.result("{}"));
        server.post("user", this::register);
    }

    private void register(Context ctx) {
        var serializer = new Gson();
        var request = serializer.fromJson(ctx.body(), UserData.class);
        var response = userService.register(request);
        ctx.result(serializer.toJson(response));
    }


    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
