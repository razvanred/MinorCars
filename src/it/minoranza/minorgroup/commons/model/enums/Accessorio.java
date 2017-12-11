package it.minoranza.minorgroup.commons.model.enums;

public enum Accessorio {

    TCS("Traction Auto System", 90000),
    AUTORADIO("Autoradio", 4),
    NAVIGATORE("Navigatore e Bluetooth", 10000000),
    SEDILI_HOT("Sedili elettrici", 1);

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
