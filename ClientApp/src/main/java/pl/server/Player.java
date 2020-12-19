package pl.server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Player {
    private int playerID;
    private Socket socket;
    private DataInputStream fromPlayer;
    private DataOutputStream toPlayer;


    public Player(int ID, Socket s){
        this.playerID=ID;
        this.socket=s;

        try{
            this.fromPlayer= new DataInputStream(this.socket.getInputStream());
            this.toPlayer= new DataOutputStream(this.socket.getOutputStream());
        }
        catch (Exception e){

        }
    }

    public void sendData(String data){
        try{
            this.toPlayer.writeBytes(data);
        }
        catch (Exception e){
            System.out.println("sending: Player not found");
        }
    }

    public String receiveData(){

        try{
            String data=this.fromPlayer.readUTF();
            return data;
        }
        catch (Exception e){
            System.out.println("Waiting: No response from Player");
            return "blad";
        }
    }

    public void closeConnection(){
        try{
            this.socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean isOnline(){
        return this.socket.isConnected();
    }


}
