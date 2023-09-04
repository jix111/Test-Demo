package client.ui;

import client.net.Client;
import utils.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    JTextField txtUserIdField;
    JTextField txtPasswordField;
    JButton btnLogin;
    JButton btnReg;
    JLabel statusMsg;
    private static LoginFrame instance;
    public static LoginFrame getInstance() {
        if (instance == null) {
            instance = new LoginFrame();
        }
        return instance;
    }
    public LoginFrame() {
        txtUserIdField = new JTextField();
        txtPasswordField = new JTextField();
        btnLogin = new JButton();
        btnLogin.setText("登录");

        btnReg = new JButton();
        btnReg.setText("注册");

        statusMsg = new JLabel();
        statusMsg.setText("状态信息栏");
        statusMsg.setForeground(Color.BLACK);

        this.setTitle("注册及登录窗口");
        this.setLayout(null);

        this.add(txtUserIdField);
        txtUserIdField.setBounds(10, 10, 160, 40);

        this.add(txtPasswordField);
        txtPasswordField.setBounds(10, 60, 160, 40);

        this.add(btnLogin);
        btnLogin.setBounds(180, 10, 90, 40);

        this.add(btnReg);
        btnReg.setBounds(180, 60, 90, 40);

        this.add(statusMsg);
        statusMsg.setBounds(10, 110, 280, 20);

        // 窗口布局
        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtUserIdField.setToolTipText("账号");
        txtPasswordField.setToolTipText("密码");

        // 注册监听事件
        btnReg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String msg = Message.requestRegister(txtUserIdField.getText(), txtPasswordField.getText());
                Client.getInstance().send(msg);
            }
        });


        // 登录监听事件
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String msg = Message.requestLogin(txtUserIdField.getText(), txtPasswordField.getText());
                Client.getInstance().send(msg);
            }
        });
    }

    public JTextField getTxtUserIdField() { return txtUserIdField; }
    public JTextField getTxtPasswordField() { return txtPasswordField; }
    public JLabel getStatusMsg() { return statusMsg; }
}
