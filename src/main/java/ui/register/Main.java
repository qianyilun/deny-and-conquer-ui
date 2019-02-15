package ui.register;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    RegisterController registerController = new RegisterController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        primaryStage.setTitle("deny-and-conquer");
        primaryStage.setScene(new Scene(root, 350, 275));
        primaryStage.getScene();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}