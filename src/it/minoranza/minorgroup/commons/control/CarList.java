package it.minoranza.minorgroup.commons.control;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarList implements Initializable {

    private final AbstractManager manager;
    private final Filter filter;
    private Parent rootFilter;

    public CarList(final AbstractManager manager){
        this.manager=manager;
        FXMLLoader load=new FXMLLoader(getClass().getResource("../view/filter.fxml"));

        try{
            rootFilter=load.load();
        }catch(IOException io){
            io.printStackTrace();
        }

        filter=load.getController();
        filter.attachCarList(this);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    public void refreshTable(){

    }

}
