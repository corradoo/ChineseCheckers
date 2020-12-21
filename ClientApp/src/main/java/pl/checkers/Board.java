package pl.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Board extends Thread {

    public ArrayList<Field> fields = new ArrayList<>();
    public ArrayList<Circle> circles = new ArrayList<>();
    int gapX = 20,gapY = 17;
    int oddRowX = 10;
    int radius = 9;
    double scale = 3;
    int currentFieldNr = -1;
    int currentPlayer;
    boolean YourTurn = false;

    int movingField;
    int movingPlayer;
    int movingIndex;



    Socket socket;
    DataOutputStream toServer;
    DataInputStream fromServer;

    public Board() throws IOException {

        //this.currentPlayer = player;
        drawBoard();
        drawBase(8,1,4,false,1);
        drawBase(4,10,4,false,5);
        drawBase(4+9,10,4,false,3);

        drawBase(4+9,8,4,true,2);
        drawBase(4,8,4,true,6);
        drawBase(8,17,4,true,4);
        makeCircles();
        socket= new Socket("127.0.0.1",58000);
        toServer= new DataOutputStream(socket.getOutputStream());
        fromServer= new DataInputStream(socket.getInputStream());
        chat();
        start();


    }


    @Override
    public void run() {
        int playerTurn;
        String response;

        while(true){
            try {
                playerTurn=fromServer.readInt();
                setTurn(playerTurn);
                if(!YourTurn){
                    try{
                        int index= fromServer.readInt();
                        int currentField= fromServer.readInt();
                        move(playerTurn, index, currentField);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else if(movingIndex!=0 && movingField!=0 && movingPlayer!= 0) {
                    toServer.writeInt(movingPlayer);
                    toServer.writeInt(movingIndex);
                    toServer.writeInt(movingField);
                    resetMoving();

                }



            } catch (IOException e) {
                e.printStackTrace();
            }




        }




    }

    public void resetMoving(){
        movingPlayer=0;
        movingIndex=0;
        movingField=0;
    }

    public void setMoving(int index, int player, int field){
        this.movingField=field;
        this.movingIndex=index;
        this.movingPlayer=player;
    }

    public void chat() throws IOException{
        toServer.writeUTF("Czesc tutaj: " + socket.getLocalSocketAddress());
        int intFromServer = fromServer.readInt();
        System.out.println("Siema tu serwer, jesteś graczem nr:" + intFromServer);
        currentPlayer = intFromServer;
        setTurn(1);
    }

    private void setTurn(int currentPlayer){
        YourTurn= this.currentPlayer == currentPlayer;
    }


    private void makeCircles() {
        for(Field f : fields) {
            Circle c = new Circle(f.x*scale,f.y*scale,radius*scale);
            c.setFill(getPlayerColor(f.player));
            c.setStroke(Color.BLACK);
            c.setOnMouseClicked((e -> handleMouseClick(c)));
            circles.add(c);
        }
    }

    private void handleMouseClick(Circle c) {
            for(Field f: fields) {
                double fX = c.getCenterX()/scale;
                double fY = c.getCenterY()/scale;

                if( fX == f.x && fY == f.y && f.player == currentPlayer) {
                    currentFieldNr = fields.indexOf(f);
                    showMoves();
                }
                if(currentFieldNr > 0 && fX == f.x && fY == f.y && f.player == 0 && YourTurn){

                    setMoving(fields.indexOf(f),currentPlayer,currentFieldNr);
                    move(currentPlayer,fields.indexOf(f), currentFieldNr);

                }
            }

    }

    private void move( int player,int jumpTo, int currentField) {
        fields.get(jumpTo).setPlayer(player);
        fields.get(currentField).player = 0;
        currentFieldNr = -1;
        hideMoves();
        updateCircles();
    }



    private void showMoves() {
        for (Circle c: circles) {
            double moveDist = calculateDist(circles.get(1),circles.get(2));
            if(calculateDist(c,circles.get(currentFieldNr)) <= moveDist && fields.get(circles.indexOf(c)).player == 0)
                c.setStroke(Color.ORANGERED);
        }
    }

    private void hideMoves() {
        for (Circle c: circles) {
            c.setStroke(Color.BLACK);

        }
    }

    private void updateCircles() {
        for(Circle c : circles) {
            c.setFill(getPlayerColor(fields.get(circles.indexOf(c)).player));
        }
    }
    private Color getPlayerColor(int player) {
        switch(player) {
            case 1:
                return Color.BLUEVIOLET;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.INDIANRED;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.CADETBLUE;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Funkcja tworząca promienie na planszy
     */
    public void drawBase( int beginX,int beginY,int h, boolean invert, int player) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY, player);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player);
                    }

                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY,player);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player);
                    }
                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }



    /**
     * Tworzenie planszy bez baz
     */
    public void drawBoard() {

        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Field f;
                if (i % 2 == 0) {
                    f = new Field(j * gapX, i * gapY,0);
                } else {
                    f = new Field(j * gapX + oddRowX, i * gapY,0);
                }
                fields.add(f);
            }

            if (i >= middleY) {
                if (i % 2 == 1) beginX++;
                else endX--;
            } else {
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        }
    }

    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }
}
