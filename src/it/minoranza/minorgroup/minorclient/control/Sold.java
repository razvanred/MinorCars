package it.minoranza.minorgroup.minorclient.control;

import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Sold extends TableProperties implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @Override
    protected GestoreFile.List getList() {
        return GestoreFile.List.sold;
    }

}
