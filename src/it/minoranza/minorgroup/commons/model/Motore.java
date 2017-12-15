package it.minoranza.minorgroup.commons.model;

import it.minoranza.minorgroup.commons.model.enums.Alimentazione;
import org.json.JSONObject;

public class Motore {

    private int cilindrata;
    private int kw;
    private Alimentazione alimentazione;

    public Motore(final JSONObject motore){
        cilindrata=motore.getInt(EngineJSON.cilindrata.name());
        kw=motore.getInt(EngineJSON.kw.name());
        alimentazione=Alimentazione.valueOf(motore.getString(EngineJSON.alimentazione.name()));
    }

    public Motore(final Alimentazione alimentazione,final int cilindrata,final int kw){

        this.kw=kw;
        this.alimentazione=alimentazione;
        this.cilindrata=cilindrata;

    }


    public enum EngineJSON{
        cilindrata,
        kw,
        alimentazione
    }

    public Alimentazione getAlimentazione() {
        return alimentazione;
    }


    public int getCilindrata(){
        return cilindrata;
    }

    public int getKw(){
        return kw;
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

    public JSONObject toJSON(){
        final JSONObject object=new JSONObject();

        object.put(EngineJSON.alimentazione.name(),alimentazione);
        object.put(EngineJSON.cilindrata.name(),cilindrata);
        object.put(EngineJSON.kw.name(),kw);

        return object;
    }


}
