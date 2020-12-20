package pl.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

public class Server extends Thread {

    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);
    }

    public void run() {
        while (true) {
            Set<Socket> sockets = new HashSet<>();
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
                    sockets.add(socket);

                } catch (SocketTimeoutException s) {
                    System.out.println("Socket timed out!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                GameHandler gameHandler = new GameHandler(sockets);
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

    class GameHandler {
        Set<Socket> sockets;

        public GameHandler(Set<Socket> sockets) {
            this.sockets = sockets;
        }
    }
}



