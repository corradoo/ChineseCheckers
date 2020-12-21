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
                    clients.add(out);

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
        int movingPlayer;
        int movingField;
        int movingIndex;


        playerHandler(Socket socket, DataOutputStream out, DataInputStream in,int player){
            this.player=player;
            this.socket=socket;
            this.outputStream=out;
            this.inputStream=in;
            this.nextPlayer=player%2 +1;

        }

        @Override
        public void run() {
            try {
                outputStream.writeInt(3);
                System.out.println("wyslano");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("blad");
            }
            try{
                outputStream.writeInt(2);
                outputStream.writeInt(90);
                System.out.println("przeszlo move");
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("blad move");
            }

            /*for(OutputStream outputStream : clients){
                try{
                    outputStream.write(2);
                    outputStream.write(90);
                    System.out.println("przeszlo move");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

             */


            /*while (true){
                try{
                    movingPlayer=inputStream.readInt();
                    movingIndex

                }
                catch (Exception e){

                }



            }

             */


        }
    }









}






