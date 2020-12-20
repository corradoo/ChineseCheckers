package pl.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class App extends Application {

    private ArrayList<Button> buttons = new ArrayList<>();
    private Text serverLog = new Text();
    private Text playerInfo = new Text();
    public Board board = new Board(4);

    @Override
    public void start(Stage stage) {

        stage.setTitle("Chequers");
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


        overlay.getChildren().addAll(serverLog,playerInfo);
        overlay.getChildren().addAll(board.circles);

        root.getChildren().addAll(overlay,t);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createButtons() {
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
       /* b2.setOnAction((e -> {
            board.putChequers(8,1,4,false,Color.BLUEVIOLET);

            board.putChequers(8,17,4,true,Color.INDIANRED);
            overlay.getChildren().addAll(board.chequers);
            for (Button b:buttons) {
                b.setDisable(true);
            }
            b2.setStyle("-fx-opacity: 0.7");
        }));


        */
        /*buttons.add(b6);
        buttons.add(b4);
        buttons.add(b3);*/
        buttons.add(b2);
    }

}