package pl.checkers;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        socket = new Socket("192.168.8.101", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        board = new Board();
        playerInfo = new Text();
        serverInfo = new Text();
        start();
    }

    @Override
    public void run() {
        int playerTurn;
        try {
            initServerMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OUTER: while (true) {

            try {
                System.out.println("Czekam na kolejke");
                playerTurn = fromServer.readInt();
                System.out.println("juz wiem! "+playerTurn);
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
                            serverInfo.setText("Not your turn");
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
        System.out.println("Siema tu serwer, jestes graczem nr:" + intFromServer);
        playerInfo.setText("PLAYER "+intFromServer);
        board.currentPlayer = intFromServer;

    }



    private void setTurn(int currentPlayer) {
        board.yourTurn = board.currentPlayer == currentPlayer;
    }


    public ArrayList<Circle> getBoard() {
        return this.board.circles;
    }
}
