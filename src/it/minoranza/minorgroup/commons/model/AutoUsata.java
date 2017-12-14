package it.minoranza.minorgroup.commons.model;

import it.minoranza.minorgroup.commons.model.enums.Accessorio;
import it.minoranza.minorgroup.commons.model.enums.Marca;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AutoUsata extends Auto{

    private LocalDate date;

    public AutoUsata(final Marca marca, final String modello, final Motore motore, final Tipo tipo, final int price, final Accessorio[] accessori, final LocalDate date) {
        super(marca, modello, motore, tipo, price, accessori);
        this.date = date;
    }

    public AutoUsata(final JSONObject object){
        super(Marca.valueOf(object.getString(CarJSON.marca.name())),object.getString(CarJSON.modello.name()),new Motore(object.getJSONObject(CarJSON.motore.name())),new Tipo(object.getJSONObject(CarJSON.tipo.name())),object.getInt(CarJSON.price.name()),accessorios(object.getJSONArray(CarJSON.accessori.name())));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.US );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(object.getString(CarJSON.data.name()), formatter);
    }

    public LocalDate getLocalDate() {
        return date;
    }

    private static Accessorio[] accessorios(final JSONArray array){

        final Accessorio[] accessori=new Accessorio[array.length()];

        for(int i=0;i<array.length();i++)
            accessori[i]=Accessorio.valueOf(array.getString(i));

        return accessori;
    }


    public final String getDate() {
        return date.toString();
    }

}
