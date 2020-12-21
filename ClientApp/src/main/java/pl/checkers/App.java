package pl.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class App extends Application {

    private ArrayList<Button> buttons = new ArrayList<>();
    private Text serverLog = new Text();
    private Text playerInfo = new Text();
    public Board board;

    Stage gameStage;
    Text text = new Text();


    @Override
    public void start(Stage stage) throws IOException {

        Pane rootC = new Pane();
        text.setFont(new Font(25));
        text.setText("connecting to server...");
        text.setLayoutY(200);
        text.setLayoutX(100);

        rootC.getChildren().add(text);
        stage.setScene(new Scene(rootC));
        stage.setHeight(400);
        stage.setWidth(400);
        stage.show();

        createGameStage();
        stage.hide();

    }

    void createGameStage() throws IOException {
        gameStage = new Stage();
        gameStage.setTitle("Chequers");
        Pane root = new Pane();
        TilePane t = new TilePane();
        Pane overlay = new Pane();

        root.setStyle("-fx-background-color: #353535");


        serverLog.setFont(new Font(25));
        serverLog.setLayoutX(600);
        serverLog.setLayoutY(100);

        playerInfo.setFont(new Font(25));
        playerInfo.setLayoutX(50);
        playerInfo.setLayoutY(50);

        board = new Board();
        overlay.getChildren().addAll(serverLog,playerInfo);
        overlay.getChildren().addAll(board.circles);

        root.getChildren().addAll(overlay,t);

        gameStage.setScene(new Scene(root));
        gameStage.show();
    }

}