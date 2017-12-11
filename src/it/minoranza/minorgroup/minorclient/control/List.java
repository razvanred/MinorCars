package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class List implements Initializable {

    private Socket socket;

    @FXML
    private JFXPasswordField txfPassword;

    @FXML
    private JFXButton btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txfPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                btnLogin.setDisable(newValue.isEmpty());
            }
        });
    }

    public void attachSocket(final Socket socket){
        this.socket=socket;
    }

    public void login(final ActionEvent ae){

    }
}
