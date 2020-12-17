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

    @Override
    public void start(Stage stage) {

        ArrayList<Circle> circles = new ArrayList<>();

        stage.setTitle("Drawing Operations Test");
        Group root = new Group();

        Canvas canvas = new Canvas(1000, 1000);
        AnchorPane overlay = new AnchorPane();

        /*TODO ustawić autodopasowanie do okna*/
        circles.add(new Circle(1000,1000,1));
        overlay.setStyle("-fx-background-color: #202020");


        /*Tworzenie planszy bez baz*/
        int beginX = 17/2-2, endX= beginX +5;
        int beginY = 4, endY = 13, middleY = 8;
        for(int i = beginY; i < endY; i++) {
            for(int j = beginX; j < endX; j++) {
                Circle c;
                if (i % 2 == 0 ) {
                    c = new Circle(j * 55, i * 50, 25);
                }
                else {
                    c = new Circle(j * 55+25, i * 50, 25);
                }
                c.setOnMouseClicked((event -> c.setFill(new Color(Math.random(),Math.random(),Math.random(),Math.random()))));
                circles.add(c);
            }
            if(i >= middleY) {
                if (i % 2 == 1) beginX++;
                else endX--;
            } else {
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        }

        overlay.getChildren().addAll(circles);

        root.getChildren().addAll(canvas, overlay);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }

}