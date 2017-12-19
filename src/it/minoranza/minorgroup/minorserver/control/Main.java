package it.minoranza.minorgroup.minorserver.control;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import it.minoranza.minorgroup.minorserver.Principale;
import it.minoranza.minorgroup.minorserver.model.ConnectionSaver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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

    private TCPThread tcp;
    public static UDPThread udp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        txfPortUDP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortUDP.setText(newValue.replaceAll("[^\\d]", ""));

                btnUDP.setDisable(checkEqualsUDP()||checkPort(txfPortUDP) || checkPort(txfPortUDPClient));
            }
        });

        txfPortUDPClient.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortUDPClient.setText(newValue.replaceAll("[^\\d]", ""));
                btnUDP.setDisable(checkEqualsUDP()||checkPort(txfPortUDP) || checkPort(txfPortUDPClient));
            }
        });

        txfPortTCP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*"))
                    txfPortTCP.setText(newValue.replaceAll("[^\\d]", ""));
                btnTCP.setDisable(checkEqualsUDP()||!btnUDP.isSelected()||checkPort(txfPortTCP));
            }
        });

        Principale.dealers.addListener(new ListChangeListener<ConnectionSaver>() {
            @Override
            public void onChanged(Change<? extends ConnectionSaver> c) {
                if(udp!=null)
                    udp.refresh();
            }
        });

    }

    private boolean checkEqualsUDP() {

        return Integer.parseInt(txfPortUDP.getText()) == Integer.parseInt(txfPortUDPClient.getText());
    }

    private boolean checkPort(final TextField txf) {
        return txf.getText().isEmpty() || Integer.parseInt(txf.getText()) < 1024 || Integer.parseInt(txf.getText()) > 49151;
    }

    public final int getPortTCP(){
        return Integer.parseInt(txfPortTCP.getText());
    }

    public final int getPortUDP(){
        return Integer.parseInt(txfPortUDP.getText());
    }

    @FXML
    private void startStopTCP(){
        if(btnTCP.isSelected()){
            try {
                System.err.println("tcp started");
                tcp=new TCPThread(this);
                tcp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            btnTCP.setSelected(false);
            if(tcp!=null)
                tcp.boom();
        }
    }

    public final int getPortUDPClient(){
        return Integer.parseInt(txfPortUDPClient.getText());
    }

    @FXML
    private void startStopUDP(){
        System.err.println("udp started");
        if(btnUDP.isSelected()) {
            try {
                udp = new UDPThread(this);
                udp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            if(udp!=null)
                udp.boom();
        }
    }

}
