package it.minoranza.minorgroup.minorclient.control;

import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.RowAuto;
import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class OnSale extends TableProperties {

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
    }


    public void sellCar(final ActionEvent ae) {
        try {
            final Auto row = ((RowAuto) table.getSelectionModel().getSelectedItem()).getA();
            try {
                GestoreFile.inserimento(row, GestoreFile.List.sold);
                GestoreFile.delete(row, getList());
                refreshData();
                Notifications.create().title("Fatto").text("L'auto è stata venduta con successo").showInformation();
            } catch (Exception exc) {
                exc.printStackTrace();
                refreshData();
                Notifications.create().title("Attenzione").text("Si è verificato un problema durante la vendita").showWarning();
            }
        }catch(NullPointerException nuller){
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Devi prima selezionare un'auto");
            alert.showAndWait();
        }
    }

    @Override
    public final GestoreFile.List getList() {
        return GestoreFile.List.onSale;
    }
}
