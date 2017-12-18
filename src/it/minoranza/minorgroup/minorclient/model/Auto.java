package carsFX.model;

import carsFX.model.enums.Accessorio;
import carsFX.model.enums.Alimentazione;
import carsFX.model.enums.Marca;

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

        Auto auto = (Auto) o;

        /*if (price != auto.price) return false;
        if (modello != null ? !modello.equals(auto.modello) : auto.modello != null) return false;
        if (marca != auto.marca) return false;
        if (motore != null ? !motore.equals(auto.motore) : auto.motore != null) return false;
        if (tipo != null ? !tipo.equals(auto.tipo) : auto.tipo != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(accessori, auto.accessori);*/

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
}
