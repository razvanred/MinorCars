package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.control.AbstractManager;
import org.json.JSONArray;

public class DealerManager extends AbstractManager {


    public DealerManager(){
        //TODO check and read from file

        super(new JSONArray());
    }

    @Override
    public void save() {

    }

    @Override
    public void delete(){

    }
}
