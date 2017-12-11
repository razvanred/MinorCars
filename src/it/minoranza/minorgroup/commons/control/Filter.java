package it.minoranza.minorgroup.commons.control;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.util.ResourceBundle;

public class Filter implements Initializable {

    private CarList carList;

    @FXML
    private ToggleSwitch neoToggle;

    @FXML
    private JFXRadioButton radioNew,radioUsed;

    @FXML
    private JFXComboBox comboAlimentazione,comboVersione;

    @FXML
    private JFXTextField startingAt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void attachCarList(final CarList carList){
        this.carList=carList;
    }

}
