package it.minoranza.minorgroup.minorserver;

import it.minoranza.minorgroup.minorserver.model.ConnectionSaver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principale extends Application{

    public static ObservableList<ConnectionSaver> dealers = FXCollections.observableArrayList();

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
