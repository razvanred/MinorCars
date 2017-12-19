package it.minoranza.minorgroup.commons.model;

import it.minoranza.minorgroup.commons.model.enums.Accessorio;
import it.minoranza.minorgroup.commons.model.enums.Marca;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;

public class AutoUsata extends Auto{

    private LocalDate date;

    public AutoUsata(final Marca marca, final String modello, final Motore motore, final Tipo tipo, final int price, final Accessorio[] accessori, final LocalDate date) {
        super(marca, modello, motore, tipo, price, accessori);
        this.date = date;
    }

    public enum AutoUsataParams{
        date
    }

    public LocalDate getLocalDate() {
        return date;
    }

    @Override
    public JSONObject toJSON(){
        final JSONObject object=super.toJSON();

        object.put(AutoUsataParams.date.name(),date.toString());

        return object;
    }


    public final String getDate() {
        return date.toString();
    }
}
