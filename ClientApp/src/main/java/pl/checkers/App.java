package pl.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

import java.util.ArrayList;


public class App extends Application {
    /*private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Circle> chequers = new ArrayList<>();
    private Circle current = null;
    private Circle currChequer = null;

    private int yGap = 45;
    */
    private Board board;
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
        System.out.println(response);




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
        overlay.getChildren().add(new Circle(1000, 1000, 1));
        overlay.setStyle("-fx-background-color: #202020");

        /*drawBoard();
        drawBase(8,1,4,false);
        drawBase(4,10,4,false);
        drawBase(4+9,10,4,false);

        drawBase(4+9,8,4,true);
        drawBase(4,8,4,true);
        drawBase(8,17,4,true);
        */
        this.board = new Board();
        ArrayList<Button> buttons = new ArrayList<>();

        /*Button b6 = new Button("6 Players");
        b6.setOnAction((e -> {
            putChequers(8,1,4,false,Color.BLUEVIOLET);
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            putChequers(4+9,8,4,true,Color.ORANGE);
            putChequers(4,8,4,true,Color.CORNFLOWERBLUE);
            putChequers(8,17,4,true,Color.INDIANRED);
            overlay.getChildren().addAll(board.chequers);
            for (Button b:buttons) {
                b.setDisable(true);
            }
            b6.setStyle("-fx-opacity: 0.7");
        }));

        Button b4 = new Button("4 Players");
        b4.setOnAction((e -> {
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            putChequers(4+9,8,4,true,Color.ORANGE);
            putChequers(4,8,4,true,Color.CORNFLOWERBLUE);
            overlay.getChildren().addAll(board.chequers);
            for (Button b:buttons) {
                b.setDisable(true);
            }
            b4.setStyle("-fx-opacity: 0.7");
        }));

        Button b3 = new Button("3 Players");
        b3.setOnAction((e -> {
            putChequers(8,1,4,false,Color.BLUEVIOLET);
            putChequers(4,10,4,false,Color.GREEN);
            putChequers(4+9,10,4,false,Color.YELLOW);

            overlay.getChildren().addAll(board.chequers);
            for (Button b:buttons) {
                b.setDisable(true);
            }
            b3.setStyle("-fx-opacity: 0.7");
        }));
    */
        Button b2 = new Button("2 Players");
        b2.setOnAction((e -> {
            board.putChequers(8,1,4,false,Color.BLUEVIOLET);

            board.putChequers(8,17,4,true,Color.INDIANRED);
            overlay.getChildren().addAll(board.chequers);
            for (Button b:buttons) {
                b.setDisable(true);
            }
            b2.setStyle("-fx-opacity: 0.7");
        }));

        /*buttons.add(b6);
        buttons.add(b4);
        buttons.add(b3);*/
        buttons.add(b2);


        Text serverLog = new Text();
        serverLog.setFont(new Font(25));
        serverLog.setText("ELO tutaj wstaw wiadomość serwera");
        serverLog.setLayoutX(600);
        serverLog.setLayoutY(100);

        t.getChildren().addAll(b2);
        overlay.getChildren().add(serverLog);
        overlay.getChildren().addAll(board.circles);
        overlay.getChildren().addAll(board.chequers);

        root.getChildren().addAll(overlay,t);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
        //App app= new App();

    }

}