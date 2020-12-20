package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server extends Thread {

    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);
        serverSocket.setSoTimeout(10000);
    }
    public void run() {
        while (true) {
            int players = 2;
            while(players > 0)
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();

                    System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    System.out.println(in.readUTF());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    out.writeInt(players);
                    players--;
                    socket.close();

                } catch (SocketTimeoutException s) {
                    System.out.println("Socket timed out!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
        }
    }
    public static void main(String[] args) throws IOException {
            Thread server = null;
            try {
                server = new Server();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.run();
    }
}



