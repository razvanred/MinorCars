package carsFX.model.enums;

import java.io.Serializable;

public enum Versione implements Serializable {

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

    @Override
    public String toString() {
        return descrizione;
    }

}
