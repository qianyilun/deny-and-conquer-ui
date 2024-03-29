package ui.register;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/register.fxml"));
        primaryStage.setTitle("deny-and-conquer-register");
        primaryStage.setScene(new Scene(root, 350, 500));
        primaryStage.getScene();

        primaryStage.show();
        stage = primaryStage;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}