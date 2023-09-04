package server.handler;

import server.dao.UserDao;
import server.dao.UserDaoImpl;
import server.net.Server;
import utils.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final UserDao userDao;

    public ServerHandler(Socket socket) {
        this.socket = socket;
        this.userDao = new UserDaoImpl();
        try {
            //获取输入流
            reader = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Server.addConnectionCount();
        System.out.println("用户[" + socket.getInetAddress().getHostAddress() + "] 连接");
        System.out.println("当前连接客户端数量: " + Server.getConnectionCount());
        boolean isOnline = true;
        while (isOnline) {
            try {
                // 接收客户端发出的信息
                String msg = reader.readLine();
                String[] data = msg.split(Message.SPLIT);
                if(data[0].equals(Message.REGISTER)) {
                    register(data);
                } else if(data[0].equals(Message.LOGIN)) {
                    login(data);
                }
            } catch (IOException | SQLException e) {
                // e.printStackTrace();
                isOnline = false;
            }
        }
        Server.subtractConnectionCount();
        System.out.println("用户[" + socket.getInetAddress().getHostAddress() + "] 断开");
        System.out.println("当前连接客户端数量: " + Server.getConnectionCount());
    }

    private void register(String[] data) throws SQLException {
        int result = userDao.register(data[1],data[2]);
        String reMsg;
        synchronized (ServerHandler.class) {
            if(result == 0) {
                reMsg = Message.responseRegister(Message.SUCCCESS);
            } else if(result == 1) {
                reMsg = Message.responseRegister(Message.FAILURE);
            } else {
                reMsg = Message.responseRegister(Message.UNKNOWN);
            }
        }
        writer.println(reMsg);
        writer.flush();
    }

    private void login(String[] data) throws SQLException {
        int result = userDao.login(data[1],data[2]);
        String reMsg;
        if(result == 0) {
            reMsg = Message.responseLogin(Message.SUCCCESS);
        } else if(result == 1) {
            reMsg = Message.responseLogin(Message.FAILURE);
        } else {
            reMsg = Message.responseLogin(Message.UNKNOWN);
        }
        writer.println(reMsg);
        writer.flush();
    }
}
