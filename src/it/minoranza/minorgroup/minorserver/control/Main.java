package it.minoranza.minorgroup.minorserver.control;

import com.jfoenix.controls.JFXTextField;
import it.minoranza.minorgroup.minorserver.model.Callback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class Main implements Initializable {

    private Callback callbackTCP,callbackUDP;

    @FXML
    private VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/server.fxml"));
        try {
            container.getChildren().add(loader.load());
        }catch(IOException io){
            io.printStackTrace();
        }

    }

}
