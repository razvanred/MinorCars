package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.control.TabMaster;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    private final DealerManager manager;
    private final TabMaster tabMaster;
    private Parent rootTabMaster;



    public Main(){
        final FXMLLoader loader=new FXMLLoader(getClass().getResource("../../commons/view/tabmaster.fxml"));
        loader.setController((tabMaster=new TabMaster(manager=new DealerManager())));

        try{
            rootTabMaster=loader.load();
        }catch(IOException io){
            io.printStackTrace();
        }

        //tabMaster=loader.getController();

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    public void disconnect(final ActionEvent ae){

    }
}
