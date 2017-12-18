package it.minoranza.minorgroup.minordealer.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Main implements Initializable {

    private UDPThread udp;
    private TCPThread tcp;

    @FXML
    private JFXTextField txfIP,txfPort,txfName;

    @FXML
    private JFXPasswordField tpfPassword;

    @FXML
    private JFXButton btnTCP;

    @FXML
    private BarChart barChar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*txfPassword.textProperty().addListener((observable, oldValue, newValue) ->
                btnTCP.setDisable(txfPassword.getText().isEmpty()||txfUsername.getText().isEmpty())
        );*/

        btnTCP.setDisable(true);

        txfPort.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txfPort.setText(newValue.replaceAll("[^\\d]", ""));
            }
            btnTCP.setDisable(txfIP.getText().isEmpty()||txfPort.getText().isEmpty()||txfName.getText().trim().isEmpty());
        });

        final String regex = makePartialIPRegex();
        final UnaryOperator<TextFormatter.Change> ipAddressFilter = c -> {
            String text = c.getControlNewText();
            if  (text.matches(regex)) {
                return c ;
            } else {
                return null ;
            }
        };
        txfIP.setTextFormatter(new TextFormatter<>(ipAddressFilter));

        txfPort.textProperty().addListener((observable, oldValue, newValue) ->
                btnTCP.setDisable(txfIP.getText().isEmpty()||txfPort.getText().isEmpty()||txfName.getText().trim().isEmpty())
        );

        txfName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnTCP.setDisable(txfIP.getText().isEmpty()||txfPort.getText().isEmpty()||txfName.getText().trim().isEmpty());
        });

    }

    private String makePartialIPRegex() {
        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))" ;
        String subsequentPartialBlock = "(\\."+partialBlock+")" ;
        String ipAddress = partialBlock+"?"+subsequentPartialBlock+"{0,3}";
        return "^"+ipAddress ;
    }

    public void openConnection(){
        /*try {
            udp=new UDPThread(InetAddress.getByName(txfIP.getText()),Integer.parseInt(txfPort.getText()),this);
            udp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try{
            tcp=new TCPThread(this);
            tcp.start();

            btnTCP.setDisable(true);
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    public final InetAddress getAddress() throws UnknownHostException {
        return InetAddress.getByName(txfIP.getText());
    }

    public final String getPasskey(){
        return tpfPassword.getText();
    }

    public final int getPort(){
        return Integer.parseInt(txfPort.getText());
    }

    public String getName(){
        return txfName.getText().trim();
    }
}
