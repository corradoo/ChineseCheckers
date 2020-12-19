package pl.server;

import java.net.Socket;

public class SessionHandler {

    private Player player1;
    private Player player2;

    public SessionHandler(Socket p1, Socket p2){
        this.player1=new Player(1,p1);
        this.player2=new Player(2,p2);
    }




















}
