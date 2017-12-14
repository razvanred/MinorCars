package it.minoranza.minorgroup.minorserver.control;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private Label lblStatusTCP, lblStatusUDP;

    @FXML
    private JFXTextField portTCP, portUDP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void switchServerTCP(final MouseEvent ae) {
        if (((ToggleSwitch) ae.getSource()).isSelected()) {
            new Thread(() -> {
                try {
                    final ServerSocket ss = new ServerSocket(Integer.parseInt(portTCP.getText()));
                    while (true)
                        new RunVirtualCommunication(ss.accept()).start();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }).start();
        } else {

        }
    }

    public void switchServerUDP(final MouseEvent ae) {
        if (((ToggleSwitch) ae.getSource()).isSelected()) {
            new Thread(() -> {
                try {
                    DatagramSocket s = new DatagramSocket(Integer.parseInt(portUDP.getText()));
                    s.setBroadcast(true);
                    InetAddress address = InetAddress.getByName("255.255.255.255");
                    //s.send(new DatagramPacket());
                } catch (SocketException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {

        }
    }

}
