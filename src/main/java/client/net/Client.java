package client.net;

import client.handler.ClientHandler;
import config.Config;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private static Client instance;
    private static PrintWriter writer;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public Client() {
        try {
            int port = Config.PORT;
            socket = new Socket("127.0.0.1", port);
            writer = new PrintWriter(socket.getOutputStream());
            // 启动监听线程
            new Thread(new ClientHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(String msg) {
        writer.println(msg);
        writer.flush();
    }
}
