package pl.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;


public class App extends Application {
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Circle> chequers = new ArrayList<>();
    private Circle current = null;

    @Override
    public void start(Stage stage) {


        stage.setTitle("Drawing Operations Test");
        Group root = new Group();

        Canvas canvas = new Canvas(1000, 1000);
        AnchorPane overlay = new AnchorPane();

        /*TODO ustawić autodopasowanie do okna*/
        circles.add(new Circle(1000, 1000, 1));
        overlay.setStyle("-fx-background-color: #202020");

        drawBoard();
        drawBase(8,1,4,true,Color.BLUEVIOLET);
        drawBase(4,10,4,true,Color.GREEN);
        drawBase(4+9,10,4,true,Color.YELLOW);

        drawBase(4+9,8,4,false,Color.ORANGE);
        drawBase(4,8,4,false,Color.CORNFLOWERBLUE);
        drawBase(8,17,4,false,Color.RED);



        overlay.getChildren().addAll(circles);
        overlay.getChildren().addAll(chequers);

        root.getChildren().addAll(canvas, overlay);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void drawBase( int beginX,int beginY,int h, boolean normal, Color color) {
        int endX = beginX;
        int endY;
        Circle c;
        if(normal) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    if (i % 2 == 0) {
                        c = new Circle(j * 55, i * 50, 25);
                    } else {
                        c = new Circle(j * 55 + 25, i * 50, 25);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        System.out.println("chequer");
                    }));
                    chequers.add(c);
                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for (int j = beginX; j <= endX; j++) {
                    if (i % 2 == 0) {
                        c = new Circle(j * 55, i * 50, 25);
                    } else {
                        c = new Circle(j * 55 + 25, i * 50, 25);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        System.out.println("chequer");
                    }));
                    chequers.add(c);
                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    public void drawBoard() {

        /*Tworzenie planszy bez baz*/
        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Circle c;
                if (i % 2 == 0) {
                    c = new Circle(j * 55, i * 50, 25);
                } else {
                    c = new Circle(j * 55 + 25, i * 50, 25);
                }
                c.setOnMouseClicked((event -> {
                            for (Circle cir: circles)cir.setStroke(Color.BLACK);
                            if(current == null) {
                                current = c;
                                c.setFill(Color.GREEN);
                                paintNeighbours(c);
                            } else if (calculateDist(c,current) < 60) {
                                current = c;
                                c.setFill(Color.CORNFLOWERBLUE);
                                paintNeighbours(c);
                            } else {
                                current = null;
                                for (Circle cir: circles)cir.setFill(Color.BLACK);
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
    }

    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }

    public static void main(String args[]){
        launch(args);
    }

}