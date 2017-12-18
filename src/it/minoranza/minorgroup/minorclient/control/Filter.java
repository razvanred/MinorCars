package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.AutoUsata;
import it.minoranza.minorgroup.commons.model.RowAuto;
import it.minoranza.minorgroup.minorclient.model.enums.Alimentazione;
import it.minoranza.minorgroup.minorclient.model.enums.Marca;
import it.minoranza.minorgroup.minorclient.model.enums.Versione;
import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.ToggleSwitch;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Filter implements Initializable {

    private TableProperties table;

    @FXML
    private ToggleSwitch neoToggle;

    @FXML
    private JFXComboBox<String> comboVersione;

    @FXML
    private JFXComboBox<String> comboAlimentazione;

    @FXML
    private JFXTextField startingAt;

    @FXML
    private SegmentedButton filterBrand;

    @FXML
    private JFXRadioButton radioNew, radioUsed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startingAt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    startingAt.setText(newValue.replaceAll("[^\\d]", ""));
                }
                filter();
            }
        });

        ToggleGroup status = new ToggleGroup();
        radioNew.setToggleGroup(status);
        radioNew.setUserData(0);
        radioUsed.setToggleGroup(status);
        radioUsed.setUserData(1);

        radioNew.setSelected(true);

        ToggleGroup brandGroup = new ToggleGroup();

        filterBrand.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        for (Marca m : Marca.values()) {
            ToggleButton button = new ToggleButton(m.getNome());
            filterBrand.getButtons().add(button);
            button.setUserData(m);
            button.setToggleGroup(brandGroup);

            button.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filter();
                    /*if (newValue) {
                        System.out.println(m.getNome());
                    }*/
                }
            });
        }

        status.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //System.out.println(newValue.getUserData());
                filter();
            }
        });

        comboVersione.getItems().add("Tutti");
        comboAlimentazione.getItems().add("Tutti");

        for (Alimentazione a : Alimentazione.values())
            comboAlimentazione.getItems().add(a.name());

        for (Versione v : Versione.values())
            comboVersione.getItems().add(v.getDescrizione());

        comboVersione.getSelectionModel().selectFirst();
        comboAlimentazione.getSelectionModel().selectFirst();

    }

    public void attachTable(final TableProperties table) {
        this.table = table;
    }


    public void filter() {

        int price;

        try {
            price = Integer.parseInt(startingAt.getText());
        } catch (NumberFormatException num) {
            price = 0;
        }

        try {
            ArrayList<Auto> auto = GestoreFile.read(table.getList());
            table.getCars().clear();
            for (Auto a : auto) {
                if (a.getTotalPrice() >= price && testVersione(a) && testAlimentazione(a) && testConditions(a) && testNeo(a) && testBrand(a))
                    table.getCars().add(new RowAuto(a));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean testNeo(final Auto a) {
        return !neoToggle.isSelected() || a.isNeo();
    }

    private boolean testBrand(final Auto a) {

        for (int i = 0; i < filterBrand.getButtons().size(); i++)
            if (filterBrand.getButtons().get(i).isSelected())
                return (Marca) filterBrand.getButtons().get(i).getUserData() == a.getMarca();

        return true;
    }

    private boolean testConditions(final Auto auto) {
        if (radioUsed.isSelected())
            return auto instanceof AutoUsata;
        else
            return !(auto instanceof AutoUsata);
    }

    private boolean testVersione(final Auto a) {
        try {
            return Versione.values()[comboVersione.getSelectionModel().getSelectedIndex() - 1] == a.getTipo().getVersione();
        } catch (ArrayIndexOutOfBoundsException ar) {
            return true;
        }
    }

    private boolean testAlimentazione(final Auto a) {
        try {
            return Alimentazione.values()[comboAlimentazione.getSelectionModel().getSelectedIndex() - 1] == a.getMotore().getAlimentazione();
        } catch (ArrayIndexOutOfBoundsException ar) {
            return true;
        }
    }

}
