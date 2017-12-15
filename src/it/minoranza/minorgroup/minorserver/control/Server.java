package it.minoranza.minorgroup.minorserver.control;

import com.jfoenix.controls.JFXTextField;
import it.minoranza.minorgroup.minorserver.model.Callback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Server implements Initializable {

    @FXML
    private JFXTextField txfPort;

    private Callback callback;

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
        txfPort.setTextFormatter(new TextFormatter<>(ipAddressFilter));
    }

    public void attachCallback(final Callback callback){
        this.callback=callback;
        txfPort.setPromptText(callback.getType());
    }

    private String makePartialIPRegex() {
        String partialBlock = "(([01]?[0-9]{0,2})|(2[0-4][0-9])|(25[0-5]))" ;
        String subsequentPartialBlock = "(\\."+partialBlock+")" ;
        String ipAddress = partialBlock+"?"+subsequentPartialBlock+"{0,3}";
        return "^"+ipAddress ;
    }

    public void startStop(final ActionEvent ae){
        if(((ToggleButton)ae.getSource()).isSelected())
            callback.create();
        else
            callback.interrupt();
    }
}
