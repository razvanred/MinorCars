package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.*;
import it.minoranza.minorgroup.minorclient.control.threads.StageOne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.json.JSONArray;

import java.net.URL;
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
    private JFXListView<Label> listDealers;

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

        listDealers.setMinWidth(200f);
        listDealers.setExpanded(true);
    }

    @FXML
    public void startListening() {
        if(btnListen.isSelected()) {
            thread.startOperations(Integer.parseInt(txfPort.getText()));
            spinner.setVisible(true);
        }else{
            if(thread!=null&&thread.isAlive())
                thread.explode();
            spinner.setVisible(false);
        }
    }

    @FXML
    public void connectionToServer() {

        onOff(true);

        String toSend = listDealers.getSelectionModel().getSelectedItem().getText();
        if (toSend != null) {

        } else {
            Notifications.create().title("Attenzione").text("Devi selezionare almeno una cella").showWarning();
        }

    }

    public void onOff(final boolean boo) {
        btnConn.setDisable(boo);
        txfPassword.setDisable(boo);
        listDealers.setDisable(boo);
    }

    public final void close(){
        ((Stage) spinner.getScene().getWindow()).close();
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

    public final void changeList(final JSONArray array){
        listDealers.getItems().clear();
        for(int i=0;i<array.length();i++){
            Label lbl=new Label(array.getString(i));
            listDealers.getItems().add(lbl);
        }
    }

}
