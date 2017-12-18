package carsFX.model;

import carsFX.model.enums.Alimentazione;

import java.io.Serializable;

public class Motore implements Serializable {

    private int cilindrata;
    private int kw;
    private Alimentazione alimentazione;

    public Motore(final Alimentazione alimentazione, final int cilindrata, final int kw) {
        this.alimentazione = alimentazione;
        this.cilindrata = cilindrata;
        this.kw = kw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motore)) return false;

        Motore motore = (Motore) o;

        if (cilindrata != motore.cilindrata) return false;
        if (kw != motore.kw) return false;
        return alimentazione == motore.alimentazione;
    }

    @Override
    public int hashCode() {
        int result = cilindrata;
        result = 31 * result + kw;
        result = 31 * result + (alimentazione != null ? alimentazione.hashCode() : 0);
        return result;
    }

    public final int getKw() {
        return kw;
    }

    public final int getCilindrata() {
        return cilindrata;
    }

    public final Alimentazione getAlimentazione() {
        return alimentazione;
    }
}
