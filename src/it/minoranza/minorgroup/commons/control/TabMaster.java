package it.minoranza.minorgroup.commons.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabMaster implements Initializable {

    @FXML
    private BorderPane hereOnSale,hereSold;

    private AbstractManager manager;
    private CarList onSale,sold;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../../commons/view/carlist.fxml"));
        try{
            hereOnSale.setCenter(loader.load());
        }catch(IOException io){
            io.printStackTrace();
        }

        onSale=loader.getController();
        loader=new FXMLLoader(getClass().getResource("../../commons/view/carlist.fxml"));

        try{
            hereSold.setCenter(loader.load());
        }catch(IOException io){
            io.printStackTrace();
        }

        sold=loader.getController();


    }

    public void attachManager(final AbstractManager manager){
        this.manager=manager;
    }
}
