package carsFX.model.enums;

import java.io.Serializable;

public enum Accessorio implements Serializable {

    TCS("Traction Auto System", 5000),
    AUTORADIO("Autoradio", 100),
    NAVIGATORE("Navigatore e Bluetooth", 500),
    SEDILI_HOT("Sedili elettrici", 1000),
    STARTandSTOP("Start & Stop", 2000);

    private String descrizione;
    private int price;

    Accessorio(String descrizione, int price) {
        this.descrizione = descrizione;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
