package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server extends Thread {

    ServerSocket serverSocket;
    private ArrayList<Player> players= new ArrayList<>();
    ServerBoard serverBoard;


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
        serverBoard = new ServerBoard(number);

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

        int playersCount;
        int[] playersTurn;
        int index=0;

        public void init(){
            playersCount=players.size();
            playersTurn = new int[playersCount];
            Random random= new Random();
            int starting= random.nextInt(playersCount)+1;
            playersTurn[0]=starting;
            for(int i=0; i<playersCount-1;i++){
                playersTurn[i+1]=((starting+i)%playersCount)+1;
            }

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
            boolean validMove;
            init();
            while (true){
                int winner=serverBoard.getWinner();
                if(winner!=0){
                    for(int i = 0; i< playersTurn.length; i++){
                        if(winner== playersTurn[i]) playersTurn[i]=0;
                    }
                }
                for(Player player: players){
                    if(player.playerID== playersTurn[index]){
                        index++;
                        index=index%playersCount;
                        int next = playersTurn[index];
                        if(next==0){
                            index++;
                            index=index%playersCount;
                            next= playersTurn[index];
                        }
                        validMove = false;
                        while(!validMove) {
                            player.getMessage();
                            String msg = player.fromServer;
                            if (serverBoard.validateMove(msg)) {
                                validMove = true;
                                player.sendMessage("ok");
                                for (Player p : players) {
                                    p.sendMessage(msg);
                                }
                            } else {
                                player.sendMessage("err");
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


