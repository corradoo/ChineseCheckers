package pl.checkers.tests;

import org.junit.Test;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Test;
import pl.checkers.Board;
import pl.server.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class SocketConnectionTest {

    @Test
    public void socketTest() {
        String result="";
        int port=0;
        try{
            ServerSocket serverSocket= new ServerSocket(59000);
            Socket socket = new Socket("127.0.0.1", 59000);
            /*Socket clientSocket;
            clientSocket=serverSocket.accept();

             */
            result="SUKCES";
            //port=clientSocket.getPort();
        }
        catch (Exception e){
            result="BLAD";
            e.printStackTrace();
        }
        System.out.println("PORT: "+port);
        assertEquals(result,"SUKCES");


    }

    @Test
    public void playerConnectionTest() throws IOException {
        ServerSocket serverSocket= new ServerSocket(58000);
        Socket socket1= new Socket("localhost",58000);
        System.out.println("SOCKET1");
        Socket socket2= new Socket("localhost",58000);
        System.out.println("SOCKET");
        ArrayList<Player> players= new ArrayList<>();
        String message="czesc";
        serverSocket.accept();
        System.out.println("ACCEPT");
        DataInputStream dataInputStream= new DataInputStream(socket1.getInputStream());
        DataOutputStream dataOutputStream= new DataOutputStream(socket1.getOutputStream());
        DataInputStream dataInputStream2= new DataInputStream(socket2.getInputStream());
        DataOutputStream dataOutputStream2= new DataOutputStream(socket2.getOutputStream());


        Player player1= new Player(socket1,dataInputStream,dataOutputStream,0);
        Player player2= new Player(socket2,dataInputStream2,dataOutputStream2,1);
        players.add(player1);
        players.add(player2);
        player1.sendMessage(message);
        player2.sendMessage(message);
        System.out.println("SENT");
        assertEquals(player1.toServer,message);
        assertEquals(player2.toServer,message);



    }

}
