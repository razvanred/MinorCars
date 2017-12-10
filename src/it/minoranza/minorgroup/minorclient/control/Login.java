package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.minoranza.minorgroup.commons.RequestServer;
import it.minoranza.minorgroup.commons.ResponseServer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Login implements Initializable {

    private Socket socket;

    @FXML
    private JFXTextField txfPassword;

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
