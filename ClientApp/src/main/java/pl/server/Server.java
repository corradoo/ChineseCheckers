package pl.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58000)) {
            System.out.println("Serwer uruchomiony");
            var pool = Executors.newFixedThreadPool(50);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(),"Player1"));
                pool.execute(game.new Player(listener.accept(),"Player2"));
                /*pool.execute(game.new Player(listener.accept(),"Player3"));
                pool.execute(game.new Player(listener.accept(),"Player4"));
                pool.execute(game.new Player(listener.accept(),"Player5"));
                pool.execute(game.new Player(listener.accept(),"Player6"));

                 */

            }


        }
    }

}






