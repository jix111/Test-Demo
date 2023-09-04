package client.app;

import client.ui.LoginFrame;

public class ClientService {
    public static void main(String[] args) {
        LoginFrame.getInstance();
        System.out.println("Client Started!");
    }
}
