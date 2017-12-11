package it.minoranza.minorgroup.minorserver.control;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void switchServer(final ActionEvent ae){
       if(((ToggleSwitch)ae.getSource()).isSelected())
           new Thread(new Runnable(){
               @Override
               public void run(){
                   try {
                       final ServerSocket ss = new ServerSocket(2000);
                       while(true)
                           new RunVirtualCommunication(ss.accept()).start();
                   }catch(IOException io){
                       io.printStackTrace();
                   }
               }
           }).start();
    }
}
