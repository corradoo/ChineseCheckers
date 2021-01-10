package pl.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server extends Thread {

    ServerSocket serverSocket;
    private ArrayList<Player> players;
    ServerBoard serverBoard;

    /**
     * Ustawia socket serwera
     * @throws IOException
     */
    public Server() throws IOException {
        serverSocket = new ServerSocket(58000);

    }

    /**
     * Dodaje nowych graczy do ArrayList i tworzy obiekty klasy Player
     */
    public void run() {
        int player = 1;
        int counter = 0;
        SessionHandler sessionHandler;
        System.out.println("Podaj liczbe graczy");
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        if (number <= 0 || number > 6) {
            System.out.println("Nieprawidlowa ilosc graczy, prosze podac liczbe z zakresu [1,6]");
            number = scanner.nextInt();
        }
        players = new ArrayList<>(number);
        System.out.println("Ilosc graczy: " + number);
        serverBoard = new ServerBoard(number);

        while (true) {


            if (player > number && counter == 0) {
                System.out.println("START");
                sessionHandler = new SessionHandler();
                sessionHandler.start();
                counter++;
            }

            while (player <= number) {
                try {
                    System.out.println("Waiting for clients on port:" + serverSocket.getLocalPort());
                    Socket socket = serverSocket.accept();


                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    out.writeInt(number);
                    out.writeInt(player);


                    Player p = new Player(socket, in, out, player);
                    players.add(p);
                    player++;

                } catch (IOException e) {
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

        int playersCount; //Ilośc graczy
        int[] playersTurn; //Lista graczy którzy dalej grają w kolejności
        int movingPlayerIndex=0;
        int current;
        int winner=0;
        int numberOfWinners=0;
        boolean gameOver=false;

        /**
         * Funkcje wywoływane przy tworzeniu nowej instancji SessionHandler
         */
        public void init(){
            playersCount=players.size();
            playersTurn = new int[playersCount];
            Random random= new Random();
            int starting= random.nextInt(playersCount)+1;
            playersTurn[0]=starting;
            for(int i=0; i<playersCount-1;i++){
                playersTurn[i+1]=((starting+i)%playersCount)+1;
            }
            current = starting;
            writeStartingPlayer(starting);
        }

        @Override
        public void run() {
            boolean validMove;

            init();
            while (true){

                if(gameOver){
                    try{
                        serverSocket.close();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }

                for(Player player: players){
                    if(player.playerID== playersTurn[movingPlayerIndex]){
                        System.out.println("TURN: "+playersTurn[movingPlayerIndex]);
                        //Ustawia gracza który się porusza
                        int next=setMovingPlayer();

                        if(next==0){
                            while(next==0){
                                next=setMovingPlayer();
                            }
                        }
                        validMove = false;
                        serverBoard.jumped = false;

                        while(!validMove) {
                            player.getMessage();
                            String msg = player.fromServer;
                            //Jeżeli gracz pominął kolejkę wyślij msg o pominięciu
                            if(msg.equals("skip")) {
                                validMove = true; //Pominięcie jest poprawnym ruchem
                                System.out.println("Player " + playersTurn[movingPlayerIndex] + " skipped move");
                                player.sendMessage("skip");
                                checkWinner();
                                for (Player p : players) {
                                    p.sendMessage(msg);
                                    checkWinConditions(p);
                                }
                            }
                            //Sprawdź poprawnośc ruchu
                            else if (serverBoard.validateMove(msg)) {
                                System.out.println("Validating move...");

                                validMove = true;
                                if(serverBoard.jumped) {
                                    player.sendMessage("jumped");
                                } else {
                                    player.sendMessage("correctMove");
                                }
                                checkWinner();
                                for (Player p : players) {
                                    p.sendMessage(msg);
                                    checkWinConditions(p);
                                }
                                //Tryb skoków
                                if(serverBoard.jumped && serverBoard.nextJumpPossible()) {
                                    //Dopóki jest możliwy kolejny skok


                                    sendMessageInt(current);
                                    while(serverBoard.nextJumpPossible()) {
                                        player.getMessage();
                                        String nextMsg = player.fromServer;
                                        if(nextMsg.equals("skip")) {
                                            System.out.println("Player " + playersTurn[movingPlayerIndex] + " skipped move");
                                            player.sendMessage("skip");

                                            for (Player p : players) {
                                                p.sendMessage(nextMsg);
                                                checkWinConditions(p);
                                            }
                                            break;
                                        }

                                        //Sprawdza poprawność skoku
                                        System.out.println("Validating jump move...");
                                        if(serverBoard.validateJump(nextMsg)) {
                                            System.out.println("Correct jump move");
                                            player.sendMessage("jumped");
                                            checkWinner();
                                            for (Player p : players) {
                                                p.sendMessage(nextMsg);
                                                checkWinConditions(p);
                                            }
                                            if(serverBoard.nextJumpPossible()) sendMessageInt(current);
                                        } else {
                                            System.out.println("Incorrect jump move");
                                            player.sendMessage("err");
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage("err");
                            }
                        }
                        current = next;
                        sendMessageInt(next);
                        checkWinner();

                    }
                }
            }
        }
        /** Wysyła wiadomośc o kolejce do wszystkich graczy*/
        public void sendMessageInt(int next){
            for(Player p: players){
                try {
                    p.outputStream.writeInt(next);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Sprawdza warunki zwycięstwa i warunek końca gry
         * @param p Gracz do którego wysyła wiadomość po sprawdzeniu warunków
         */
        public void checkWinConditions(Player p){
            if(winner!=0){
                p.sendMessage("winner");
                p.sendIntMessage(winner);
                System.out.println("Skonczyl gracz : "+winner);
            }
            else{
                p.sendMessage("nothing");
            }
            if(numberOfWinners==playersCount-1){
                p.sendMessage("KONIEC");
                System.out.println("KONIEC GRY");
                gameOver=true;
            }
            else{
                p.sendMessage("DALEJ");

            }

        }

        /**
         * Sprawdza czy ktoś już skończył grę
         */
        public void checkWinner(){
            winner=serverBoard.getWinner();
            if(winner!=0){
                for(int i = 0; i< playersTurn.length; i++){
                    if(winner == playersTurn[i]) {
                        playersTurn[i]=0;
                        System.out.println("Player " + current + " has finished");
                        numberOfWinners++;
                    }
                }
            }
        }

        /**
         * Ustawia gracza który teraz wykona ruch
         * @return Zwraca numer gracza który teraz wykona ruch
         */
        public int setMovingPlayer(){
            movingPlayerIndex++;
            movingPlayerIndex=movingPlayerIndex%playersCount;
            return playersTurn[movingPlayerIndex];
        }

        /**
         * Wysyła wiadomość do wszystkich graczy o numerze gracza która zaczyna rozgrywkę
         * @param starting ID gracza który rozpoczyna
         */
        public void writeStartingPlayer(int starting){
            try{
                for(Player player: players){
                    player.outputStream.writeInt(starting);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }





    }


}


