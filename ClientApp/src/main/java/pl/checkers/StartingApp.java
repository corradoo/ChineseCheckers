package pl.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingApp extends Application {

    Text text = new Text();
    Connector connector;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        text.setFont(new Font(25));
        text.setText("connecting to server...");
        text.setLayoutY(200);
        text.setLayoutX(100);

        root.getChildren().add(text);
        stage.setScene(new Scene(root));
        stage.setHeight(400);
        stage.setWidth(400);
        stage.show();

        connectToServer();
    }

    private void connectToServer() throws IOException {
        connector = new Connector();
    }


}
