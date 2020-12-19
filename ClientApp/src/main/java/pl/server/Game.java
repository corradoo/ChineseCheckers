package pl.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Player currentPlayer;

    class Player implements Runnable{

        private Player[] board=new Player[61];


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

        boolean canMove;

        Player(Socket socket,String name){
            this.socket=socket;
            this.playerName=name;
        }

        public synchronized void move(int location, Player player){
            if(player!=currentPlayer){
                throw new IllegalStateException("Not your turn");
            }
            else if(player.player2==null /*| player.player3==null |player.player4==null |player.player5==null |player.player6==null*/){
                throw new IllegalStateException("Wait for more players");
            }
            else if(board[location]!=null){
                throw new IllegalStateException("Can't move here");
            }
            board[location]=currentPlayer;
            currentPlayer=currentPlayer.nextPlayer;

        }


        @Override
        public void run() {
            System.out.println("Połaczono: "+socket);
            try{
                setup();
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
                    output.println("Waiting for other players");
                    break;
                case "Player2":
                    player2 = currentPlayer;
                    player2.nextPlayer=player1;
                    output.println("Player 2 joined");
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

        private void processCommands(){

        }


    }

}

