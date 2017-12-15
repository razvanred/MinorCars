package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.awt.event.ActionEvent;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoosePort implements Initializable {

    private UDPListener udp;

    @FXML
    private ToggleButton btnListen;

    @FXML
    private JFXTextField txfPort;

    @FXML
    private JFXSpinner spinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ChoosePort(){
        udp=new UDPListener();
    }

    public void listen(){
        if(btnListen.isSelected()){
            try {
                udp.startOperations(Integer.parseInt(txfPort.getText()));
                spinner.setVisible(true);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }else{
            udp.boom();
            spinner.setVisible(false);
        }

    }

}
