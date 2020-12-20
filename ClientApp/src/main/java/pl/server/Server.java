package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;


public class Server extends Thread {

    ServerSocket serverSocket;
    Set<OutputStream> clients= new HashSet<>();

    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);
        serverSocket.setSoTimeout(10000);
    }
    public void run() {
        while (true) {
            int players = 1;
            while(players < 3)
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();

                    System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    System.out.println(in.readUTF());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Thread t= new playerHandler(socket,out,in,players);
                    out.writeInt(players);
                    players++;
                    t.start();
                    //socket.close();

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
            server.start();
    }



    public class playerHandler extends Thread {


        Socket socket;
        DataInputStream inputStream;
        DataOutputStream outputStream;
        int id;



        playerHandler(Socket socket, DataOutputStream out, DataInputStream in,int id){
            this.id=id;
            this.socket=socket;
            this.outputStream=out;
            this.inputStream=in;
        }

        @Override
        public void run() {
            clients.add(outputStream);




        }
    }









}






