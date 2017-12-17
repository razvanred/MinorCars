package it.minoranza.minorgroup.minorserver.control;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private JFXTextField txfPortUDP, txfPortUDPClient, txfPortTCP;

    @FXML
    private JFXToggleButton btnUDP, btnTCP;

    private ServerSocket ss;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        txfPortUDP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortUDP.setText(newValue.replaceAll("[^\\d]", ""));

                btnUDP.setDisable(checkEquals()||checkPort(txfPortUDP) || checkPort(txfPortUDPClient));
            }
        });

        txfPortUDPClient.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortUDPClient.setText(newValue.replaceAll("[^\\d]", ""));
                btnUDP.setDisable(checkEquals()||checkPort(txfPortUDP) || checkPort(txfPortUDPClient));
            }
        });

        txfPortTCP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortTCP.setText(newValue.replaceAll("[^\\d]", ""));
                btnTCP.setDisable(checkEquals()||checkPort(txfPortTCP));
            }
        });

    }

    private boolean checkEquals() {

        return Integer.parseInt(txfPortUDP.getText()) == Integer.parseInt(txfPortUDPClient.getText()) || Integer.parseInt(txfPortUDPClient.getText()) == Integer.parseInt(txfPortTCP.getText()) || Integer.parseInt(txfPortUDP.getText()) == Integer.parseInt(txfPortTCP.getText());
    }

    private boolean checkPort(final TextField txf) {
        return txf.getText().isEmpty() || Integer.parseInt(txf.getText()) < 1024 || Integer.parseInt(txf.getText()) > 49151;
    }

    @FXML
    private void startStopTCP(){
        try {
            if (btnTCP.isSelected()) {
                ss = new ServerSocket(Integer.parseInt(txfPortTCP.getText()));
                while(true){
                    new RunVirtualCommunication(ss.accept()).start();
                }
            }
        }catch(IOException io){
            btnTCP.setSelected(false);
        }
    }

    @FXML
    private void startStopUDP(){

    }

}
