package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.JFXCheckBox;
import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.RowAuto;
import it.minoranza.minorgroup.minorclient.model.enums.Alimentazione;
import it.minoranza.minorgroup.minorclient.model.enums.Versione;
import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.Notifications;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class TableProperties implements Initializable {

    protected ObservableList<RowAuto> cars;

    @FXML
    protected BorderPane parent;

    @FXML
    protected TableView table;

    private Filter filter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cars = FXCollections.observableArrayList();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/minoranza/minorgroup/minorclient/view/filter.fxml"));
        try {
            parent.setTop(loader.load());
        } catch (IOException io) {
            io.printStackTrace();
        }

        filter = loader.getController();
        filter.attachTable(this);

        refreshData();

        final TableColumn<RowAuto, ImageView> marcaCol = new TableColumn<>("Marca");
        final TableColumn<RowAuto, String> modelloCol = new TableColumn<>("Modello");
        final TableColumn<RowAuto, Versione> versioneColumn = new TableColumn<>("Versione");
        final TableColumn<RowAuto, String> pesoColumn = new TableColumn<>("Peso in t");
        final TableColumn<RowAuto, Integer> kwColumn = new TableColumn<>("KW");
        final TableColumn<RowAuto, Integer> cilindrataColumn = new TableColumn<>("Cilindrata");
        final TableColumn<RowAuto, JFXCheckBox> neoColumn = new TableColumn<>("Neopatentati");
        final TableColumn<RowAuto, Alimentazione> alimentazioneColumn = new TableColumn<>("Alimentazione");
        final TableColumn<RowAuto, Integer> priceColumn = new TableColumn<>("Prezzo finale EUR");
        final TableColumn<RowAuto, String> dateColumn = new TableColumn<>("Data");

        marcaCol.setCellValueFactory(new PropertyValueFactory<>("logo"));
        modelloCol.setCellValueFactory(new PropertyValueFactory<>("modello"));
        versioneColumn.setCellValueFactory(new PropertyValueFactory<>("versione"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        kwColumn.setCellValueFactory(new PropertyValueFactory<>("kw"));
        cilindrataColumn.setCellValueFactory(new PropertyValueFactory<>("cilindrata"));
        neoColumn.setCellValueFactory(new PropertyValueFactory<>("neo"));
        alimentazioneColumn.setCellValueFactory(new PropertyValueFactory<>("alimentazione"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("eurPrice"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        table.getColumns().addAll(marcaCol, modelloCol, versioneColumn, alimentazioneColumn, pesoColumn, kwColumn, cilindrataColumn, dateColumn, neoColumn, priceColumn);
        table.setItems(cars);

    }

    protected abstract GestoreFile.List getList();

    public ObservableList<RowAuto> getCars() {
        return cars;
    }

    public void refreshData() {

        cars.clear();
        try {

            for (Auto a : GestoreFile.read(getList()))
                cars.add(new RowAuto(a));

            System.out.println("SIZE " + cars.size());

        } catch (Exception exc) {
            exc.printStackTrace();
            System.err.println("errore di lettura salvataggio");
        }

        filter.filter();
    }

    public void removeCar() {
        Alert alert;
        try {
            final Auto row = ((RowAuto) table.getSelectionModel().getSelectedItem()).getA();

            if (row != null) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma");
                alert.setHeaderText("Sei sicuro di voler elinare l'auto selezionata?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        GestoreFile.delete(row, getList());
                        refreshData();
                        Notifications.create()
                                .title("Fatto")
                                .text("Eliminazione avvenuta con successo")
                                .showInformation();
                    } catch (Exception exc) {
                        Notifications.create()
                                .title("Attenzione")
                                .text("L'eliminazione non è avvenuta")
                                .showWarning();
                    }
                }
            } else {
                throw new NullPointerException();
            }
        }catch(NullPointerException exc){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Devi prima selezionare un'auto");
            alert.showAndWait();
        }
    }

}
