package carsFX.model;

import carsFX.model.enums.Accessorio;
import carsFX.model.enums.Alimentazione;
import carsFX.model.enums.Versione;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.image.ImageView;

public class RowAuto {

    private final ImageView logo;
    private final JFXCheckBox neo;
    private final String peso, data, modello;
    private final Versione versione;
    private final Alimentazione alimentazione;
    private final Auto a;
    private int eurPrice, kw, cilindrata;

    public RowAuto(final Auto auto) {

        a = auto;

        logo = auto.getMarca().getLogo();
        logo.setFitWidth(35);
        logo.setSmooth(true);
        logo.setCache(true);
        logo.setPreserveRatio(true);

        neo = new JFXCheckBox();
        neo.setDisable(true);
        neo.setSelected(auto.isNeo());

        peso = auto.getTipo().getFormattedTonn();
        eurPrice = auto.getPrice();

        for (Accessorio a : auto.getAccessori())
            eurPrice += a.getPrice();

        if (auto instanceof AutoUsata)
            data = ((AutoUsata) auto).getDate();
        else
            data = "-";

        versione = auto.getTipo().getVersione();
        modello = auto.getModello();

        kw = auto.getMotore().getKw();
        cilindrata = auto.getMotore().getCilindrata();

        alimentazione = auto.getAlimentazione();

    }

    public ImageView getLogo() {
        return logo;
    }

    public String getPeso() {
        return peso;
    }

    public String getData() {
        return data;
    }

    public String getModello() {
        return modello;
    }

    public Versione getVersione() {
        return versione;
    }

    public int getEurPrice() {
        return eurPrice;
    }

    public int getKw() {
        return kw;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }

    public JFXCheckBox getNeo() {
        return neo;
    }

    public final Auto getA() {
        return a;
    }

}
