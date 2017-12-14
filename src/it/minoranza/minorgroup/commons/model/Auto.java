package it.minoranza.minorgroup.commons.model;

import it.minoranza.minorgroup.commons.model.enums.Accessorio;
import it.minoranza.minorgroup.commons.model.enums.Alimentazione;
import it.minoranza.minorgroup.commons.model.enums.Marca;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class Auto implements Serializable {

    private final String modello;
    private final Marca marca;
    private final Motore motore;
    private final Tipo tipo;
    private final int price;
    private final Accessorio[] accessori;

    public Auto(final Marca marca, final String modello, final Motore motore, final Tipo tipo, final int price, final Accessorio[] accessori) {
        this.modello = modello;
        this.motore = motore;
        this.tipo = tipo;
        this.marca = marca;
        this.price = price;
        this.accessori = accessori;
    }

    public Auto(final JSONObject object) throws JSONException{
        this.modello=object.getString(CarJSON.modello.name());
        motore=new Motore(object.getJSONObject(CarJSON.motore.name()));
        marca= Marca.valueOf(object.getString(CarJSON.marca.name()));

        tipo=new Tipo(object.getJSONObject(CarJSON.tipo.name()));
        JSONArray array=new JSONArray(object.getJSONArray(CarJSON.accessori.name()));
        accessori=new Accessorio[array.length()];

        for(int i=0;i<array.length();i++)
            accessori[i]=Accessorio.valueOf(array.getString(i));

        price=object.getInt(CarJSON.price.name());
    }

    public final Marca getMarca() {
        return marca;
    }

    public final Motore getMotore() {
        return motore;
    }

    public final String getModello() {
        return modello;
    }

    public final Tipo getTipo() {
        return tipo;
    }

    public final boolean isNeo() {
        return motore.getKw() <= 70 && motore.getKw() / tipo.getTonn() <= 55;
    }

    public final int getPrice() {
        return price;
    }

    public final Alimentazione getAlimentazione() {
        return motore.getAlimentazione();
    }

    public final Accessorio[] getAccessori() {
        return accessori;
    }

    public final int getTotalPrice() {
        int price = this.price;

        for (Accessorio a : accessori)
            price += a.getPrice();

        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auto)) return false;

        final Auto auto = (Auto) o;

        boolean acc = true;
        for (int i = 0; i < accessori.length; i++)
            if (accessori[i] != auto.accessori[i])
                acc = false;

        return price == auto.price && modello.compareTo(auto.modello) == 0 && marca == auto.marca && motore.equals(auto.motore) && tipo.equals(auto.tipo) && acc;
    }

    @Override
    public int hashCode() {
        int result = modello != null ? modello.hashCode() : 0;
        result = 31 * result + (marca != null ? marca.hashCode() : 0);
        result = 31 * result + (motore != null ? motore.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + Arrays.hashCode(accessori);
        return result;
    }

    public enum CarJSON {
        marca,
        modello,
        motore,
        tipo,
        accessori,
        price,
        data
    }

}


