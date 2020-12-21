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
import java.util.Scanner;
import java.util.Set;


public class Server extends Thread {

    ServerSocket serverSocket;
    private ArrayList<Player> players= new ArrayList<>();


    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);

    }
    public void run() {
        int player = 1;
        int counter=0;
        SessionHandler sessionHandler;

        while (true) {

            if(player==3&&counter==0){
                sessionHandler= new SessionHandler();
                sessionHandler.start();
                counter++;
            }
            while(player <3) {
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();


                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeInt(player);

                    Player p= new Player(socket,in,out, player);
                    players.add(p);
                    player++;

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


    public class SessionHandler extends Thread{


        public void init(){
            try{
                for(Player player: players){
                    player.outputStream.writeInt(1);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }



        @Override
        public void run() {
            init();
            while (true){


                for(Player player: players){

                    player.getMessage();
                    String msg= player.fromServer;
                    int next= player.playerID%3 +1;
                    int id=player.playerID;
                    for(Player p : players){
                        if(p.playerID!=id){
                            p.sendMessage(msg);
                        }
                    }
                    for(Player p: players){
                        try {
                            p.outputStream.writeInt(next);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }



                }


            }


        }

    }
}


