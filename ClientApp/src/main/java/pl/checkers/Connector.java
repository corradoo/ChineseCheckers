package pl.checkers;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Connector {

    Socket socket = new Socket("127.0.0.1", 58000);
    DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
    DataInputStream fromServer = new DataInputStream(socket.getInputStream());

    public Connector() throws IOException {}

    public void send() throws IOException {
        toServer.writeUTF("Date");
    }

    public String getMessage() throws IOException {
        System.out.println(fromServer.readUTF());
        return "Responsed!";
    }

}
