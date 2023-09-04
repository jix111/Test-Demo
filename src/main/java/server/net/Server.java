package server.net;

import config.Config;
import server.handler.ServerHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private static int connectionCount = 0;
    public Server()
    {
        try {
            serverSocket = new ServerSocket(Config.PORT);
            // 循环监听等待客户端的连接
            while (true) {
                System.out.println("等待客户端连接中... 端口[" + Config.PORT + "]");
                // 创建接收接口
                Socket socket = serverSocket.accept();
                System.out.println("客户端已连接");
                // 启动监听线程
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getConnectionCount() { return connectionCount; }
    public static void addConnectionCount() { connectionCount++; }
    public static void subtractConnectionCount() { connectionCount--; }
}
