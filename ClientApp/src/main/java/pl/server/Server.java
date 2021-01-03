package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;


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
        System.out.println("Podaj liczbe graczy");
        Scanner scanner= new Scanner(System.in);

        int number= scanner.nextInt();
        if(number<=0 || number>6){
            System.out.println("Nieprawidlowa ilosc graczy, prosze podac liczbe z zakresu [1,6]");
            number=scanner.nextInt();
        }
        System.out.println("Ilosc graczy: "+number);

        while (true) {


            if(player>number&&counter==0){
                System.out.println("START");
                sessionHandler= new SessionHandler();
                sessionHandler.start();
                counter++;
            }

            while(player <=number) {
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();


                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    out.writeInt(number);
                    out.writeInt(player);


                    Player p= new Player(socket,in,out, player);
                    players.add(p);
                    player++;
                    //System.out.println(player+" | "+ counter+" | "+number);


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

        int[] table= new int[10];
        int playersCount;
        int index=0;
//index%playersCount +1
        public void init(){
            playersCount=players.size();
            //System.out.println("PC: "+playersCount);
            Random random= new Random();
            int starting= random.nextInt(playersCount)+1;
            //System.out.println(starting);
            table[0]=starting;
            for(int i=0; i<playersCount;i++){
                table[i+1]=((starting+i)%playersCount)+1;
            }
            /*System.out.println("TABLE 0 :"+table[0]);
            System.out.println("TABLE 1 :"+table[1]);
            System.out.println("TABLE 2 :"+table[2]);

             */

            try{
                for(Player player: players){
                    player.outputStream.writeInt(starting);
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

                    if(player.playerID==table[index]){
                        index++;
                        index=index%playersCount;
                        player.getMessage();
                        String msg= player.fromServer;
                        int next= table[index];
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
}


