package pl.checkers;

import javafx.scene.control.Button;
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
    Button skipButton = new Button();
    int playerTurn;

    Game() throws IOException {
        socket = new Socket("127.0.0.1", 58000);
        toServer = new DataOutputStream(socket.getOutputStream());
        fromServer = new DataInputStream(socket.getInputStream());
        playerInfo = new Text();
        serverInfo = new Text();
        skipButton.setText("Skip");
        skipButton.setOnAction( event -> {
            try {
                skipMove();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        skipButton.setDisable(true);

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
    /** Wysyła wiadomośc o zakończeniu kolejki do serwera*/
    private void skipMove() throws IOException {
        toServer.writeUTF("skip");
        skipButton.setDisable(true);
        unlock();
    }
    /** Wysyła zapytanie o ruch do serwera*/
    public void requestMove() throws IOException {
        toServer.writeUTF(board.jumpIndex + " " + board.startIndex);
        System.out.println(board.movingPlayer + " " + board.jumpIndex + " " + board.startIndex);
    }
    /** Dekoduje wiadomość od serwera*/
    private void decodeResponseFromServer() throws IOException {
        String string = fromServer.readUTF();

        //Jeżeli ktoś pominął, nie rób nic
        if(string.equals("skip")) return;

        //Jeżeli ruch, zdekoduj i wykonaj na planszy
        String[] arr= string.split(" ");
        int index= Integer.parseInt(arr[0]);
        int currentField=Integer.parseInt(arr[1]);
        System.out.println(index + " " + currentField);
        board.move(playerTurn, index, currentField);
        board.moved=false;
    }

    @Override
    public void run() {

        while (true) {

            try {
                System.out.println("Czekam na kolejke");

                //Odbiera wiadomość od serwera czyja jest kolej
                playerTurn = fromServer.readInt();
                System.out.println("juz wiem! " + playerTurn);

                //Ustawia czyj jest ruch
                setTurn(playerTurn);

                //Jeżeli nie jest kolej tego klienta nasłuchuje ruchu
                if(!board.yourTurn){
                    skipButton.setDisable(true);
                    board.jump = false;
                    System.out.println("Not your turn");
                    serverInfo.setText("Not Your Turn");
                    decodeResponseFromServer();
                }

                //W przeciwnym wypadku może wysłać ruch lub pominąc kolejke
                else {
                    skipButton.setDisable(false);
                    if(board.jump) serverInfo.setText("Jumping mode");
                    else serverInfo.setText("Your Turn");
                    while(true) {
                        System.out.println("wysylanie");

                        lock();
                        String feedbackFromServer = fromServer.readUTF();
                        if(feedbackFromServer.equals("correctMove") || feedbackFromServer.equals("skip") ) {
                            decodeResponseFromServer();
                            skipButton.setDisable(true);
                            break;
                        }
                        if(feedbackFromServer.equals("jumped")) {
                            board.jump = true;
                            decodeResponseFromServer();
                            board.currentFieldNr = board.jumpIndex;
                            board.showMoves();
                            break;
                        }
                    }

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
