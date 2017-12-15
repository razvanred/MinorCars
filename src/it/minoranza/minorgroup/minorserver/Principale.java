package it.minoranza.minorgroup.minorserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principale extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final Parent root= FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setScene(new Scene(root,500,350));
        primaryStage.setTitle("Server - MinorGroup");
        primaryStage.show();
    }
}
