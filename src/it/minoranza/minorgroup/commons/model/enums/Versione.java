package it.minoranza.minorgroup.commons.model.enums;

public enum Versione {

    BREAK("Famigliare"),
    UTILITARIA("Utilitaria"),
    BERLINA("Berlina"),
    SUV("Crossover"),
    SUPERCAR("Supercar"),
    HYPERCAR("Hypercar");

    private String descrizione;

    Versione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

}
