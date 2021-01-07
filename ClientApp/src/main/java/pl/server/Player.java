package pl.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Player {

    public Socket socket;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;
    public int playerID;

    public String fromServer;
    public String toServer;
    public int fromServerInt;
    public int toServerInt;

    public Player(Socket socket, DataInputStream input, DataOutputStream output, int id) {
        this.socket=socket;
        this.inputStream=input;
        this.outputStream=output;
        this.playerID=id;
    }




    public void getMessage(){
        while(true){
            try{
                fromServer=inputStream.readUTF();
                break;
            }
            catch (Exception e){

            }
        }

    }


    public void sendMessage(String message){
        try{
            toServer=message;
            outputStream.writeUTF(toServer);
        }
        catch (Exception e){

        }
    }

    public void sendIntMessage(int message){
        try{
            toServerInt=message;
            outputStream.writeInt(toServerInt);
        }
        catch (Exception e){

        }
    }



}
