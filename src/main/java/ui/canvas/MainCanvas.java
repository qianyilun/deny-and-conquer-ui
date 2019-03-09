package ui.canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainCanvas extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/canvas.fxml"));
//        primaryStage.setTitle("deny-and-conquer-canvas");
//        primaryStage.setScene(new Scene(root, 1500, 1000));
//        primaryStage.getScene();
//
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void launchCanvas(Stage stage) throws IOException {
        MainCanvas mainCanvas = new MainCanvas();
        Parent root = FXMLLoader.load(mainCanvas.getClass().getResource("/canvas.fxml"));
        stage.setTitle("deny-and-conquer-canvas");
        stage.setScene(new Scene(root, 1500, 1000));
        stage.getScene();

        stage.show();
    }

}