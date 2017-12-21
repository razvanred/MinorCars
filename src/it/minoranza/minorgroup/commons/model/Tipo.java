package it.minoranza.minorgroup.commons.model;

import it.minoranza.minorgroup.commons.model.enums.Versione;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Tipo implements Serializable {

    private Versione versione;
    private float tonn;

    public enum TipoParams{
        versione,
        tonn
    }

    public Tipo(final JSONObject object) throws JSONException {
        versione = Versione.valueOf(object.getString(TipoParams.versione.name()));
        tonn = object.getFloat(TipoParams.tonn.name());
    }

    public Tipo(Versione versione, float peso) {
        this.versione = versione;
        this.tonn = peso;
    }

    public float getTonn() {
        return tonn;
    }

    public String getFormattedTonn() {
        return new DecimalFormat("#.##").format(tonn);
    }

    public Versione getVersione() {
        return versione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tipo)) return false;

        Tipo tipo = (Tipo) o;

        if (Float.compare(tipo.tonn, tonn) != 0) return false;
        return versione == tipo.versione;
    }

    @Override
    public int hashCode() {
        int result = versione != null ? versione.hashCode() : 0;
        result = 31 * result + (tonn != +0.0f ? Float.floatToIntBits(tonn) : 0);
        return result;
    }

    public final JSONObject toJSON(){
        final JSONObject object=new JSONObject();
        object.put(TipoParams.versione.name(),versione.name());
        object.put(TipoParams.tonn.name(),tonn);

        return object;
    }
}
