package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.*;
import com.sun.deploy.util.FXLoader;
import it.minoranza.minorgroup.minorclient.control.threads.StageOne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListeningServer implements Initializable {

    private StageOne thread;

    @FXML
    private Label lblStatus;

    @FXML
    private JFXTextField txfPort;

    @FXML
    private JFXPasswordField txfPassword;

    @FXML
    private JFXListView<String> listDealers;

    @FXML
    private JFXSpinner spinner;

    @FXML
    private ToggleButton btnListen;

    @FXML
    private JFXButton btnConn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txfPort.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                txfPort.setText(newValue.replaceAll("[^\\d]", ""));
            btnListen.setDisable(newValue.isEmpty() || Integer.parseInt(newValue) < 1024 || Integer.parseInt(newValue) > 49151);
        });
        thread = new StageOne(this);
    }

    @FXML
    public void startListening() {
        thread.startOperations(Integer.parseInt(txfPort.getText()));
    }

    public void connectionToServer() {

    }

    public void setUpDown(final boolean status) {
        txfPort.setDisable(status);
        spinner.setVisible(status);
        txfPassword.setDisable(!status);
        btnConn.setDisable(!status);

        if(!status) {
            listDealers.getItems().clear();
            lblStatus.setText("Avvio server UDP");
        }else{
            lblStatus.setText("Seleziona una concessionaria");
        }
    }

}
