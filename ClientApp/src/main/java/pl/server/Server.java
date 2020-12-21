package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Server extends Thread {

    ServerSocket serverSocket;
    Set<OutputStream> clients= new HashSet<>();


    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);

    }
    public void run() {
        int player = 1;
        int counter=0;
        while (true) {

            while(player <3) {
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();

                    System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    System.out.println(in.readUTF());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Thread t = new playerHandler(socket, out, in, player);
                    out.writeInt(player);
                    t.start();
                    player++;

                    //socket.close();

                }
                catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            if(counter==0){
                try{
                    System.out.println(clients.size());
                    for(OutputStream os : clients){
                        os.write(1);
                        counter++;

                    }

                }
                catch (Exception e){
                    e.printStackTrace();

                }

            }

                
        }




    }
    public static void main(String[] args){
            Thread server;
            try {
                server = new Server();
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }



    public class playerHandler extends Thread {


        Socket socket;
        DataInputStream inputStream;
        DataOutputStream outputStream;
        int player;
        int nextPlayer;



        playerHandler(Socket socket, DataOutputStream out, DataInputStream in,int player){
            this.player=player;
            this.socket=socket;
            this.outputStream=out;
            this.inputStream=in;
            this.nextPlayer=player%2 +1;
        }

        @Override
        public void run() {
            clients.add(outputStream);


        }
    }









}






