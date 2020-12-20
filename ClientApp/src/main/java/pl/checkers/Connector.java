package pl.checkers;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Connector {

    Socket socket;
    DataOutputStream toServer;
    DataInputStream fromServer;
    int playerNr = 1;

    public Connector() throws IOException {
        socket =new Socket("127.0.0.1", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        chat();
    }

    public void chat() throws IOException {
        toServer.writeUTF("Czesc tutaj: " + socket.getLocalSocketAddress());
        int intFromServer = fromServer.readInt();
        System.out.println("Siema tu serwer, jesteś graczem nr:" + intFromServer);
        playerNr = intFromServer;
        socket.close();
    }

}
