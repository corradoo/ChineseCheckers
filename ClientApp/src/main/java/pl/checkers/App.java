package pl.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

import java.util.ArrayList;


public class App extends Application {
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Circle> chequers = new ArrayList<>();
    private Circle current = null;
    private Circle currChequer = null;

    private int yGap = 45;

    private Connect connect;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    public String playerName;



    public void setConnect() throws Exception {
        this.connect=new Connect("localhost");
        this.socket= connect.socket;
        this.in= connect.in;
        this.out= connect.out;

    }

    /*public App(){
        setConnect();
    }

     */

    public void play(){
        var response= in.nextLine();
        this.playerName=response.substring(8);





    }





    @Override
    public void start(Stage stage) {

        try{
            setConnect();
            play();
        }
        catch (Exception e){
            System.out.println("Blad");
        }
        stage.setTitle("Drawing Operations Test");
        Group root = new Group();

        TilePane t = new TilePane();
        Pane overlay = new Pane();

        /*TODO ustawić autodopasowanie do okna*/
        circles.add(new Circle(1000, 1000, 1));
        overlay.setStyle("-fx-background-color: #202020");

        drawBoard();
        drawBase(8,1,4,false);
        drawBase(4,10,4,false);
        drawBase(4+9,10,4,false);

        drawBase(4+9,8,4,true);
        drawBase(4,8,4,true);
        drawBase(8,17,4,true);


        Button b6 = new Button("6 Players");
        b6.setOnAction((e -> {
            putChequers(8,1,4,false,Color.BLUEVIOLET);
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            putChequers(4+9,8,4,true,Color.ORANGE);
            putChequers(4,8,4,true,Color.CORNFLOWERBLUE);
            putChequers(8,17,4,true,Color.INDIANRED);
            overlay.getChildren().addAll(chequers);
        }));

        Button b4 = new Button("4 Players");
        b4.setOnAction((e -> {
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            putChequers(4+9,8,4,true,Color.ORANGE);
            putChequers(4,8,4,true,Color.CORNFLOWERBLUE);
            overlay.getChildren().addAll(chequers);
        }));

        Button b3 = new Button("3 Players");
        b3.setOnAction((e -> {
            putChequers(8,1,4,false,Color.BLUEVIOLET);
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            overlay.getChildren().addAll(chequers);
        }));

        Button b2 = new Button("2 Players");
        b2.setOnAction((e -> {
            putChequers(8,1,4,false,Color.BLUEVIOLET);

            putChequers(8,17,4,true,Color.INDIANRED);
            overlay.getChildren().addAll(chequers);
        }));


        t.getChildren().addAll(b6,b4,b3,b2);
        overlay.getChildren().addAll(circles);
        overlay.getChildren().addAll(chequers);

        root.getChildren().addAll(overlay,t);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void drawBase( int beginX,int beginY,int h, boolean invert) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Circle c;

                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setOnMouseClicked((event -> {
                        if(currChequer != null) {
                            moveChequer(c);
                            currChequer = null;
                        }
                    }));
                    circles.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for (int j = beginX; j <= endX; j++) {
                    Circle c;
                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setOnMouseClicked((event -> {
                        if(currChequer != null) {
                            moveChequer(c);
                            currChequer = null;
                        }
                    }));
                    circles.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    public void putChequers( int beginX,int beginY,int h, boolean invert, Color color) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Circle c;

                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        if(currChequer == null) {
                            setCurrentChequer(c);
                            showMoves(currChequer);
                        }
                    }));
                    c.setOnMouseMoved((e -> c.setOpacity(0.8)));
                    c.setOnMouseExited((e -> c.setOpacity(1)));
                    chequers.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for (int j = beginX; j <= endX; j++) {
                    Circle c;
                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        if(currChequer == null) {
                            setCurrentChequer(c);
                            showMoves(currChequer);
                        }
                    }));
                    c.setOnMouseMoved((e -> c.setOpacity(0.8)));
                    c.setOnMouseExited((e -> c.setOpacity(1)));
                    chequers.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    private void setCurrentChequer(Circle c) {
        for (Circle ch: chequers) {
            if(ch.getCenterX() == c.getCenterX() && ch.getCenterY() == c.getCenterY())
                currChequer = ch;

        }
    }

    private void showMoves(Circle chequer) {
        paintNeighbours(chequer);
    }

    public void drawBoard() {

        /*Tworzenie planszy bez baz*/
        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Circle c;
                if (i % 2 == 0) {
                    c = new Circle(j * 50, i * yGap, 24);
                } else {
                    c = new Circle(j * 50 + 25, i * yGap, 24);
                }
                c.setOnMouseClicked((event -> {
                            if(currChequer != null) {
                                moveChequer(c);
                                currChequer = null;
                            }
                        }));
                circles.add(c);
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

    private void moveChequer( Circle c) {
        for (Circle ch:chequers) {
            if(ch.getCenterX() == currChequer.getCenterX() && ch.getCenterY() == currChequer.getCenterY() &&
                    calculateDist(currChequer,c) < 60) {
                ch.setCenterX(c.getCenterX());
                ch.setCenterY(c.getCenterY());

                for (Circle cir: circles)cir.setStroke(Color.BLACK);
            }
            if (ch.getCenterX() == currChequer.getCenterX() && ch.getCenterY() == currChequer.getCenterY() && c.getStroke() == Color.RED) {
                ch.setCenterX(c.getCenterX());
                ch.setCenterY(c.getCenterY());

                for (Circle cir: circles)cir.setStroke(Color.BLACK);
            }
        }
    }

    public void paintNeighbours(Circle main) {
        double dist;
        double xDis;
        double yDis;
        for (Circle c: circles) {
            xDis = c.getCenterX()-main.getCenterX();
            yDis = c.getCenterY()-main.getCenterY();
            dist = Math.sqrt(xDis*xDis + yDis*yDis);
            if(dist < 60 && dist !=0) c.setStroke(Color.RED);
        }
        for (Circle ch: chequers) {
            xDis = ch.getCenterX()-main.getCenterX();
            yDis = ch.getCenterY()-main.getCenterY();
            dist = Math.sqrt(xDis*xDis + yDis*yDis);
            if(dist < 60 && dist !=0) {
                System.out.println("Dist: "+dist);
                showJumps(main,ch);
            }
        }
    }

    private void showJumps(Circle main, Circle ch) {

        double xDis = ch.getCenterX() - main.getCenterX() ;
        double yDis = ch.getCenterY() - main.getCenterY() ;

        for(Circle c: circles) {
            if(c.getCenterX() - ch.getCenterX() == xDis && c.getCenterY() - ch.getCenterY() == yDis) {
                c.setStroke(Color.RED);
            }
        }
    }

    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }







    public static void main(String[] args){
        launch(args);
        App app= new App();

    }

}