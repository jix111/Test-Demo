package client.handler;

import client.ui.LoginFrame;
import utils.Message;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private BufferedReader reader;
    private PrintWriter writer;
    public ClientHandler(Socket socket) {
        try {
            reader = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isOnline = true;
        while (isOnline)
        {
            // 接收服务器的信息
            try {
                String msg = reader.readLine();
                String[] data = msg.split(Message.SPLIT);
                if(data[0].equals(Message.REGISTER)) {
                    register(data);
                } else if(data[0].equals(Message.LOGIN)) {
                    login(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                isOnline = false;
            }
        }
    }

    // 得到注册信息
    private void register(String[] data) {
        LoginFrame loginFrame = LoginFrame.getInstance();
        JLabel statusMsg = loginFrame.getStatusMsg();
        String result = data[1];
        if(result.equals(Message.SUCCCESS)) {
            statusMsg.setForeground(Color.GREEN);
            statusMsg.setText("注册成功");
        } else if(result.equals(Message.FAILURE)) {
            statusMsg.setForeground(Color.RED);
            statusMsg.setText("用户名已存在");
        } else if(result.equals(Message.UNKNOWN)) {
            statusMsg.setForeground(Color.RED);
            statusMsg.setText("数据注入错误");
        }
    }
    
    //得到登录信息
    private void login(String[] data) {
        LoginFrame loginFrame = LoginFrame.getInstance();
        JLabel statusMsg = loginFrame.getStatusMsg();
        String result = data[1];
        if(result.equals(Message.SUCCCESS)) {
            statusMsg.setForeground(Color.GREEN);
            statusMsg.setText("登录成功");
            String msg = Message.requestLogin(loginFrame.getTxtUserIdField().getText(), loginFrame.getTxtPasswordField().getText());
            writer.println(msg);
            writer.flush();
        } else if(result.equals(Message.FAILURE)){
            statusMsg.setForeground(Color.RED);
            statusMsg.setText("用户名或密码错误");
        } else if(result.equals(Message.UNKNOWN)) {
            statusMsg.setForeground(Color.RED);
            statusMsg.setText("用户不存在");
        }
    }
}
