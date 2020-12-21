package pl.checkers;

import javafx.scene.shape.Circle;

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


    Game() throws IOException {
        socket = new Socket("127.0.0.1", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        board = new Board();
        initServerMessage();
        start();
    }

    @Override
    public void run() {
        int playerTurn;
        String response;

        while (true) {

            try {
                playerTurn = fromServer.readInt();
                System.out.println(playerTurn);
                setTurn(playerTurn);
                //if(!YourTurn){

                int index = fromServer.readInt();
                int currentField = fromServer.readInt();
                System.out.println(index + " " + currentField);
                board.move(playerTurn, index, currentField);

                //}
                /*else if(movingIndex!=0 && movingField!=0 && movingPlayer!= 0) {
                    toServer.writeInt(movingPlayer);
                    toServer.writeInt(movingIndex);
                    toServer.writeInt(movingField);
                    resetMoving();

                }

                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initServerMessage() throws IOException {
        toServer.writeUTF("Czesc tutaj: " + socket.getLocalSocketAddress());
        int intFromServer = fromServer.readInt();
        System.out.println("Siema tu serwer, jesteś graczem nr:" + intFromServer);
        board.currentPlayer = intFromServer;

    }



    private void setTurn(int currentPlayer) {
        board.yourTurn = board.currentPlayer == currentPlayer;
    }


    public ArrayList<Circle> getBoard() {
        return this.board.circles;
    }
}
