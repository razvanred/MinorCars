package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXTextField;
import it.minoranza.minorgroup.minorclient.control.threads.StageOne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class IPServer implements Initializable {

    private StageOne thread;

    @FXML
    private JFXTextField txfIp,txfPort;

    public IPServer(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final String regex = makePartialIPRegex();
        final UnaryOperator<TextFormatter.Change> ipAddressFilter = c -> {
            String text = c.getControlNewText();
            if  (text.matches(regex)) {
                return c ;
            } else {
                return null ;
            }
        };
        txfIp.setTextFormatter(new TextFormatter<>(ipAddressFilter));
    }

    private String makePartialIPRegex() {
        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))" ;
        String subsequentPartialBlock = "(\\."+partialBlock+")" ;
        String ipAddress = partialBlock+"?"+subsequentPartialBlock+"{0,3}";
        return "^"+ipAddress ;
    }


    public void attachThread(final StageOne thread){
        this.thread=thread;
    }

    public void getList(){
        try {
            thread.startOperations(InetAddress.getByName(txfIp.getText()),Integer.parseInt(txfPort.getText()));
        }catch(UnknownHostException unknown){
            unknown.printStackTrace();
        }
    }

}
