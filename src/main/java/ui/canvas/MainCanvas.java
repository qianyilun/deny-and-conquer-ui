package ui.canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainCanvas extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/canvas.fxml"));
        primaryStage.setTitle("deny-and-conquer-canvas");
        primaryStage.setScene(new Scene(root, 1500, 1000));
        primaryStage.getScene();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}