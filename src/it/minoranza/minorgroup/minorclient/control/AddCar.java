package it.minoranza.minorgroup.minorclient.control;

import com.jfoenix.controls.*;
import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.AutoUsata;
import it.minoranza.minorgroup.commons.model.Motore;
import it.minoranza.minorgroup.commons.model.Tipo;
import it.minoranza.minorgroup.minorclient.model.enums.Accessorio;
import it.minoranza.minorgroup.minorclient.model.enums.Alimentazione;
import it.minoranza.minorgroup.minorclient.model.enums.Marca;
import it.minoranza.minorgroup.minorclient.model.enums.Versione;
import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.ToggleSwitch;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCar implements Initializable {

    @FXML
    private JFXComboBox<Marca> brandCombo;

    @FXML
    private VBox accContainer;

    @FXML
    private JFXComboBox<Versione> versionCombo;

    @FXML
    private JFXComboBox<Alimentazione> alimCombo;

    @FXML
    private JFXTextField startingFrom, modello, cilindrata, kw;

    @FXML
    private JFXButton btnAdd, btnLoadPic;

    @FXML
    private ImageView imagination;

    private boolean needUp;
    private Image unknown;

    @FXML
    private Spinner<Integer> decPeso;

    @FXML
    private Spinner<Integer> intPeso;

    @FXML
    private ToggleSwitch usedSwitch;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Label finalPrice;

    private JFXCheckBox[] accessori;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        accessori = new JFXCheckBox[Accessorio.values().length];

        for (int i = 0; i < accessori.length; i++) {
            final Accessorio accessorio = Accessorio.values()[i];
            final HBox hbox = new HBox();
            hbox.setSpacing(10);
            accessori[i] = new JFXCheckBox(accessorio.getDescrizione());
            accessori[i].setOnAction(event -> calcPrice());
            accessori[i].setUserData(accessorio);
            hbox.getChildren().add(accessori[i]);
            final Label label = new Label("EUR " + accessorio.getPrice());
            label.setStyle("-fx-font-weight: bold");
            hbox.getChildren().add(label);
            accContainer.getChildren().add(hbox);
        }


        datePicker.setValue(LocalDate.now());

        for (Alimentazione a : Alimentazione.values())
            alimCombo.getItems().add(a);

        for (Versione v : Versione.values())
            versionCombo.getItems().add(v);

        for (Marca m : Marca.values())
            brandCombo.getItems().add(m);

        alimCombo.getSelectionModel().selectFirst();
        versionCombo.getSelectionModel().selectFirst();
        brandCombo.getSelectionModel().selectFirst();

       /* final ChangeListener<String> change = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                //todo
                //.setText(newValue.replaceAll("[^\\d]", ""));

            }
            enableAdd();
        };*/

        startingFrom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                startingFrom.setText(newValue.replaceAll("[^\\d]", ""));
            calcPrice();
            enableAdd();
        });

        kw.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                kw.setText(newValue.replaceAll("[^\\d]", ""));
            enableAdd();
        });

        cilindrata.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                cilindrata.setText(newValue.replaceAll("[^\\d]", ""));
            enableAdd();
        });

        modello.textProperty().addListener((observable, oldValue, newValue) -> {
            enableAdd();
        });

        unknown = new Image(getClass().getResourceAsStream("/unknown.png"));

        imagination.setImage(unknown);
        imagination.setFitHeight(200);
        imagination.setSmooth(true);
        imagination.setCache(true);
        imagination.setPreserveRatio(true);

        final SpinnerValueFactory<Integer> factoryInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 1);
        final SpinnerValueFactory<Integer> factoryDec = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);

        intPeso.setValueFactory(factoryInt);
        decPeso.setValueFactory(factoryDec);

        needUp = true;
        datePicker.setVisible(false);
    }

    private void calcPrice() {

        int price = 0;

        try {
            price += Integer.parseInt(startingFrom.getText());
        } catch (NumberFormatException num) {
            System.err.println("Empty field");
            //num.printStackTrace();
        }

        for (int i = 0; i < accessori.length; i++)
            if (accessori[i].isSelected())
                price += Accessorio.values()[i].getPrice();

        finalPrice.setText("" + price);
    }

    public void aggiungi(ActionEvent ae) {
        final Auto auto;
        float peso = decPeso.getValue() / 100;
        peso += intPeso.getValue();

        ArrayList<Accessorio> acc = new ArrayList<>();
        for (JFXCheckBox anAccessori : accessori)
            if (anAccessori.isSelected())
                acc.add((Accessorio) anAccessori.getUserData());

        Accessorio[] ac = new Accessorio[acc.size()];
        for (int i = 0; i < acc.size(); i++)
            ac[i] = acc.get(i);

        if (!usedSwitch.isSelected())
            auto = new Auto(brandCombo.getValue(), modello.getText().trim(), new Motore(alimCombo.getValue(), Integer.parseInt(cilindrata.getText()), Integer.parseInt(kw.getText())), new Tipo(versionCombo.getValue(), peso), Integer.parseInt(startingFrom.getText()), ac);
        else {
            auto = new AutoUsata(brandCombo.getValue(), modello.getText().trim(), new Motore(alimCombo.getValue(), Integer.parseInt(cilindrata.getText()), Integer.parseInt(kw.getText())), new Tipo(versionCombo.getValue(), peso), Integer.parseInt(startingFrom.getText()), ac, datePicker.getValue());
            System.err.println("used");
        }
        try {
            GestoreFile.inserimento(auto, GestoreFile.List.onSale);
            System.out.println("OK");
            resetFields();
            Notifications.create()
                    .title("Fatto")
                    .text("Salvato su file")
                    .showInformation();
        } catch (Exception exc) {
            Notifications.create()
                    .title("Attenzione")
                    .text("Il salvataggio su file è fallito")
                    .showWarning();
            System.out.println("am i wrong");
        }
    }

    public void selectImage(ActionEvent ae) {
        if (needUp) {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File file = fileChooser.showOpenDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                imagination.setImage(image);

                btnLoadPic.setStyle("-fx-background-color: red");
                btnLoadPic.setTextFill(Paint.valueOf("white"));
                btnLoadPic.setText("RIMUOVI FOTO");

                needUp = false;

            } catch (IOException ex) {
                System.err.println("Errore nella lettura");
            } catch (NullPointerException nuller) {
                System.err.println("Operazione annullata!");
            }
        } else {
            btnLoadPic.setStyle("-fx-background-color: dodgerblue");
            btnLoadPic.setTextFill(Paint.valueOf("white"));
            btnLoadPic.setText("CARICA FOTO");

            imagination.setImage(unknown);

            needUp = true;
        }
    }

    public void enableDate() {
        datePicker.setVisible(usedSwitch.isSelected());
    }

    private void enableAdd() {
        btnAdd.setDisable(startingFrom.getText().isEmpty() || modello.getText().trim().isEmpty() || kw.getText().isEmpty() || cilindrata.getText().isEmpty());
    }

    public void resetFields() {
        alimCombo.getSelectionModel().selectFirst();
        brandCombo.getSelectionModel().selectFirst();
        versionCombo.getSelectionModel().selectFirst();
        modello.setText("");
        cilindrata.setText("");
        kw.setText("");
        for (CheckBox c : accessori)
            c.setSelected(false);
        startingFrom.setText("");
        usedSwitch.setSelected(false);
        datePicker.setValue(LocalDate.now());
        datePicker.setVisible(false);
    }

}
