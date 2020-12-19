package pl.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connect {

    Socket socket;
    Scanner in;
    PrintWriter out;


    public Connect(String serverAddress) throws Exception {

        this.socket = new Socket(serverAddress, 58000);
        this.in= new Scanner(socket.getInputStream());
        this.out= new PrintWriter(socket.getOutputStream(),true);



    }

}
