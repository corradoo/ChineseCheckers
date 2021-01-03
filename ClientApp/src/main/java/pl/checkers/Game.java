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
        socket = new Socket("127.0.0.1", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        playerInfo = new Text();
        serverInfo = new Text();

        int boardSize;
        try {
            boardSize=fromServer.readInt();
            board = new Board(boardSize, this);
            initServerMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        int playerTurn;

        while (true) {

            try {
                System.out.println("Czekam na kolejke");
                playerTurn = fromServer.readInt();
                System.out.println("juz wiem! " + playerTurn);

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
                    while(true) {
                        System.out.println("wysylanie");
                        serverInfo.setText("Your Turn");
                        lock();
                        toServer.writeUTF(board.jumpIndex + " " + board.startIndex);
                        System.out.println(board.movingPlayer + " " + board.jumpIndex + " " + board.startIndex);
                        if(fromServer.readUTF().equals("ok")) break;
                    }
                    System.out.println("DONE");
                    String string=fromServer.readUTF();
                    String[] arr= string.split(" ");
                    int index= Integer.parseInt(arr[0]);
                    int currentField=Integer.parseInt(arr[1]);
                    System.out.println(index + " " + currentField);
                    board.move(playerTurn, index, currentField);
                    board.moved=false;
                    serverInfo.setText("Not Your Turn");
                }


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void lock()
            throws InterruptedException{
        while(!board.moved){
            wait();
        }
        board.moved = false;
    }

    public synchronized void unlock(){
        board.moved = true;
        notify();
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
