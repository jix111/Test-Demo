package server.app;

import config.Config;
import server.net.Server;

public class ServerService {
    public static void main(String[] args) {
        Config.init();
        new Server();
        System.out.println("Server Started!");
    }
}
