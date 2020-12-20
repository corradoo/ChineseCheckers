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
    }

    public void send() throws IOException {
        toServer.writeUTF("Time");
    }

    public String getMessage() throws IOException {
        System.out.println(fromServer.readUTF());
        return "Time received from server:\n" + fromServer.readUTF();
    }
    //Pobira ID gracza z serwera
    public void getPlayerNr() {

    }
}
