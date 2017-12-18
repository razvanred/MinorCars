package it.minoranza.minorgroup.commons.model;


import it.minoranza.minorgroup.minorclient.model.enums.Accessorio;
import it.minoranza.minorgroup.minorclient.model.enums.Marca;

import java.io.Serializable;
import java.time.LocalDate;

public class AutoUsata extends Auto implements Serializable {

    private LocalDate date;

    public AutoUsata(final Marca marca, final String modello, final Motore motore, final Tipo tipo, final int price, final Accessorio[] accessori, final LocalDate date) {
        super(marca, modello, motore, tipo, price, accessori);
        this.date = date;
    }

    public LocalDate getLocalDate() {
        return date;
    }


    public final String getDate() {
        return date.toString();
    }
}
