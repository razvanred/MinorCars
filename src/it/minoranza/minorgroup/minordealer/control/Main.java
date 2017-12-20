package it.minoranza.minorgroup.minordealer.control;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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
    private JFXTextField txfIP, txfPortTCP, txfPortUDP, txfName;

    @FXML
    private JFXPasswordField tpfPassword;

    @FXML
    private JFXToggleButton btnTCP;

    @FXML
    private BarChart barChar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*txfPassword.textProperty().addListener((observable, oldValue, newValue) ->
                btnTCP.setDisable(txfPassword.getText().isEmpty()||txfUsername.getText().isEmpty())
        );*/

        btnTCP.setDisable(true);

        txfPortTCP.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txfPortTCP.setText(newValue.replaceAll("[^\\d]", ""));
            }
            checkBtnTCP();
        });

        final String regex = makePartialIPRegex();
        final UnaryOperator<TextFormatter.Change> ipAddressFilter = c -> {
            String text = c.getControlNewText();
            if (text.matches(regex)) {
                return c;
            } else {
                return null;
            }
        };
        txfIP.setTextFormatter(new TextFormatter<>(ipAddressFilter));

        txfIP.textProperty().addListener((observable, oldValue, newValue) ->
                checkBtnTCP()
        );

        txfName.textProperty().addListener((observable, oldValue, newValue) ->
            checkBtnTCP()
        );

        txfPortUDP.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txfPortTCP.setText(newValue.replaceAll("[^\\d]", ""));
            }
            checkBtnTCP();
        }));

    }

    private void checkBtnTCP(){
        btnTCP.setDisable(txfIP.getText().isEmpty() || txfPortTCP.getText().isEmpty() || txfName.getText().trim().isEmpty() ||tpfPassword.getText().isEmpty()||txfPortUDP.getText().isEmpty()||Integer.parseInt(txfPortUDP.getText())==Integer.parseInt(txfPortTCP.getText()));
    }

    private String makePartialIPRegex() {
        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))";
        String subsequentPartialBlock = "(\\." + partialBlock + ")";
        String ipAddress = partialBlock + "?" + subsequentPartialBlock + "{0,3}";
        return "^" + ipAddress;
    }

    public void openConnection() {

        if (btnTCP.isSelected()) {
            try {
                tcp = new TCPThread(this);
                tcp.start();
                setUpDown(true);
                //btnTCP.setDisable(true);
            } catch (IOException io) {
                io.printStackTrace();
                btnTCP.setSelected(false);
                setUpDown(false);
            }
            /*try {
                udp = new UDPThread(Integer.parseInt(txfPortUDP.getText()), this);
                udp.start();
                setUpDown(true);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        /*}else{
            if (udp != null && udp.isAlive()) {
                udp.interrupt();
                setUpDown(false);
            }
        }*/
        } else {
            try{
                if (tcp.isAlive()) {
                    tcp.interrupt();
                    tcp.explode();
                }
            }catch(NullPointerException|IOException nuller){
                nuller.printStackTrace();
                setUpDown(false);
            }
        }
    }

    public final void setUpDown(final boolean boo){
        txfPortUDP.setDisable(boo);
        txfPortTCP.setDisable(boo);
        txfIP.setDisable(boo);
        txfName.setDisable(boo);
        tpfPassword.setDisable(boo);
        //btnTCP.setDisable(boo);
    }

    public final InetAddress getAddress() throws UnknownHostException {
        return InetAddress.getByName(txfIP.getText());
    }

    public final String getPasskey() {
        return tpfPassword.getText();
    }

    public final int getPortTCP() {
        return Integer.parseInt(txfPortTCP.getText());
    }

    public final int getPortUDP(){
        return Integer.parseInt(txfPortUDP.getText());
    }

    public String getName() {
        return txfName.getText().trim();
    }
}
