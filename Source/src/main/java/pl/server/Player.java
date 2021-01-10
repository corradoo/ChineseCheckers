package pl.server;

import java.io.*;
import java.net.Socket;

public class Player {

    public Socket socket;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;
    public int playerID;

    public String fromServer;
    public String toServer;
    public int toServerInt;

    /**
     * Konstruktor tworzący nowego gracza
     *
     * @param socket Socket danego gracza
     * @param input Strumień wejścia danego gracza
     * @param output Strumień wyjścia danego gracza
     * @param id Numer danego gracza
     */
    public Player(Socket socket, DataInputStream input, DataOutputStream output, int id) {
        this.socket=socket;
        this.inputStream=input;
        this.outputStream=output;
        this.playerID=id;
    }


    /**
     * Pobiera wiadomość ze strumienia wejścia
     */
    public void getMessage(){
        while(true){
            try{
                fromServer=inputStream.readUTF();
                break;
            }
            catch (Exception e){
                System.out.println("Failed to read message");
            }
        }

    }

    /**
     * Wysyła wiadomość na strumień wyjścia
     * @param message Wiadomość w formacie UTF
     */
    public void sendMessage(String message){
        try{
            toServer=message;
            outputStream.writeUTF(toServer);
        }
        catch (Exception e){
            System.out.println("Failed to write message");
        }
    }

    /**
     * Wysyła wiadomość na strumień wyjścia
     * @param message Wiadomość w formacie INT
     */
    public void sendIntMessage(int message){
        try{
            toServerInt=message;
            outputStream.writeInt(toServerInt);
        }
        catch (Exception e){
            System.out.println("Failed to write message");
        }
    }



}
