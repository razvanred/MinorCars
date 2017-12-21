package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.*;
import it.minoranza.minorgroup.commons.model.requests.ServerToDealer;
import it.minoranza.minorgroup.minorclient.control.threads.StageOne;
import it.minoranza.minorgroup.minorclient.control.threads.StageThree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
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
            try {
                thread.startOperations(Integer.parseInt(txfPort.getText()));
                setUpDown(true);
            } catch (SocketException sock) {
                sock.printStackTrace();
            }
            //spinner.setVisible(true);
            //onOff(true);
        }else{
            if(thread!=null&&thread.isAlive())
                thread.explode();
            setUpDown(false);
            //spinner.setVisible(false);
        }
    }

    @FXML
    public void connectionToServer(ActionEvent ae) {

        //onOff(true);

        //DatagramPacket p=thread.getPacket();


        //byte[] buff=new JSONObject(ServerToDealer.success.name(),true);
        //p.setData();

        Stage stage = new Stage();

        try {
            ((Stage) ((Node) ae.getSource()).getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/minoranza/minorgroup/minorclient/view/main.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 1240, 850));
            Main main = loader.getController();


            DatagramSocket r = new DatagramSocket(3333);

            StageThree stageThree = new StageThree(main, r);
            main.attachThird(stageThree);
            //StageTwo two = new StageTwo(ds, response, main);
            //main.attachSecond(two);
            stage.show();

            JSONObject h = new JSONObject();
            h.put(ServerToDealer.success.name(), true);

            byte[] buff = h.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), 40120);
            r.send(packet);

            stageThree.start();

            //run = false;
            //two.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


        /*try{
            String toSend = listDealers.getSelectionModel().getSelectedItem().getText();
            new StageTwo(thread.getDatagramSocket(), thread.getPacket(), this).startOperations(toSend, txfPassword.getText());
        } catch(NullPointerException nuller) {
            Notifications.create().title("Attenzione").text("Devi selezionare almeno una cella").showWarning();
        }*/

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

    /*private void onOff(final boolean boo){
        txfPort.setDisable(status);
    }*/

    public final void changeList(final JSONArray array){
        listDealers.getItems().clear();
        for(int i=0;i<array.length();i++){
            Label lbl=new Label(array.getString(i));
            listDealers.getItems().add(lbl);
        }
    }

}
