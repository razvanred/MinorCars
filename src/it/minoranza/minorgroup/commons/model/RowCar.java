package it.minoranza.minorgroup.commons.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import it.minoranza.minorgroup.commons.model.enums.Alimentazione;
import it.minoranza.minorgroup.commons.model.enums.CarJSON;
import it.minoranza.minorgroup.commons.model.enums.Marca;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

public class RowCar {

    private final Marca marca;
    private final SimpleStringProperty modello, versione, peso, data;
    private final SimpleIntegerProperty kw, eurPrice,cilindrata;
    private final JFXCheckBox neo;
    private final Alimentazione alimentazione;
    private final JFXButton btnEdit;

    public RowCar(final JSONObject obj) {
        marca = Marca.valueOf(obj.getString(CarJSON.marca.name()));
        modello = new SimpleStringProperty(obj.getString(CarJSON.modello.name()));
        versione = new SimpleStringProperty(obj.getString(CarJSON.versione.name()));
        peso = new SimpleStringProperty(obj.getString(CarJSON.peso.name()));
        data = new SimpleStringProperty(obj.getString(CarJSON.usata.name()));
        kw = new SimpleIntegerProperty(obj.getInt(CarJSON.kw.name()));
        eurPrice = new SimpleIntegerProperty(obj.getInt(CarJSON.startingAt.name()));
        alimentazione=Alimentazione.valueOf(obj.getString(CarJSON.alimentazione.name()));
        cilindrata=new SimpleIntegerProperty(obj.getInt(CarJSON.cilindrata.name()));

        btnEdit = new JFXButton("MODIFICA");
        btnEdit.setStyle("-fx-background-color: dodgerblue; -fx-text-fill: white");

        neo = new JFXCheckBox();
        neo.setSelected(kw.get() <= 70 && kw.get() / Integer.parseInt(peso.get()) <= 55);
    }

}
