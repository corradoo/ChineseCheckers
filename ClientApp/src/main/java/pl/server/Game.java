package pl.server;

import javafx.scene.shape.Circle;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    Player currentPlayer;

    class Player implements Runnable{

        //private Player[] board=new Player[61];


        String playerName;
        Socket socket;
        Scanner input;
        PrintWriter output;
        Player player1;
        Player player2;
        /*Player player3;
        Player player4;
        Player player5;
        Player player6;


         */
        Player nextPlayer;
        /*InputStream inputStream;
        ObjectInputStream objectInputStream;
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;
        ArrayList<Circle> movingCircles= new ArrayList<>();

         */



        private Board board;

        boolean canMove;

        Player(Socket socket,String name){
            this.socket=socket;
            this.playerName=name;
        }


        public void getMessage(String string){
            /*inputStream= socket.getInputStream();
            objectInputStream= new ObjectInputStream(inputStream);
            movingCircles=(ArrayList<Circle>) objectInputStream.readObject();


             */
            String[] str= string.split(" ");

            double cX= Double.parseDouble(str[0]);
            double cY= Double.parseDouble(str[1]);
            double chX= Double.parseDouble(str[2]);
            double chY= Double.parseDouble(str[3]);

            output.println("MOVECH ");

        }


        public synchronized void move(int location, Player player){
            if(player!=currentPlayer){
                throw new IllegalStateException("Not your turn");
            }
            else if(player.player2==null /*| player.player3==null |player.player4==null |player.player5==null |player.player6==null*/){
                throw new IllegalStateException("Wait for more players");
            }
            /*else if(board[location]!=null){
                throw new IllegalStateException("Can't move here");
            }

             */
            //board[location]=currentPlayer;
            currentPlayer=currentPlayer.nextPlayer;

        }


        @Override
        public void run() {
            System.out.println("Połaczono: "+playerName);
            try{
                setup();
                processCommands();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    socket.close();
                }
                catch (IOException e){

                }
            }
        }

        private void setup() throws IOException{
            input=new Scanner(socket.getInputStream());
            output= new PrintWriter(socket.getOutputStream(),true);
            output.println("Welcome "+playerName);
            switch (playerName) {
                case "Player1":
                    currentPlayer = this;
                    this.nextPlayer=player2;
                    output.println("MESSAGE Waiting for other players");
                    break;
                case "Player2":
                    player2 = currentPlayer;
                    player2.nextPlayer=player1;
                    output.println("MESSAGE Player 2 joined");
                    break;
                /*case "Player3":
                    player3 = currentPlayer;
                    player3.nextPlayer=player4;
                    output.println("Player 3 joined");
                    break;
                case "Player4":
                    player4 = currentPlayer;
                    player4.nextPlayer=player5;
                    output.println("Player 4 joined");
                    break;
                case "Player5":
                    player5 = currentPlayer;
                    player5.nextPlayer=player6;
                    output.println("Player 5 joined");
                    break;
                case "Player6":
                    player6 = currentPlayer;
                    player6.nextPlayer=player1;
                    output.println("Player 6 joined");
                    break;

                 */
            }


        }

        /*private void processObject() throws Exception{
            System.out.println("Start");
            outputStream=socket.getOutputStream();
            objectOutputStream.writeObject(movingCircles);
            System.out.println("Przyjelo kulka");
        }

         */


        private void processCommands() throws Exception {
            System.out.println("process commands");
            while (input.hasNextLine()){
                System.out.println("w while");
                var command= input.nextLine();
                /*if(command.startsWith("QUIT")){
                    return;
                }
                else if(command.startsWith("MOVECH")){

                 */
                    //getMessage(command.substring(7));
                    System.out.println("w movech");
                    System.out.println(command);
                    output.println(command);
                    //processObject();
               // }

            }
        }

        private void processMoveCommand(int location){
            try{
                move(location,this);
                output.println("VALID_MOVE");
                nextPlayer.output.println("PLAYER_MOVED "+location);
            }
            catch (IllegalStateException e){
                output.println("MESSAGE "+e.getMessage());
            }

        }


    }

}

