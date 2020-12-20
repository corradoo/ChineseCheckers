package pl.server;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

public class Server {

    public static Set<DataOutputStream> writers= new HashSet<>();



    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(58000);


        while(true) {
            Socket s = null;
            var pool= Executors.newFixedThreadPool(20);
            try {
                // socket object to receive incoming client requests
                s = ss.accept();
                //DataInputStream dis = new DataInputStream(s.getInputStream());
                //DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams


                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler client= new ClientHandler(s);
                pool.execute(client);
                //Thread t = new ClientHandler(s);
                // Invoking the start() method
                //t.start();
            } catch (Exception e) {
                assert s != null;
                s.close();
                e.printStackTrace();
            }

        }


    }






    private static class ClientHandler extends Thread
    {

        DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
        private DataInputStream dis;
        private DataOutputStream dos;
        private final Socket s;


        // Constructor
        public ClientHandler(Socket s)
        {
            this.s = s;

        }

        @Override
        public void run()
        {
            String received;
            String toreturn;

            try{
                dos= new DataOutputStream(s.getOutputStream());
                dis= new DataInputStream(s.getInputStream());
                // Ask user what he wants
                dos.writeUTF("What do you want?[Date | Time]..\n"+
                        "Type Exit to terminate connection.");

            }
            catch (Exception e){
                e.printStackTrace();
            }
            writers.add(dos);
            while (true)
            {
                try {

                    // receive the answer from client
                    received = dis.readUTF();


                    if(received.equals("Exit"))
                    {
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    // creating Date object
                    Date date = new Date();




                    // write on output stream based on the
                    // answer from the client
                    switch (received) {

                        case "Date" :
                            int i=0;
                            toreturn = fordate.format(date);
                            for(DataOutputStream os: writers){
                                os.writeUTF(toreturn);
                                i++;
                                System.out.println("Requested date "+i+ "size: "+writers.size());

                            }

                            break;

                        case "Time" :
                            toreturn = fortime.format(date);
                            dos.writeUTF(toreturn);
                            break;

                        default:
                            dos.writeUTF("Invalid input");
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

            try
            {
                // closing resources
                this.dis.close();
                this.dos.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }



}



