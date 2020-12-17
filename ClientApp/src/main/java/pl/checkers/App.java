package pl.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ArrayList<Circle> circles = new ArrayList<>();
        circles.add(new Circle(1000,1000,1));
        stage.setTitle("Drawing Operations Test");
        Group root = new Group();

        Canvas canvas = new Canvas(1000, 1000);
        AnchorPane overlay = new AnchorPane();


        overlay.setStyle("-fx-background-color: #202020");

        Circle circle = new Circle(50 ,50, 50);
        Circle circle2 = new Circle(150 ,50, 50);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                circle.setFill(new Color(Math.random(),Math.random(),Math.random(),Math.random()));
            }
        };

        circle.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);


        int beginX = 17/2-2, endX= beginX +5;
        int beginY = 4, endY = 13, middleY = 8;
        for(int i = beginY; i < endY; i++) {
            for(int j = beginX; j < endX; j++) {
                Circle c;
                if (i % 2 == 0 ) {
                    // gc.fillOval(j * 55, i * 50, 50, 50);
                    c=new Circle(j * 55, i * 50, 25);
                }
                else {
                    // gc.fillOval(j * 55 + 25, i * 50, 50, 50);
                    c = new Circle(j * 55+25, i * 50, 25);
                }
                c.setOnMouseClicked((new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        c.setFill(new Color(Math.random(),Math.random(),Math.random(),Math.random()));
                    }
                }));
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

        circle2.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle2.setFill(new Color(Math.random(),Math.random(),Math.random(),Math.random()));
            }
        }));

        overlay.getChildren().addAll(circles);



        root.getChildren().addAll(canvas, overlay);

        stage.setScene(new Scene(root));
        stage.show();
    }

    private void printBoard(GraphicsContext gc) {
        int beginX = 17/2-2, endX= beginX +5;
        int beginY = 4, endY = 13, middleY = 8;
        for(int i = beginY; i < endY; i++) {
            for(int j = beginX; j < endX; j++) {
                if (i % 2 == 0 ) {
                    gc.fillOval(j * 55, i * 50, 50, 50);
                }
                else {
                    gc.fillOval(j * 55 + 25, i * 50, 50, 50);
                }
            }
            if(i >= middleY) {
                if (i % 2 == 1) beginX++;
                else endX--;
            } else {
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        }
        /*GraphicsContext gc = canvas.getGraphicsContext2D();
        printBoard(gc);*/
    }

    public static void main(String args[]){
        launch(args);
    }

}