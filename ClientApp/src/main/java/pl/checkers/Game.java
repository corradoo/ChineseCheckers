package pl.checkers;

import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Game extends Thread {
    Board board;
    Socket socket;
    DataOutputStream toServer;
    DataInputStream fromServer;
    Text playerInfo ;
    Text serverInfo ;


    Game() throws IOException {
        socket = new Socket("127.0.0.1", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        board = new Board();
        playerInfo = new Text();
        serverInfo = new Text();
        initServerMessage();
        start();
    }

    @Override
    public void run() {
        int playerTurn;
        int boardSize;

        OUTER: while (true) {

            try {
                System.out.println("Czekam na kolejke");
                playerTurn = fromServer.readInt();
                System.out.println("juz wiem! "+playerTurn);
                /**TUTAJ przyjmuje ilu jest graczy **/
                //boardSize=fromServer.readInt();
                setTurn(playerTurn);
                if(!board.yourTurn){

                    System.out.println("not your turn");
                    String string=fromServer.readUTF();
                    String[] arr= string.split(" ");
                    int index= Integer.parseInt(arr[0]);
                    int currentField=Integer.parseInt(arr[1]);
                    System.out.println(index + " " + currentField);
                    board.move(playerTurn, index, currentField);
                    board.moved=false;

                }
                else  {
                    System.out.println("wysylanie");
                    serverInfo.setText("Your Turn");
                    INNER: while (true){

                        System.out.printf("");
                        if(board.moved){
                            toServer.writeUTF(board.movingIndex+" "+board.movingField);
                            System.out.println(board.movingPlayer+" "+board.movingIndex+" "+board.movingField);
                            System.out.println("DONE");
                            serverInfo.setText("Not Your Turn");
                            break INNER;
                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();


            }
        }
    }

    public void initServerMessage() throws IOException {
        int intFromServer = fromServer.readInt();
        System.out.println("Siema tu serwer, jesteś graczem nr:" + intFromServer);
        board.currentPlayer = intFromServer;
        playerInfo.setText("Player" + intFromServer);

    }



    private void setTurn(int currentPlayer) {
        board.yourTurn = board.currentPlayer == currentPlayer;
    }


    public ArrayList<Circle> getBoard() {
        return this.board.circles;
    }
}
