package it.minoranza.minorgroup.commons.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabMaster implements Initializable {

    @FXML
    private Tab hereOnSale,hereSold,hereAddCar;

    private AbstractManager manager;
    private CarList onSale,sold;

    public TabMaster(final AbstractManager manager){
        this.manager=manager;
        onSale=new CarList(manager);
        sold=new CarList(manager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/carlist.fxml"));
        loader.setController(onSale);

        try{
            hereOnSale.setContent(loader.load());
        }catch(IOException io){
            io.printStackTrace();
        }



        //onSale=loader.getController();
        loader=new FXMLLoader(getClass().getResource("../view/carlist.fxml"));
        loader.setController(sold);
        try{
            hereSold.setContent(loader.load());
        }catch(IOException io){
            io.printStackTrace();
        }

        sold=loader.getController();


    }

/*  public void attachManager(final AbstractManager manager){
        this.manager=manager;
    }*/
}
