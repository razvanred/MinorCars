package it.minoranza.minorgroup.commons.control;

import javafx.scene.control.TableView;
import org.json.JSONArray;

public abstract class AbstractManager {

    protected TableView table;
    protected final JSONArray data;

    protected AbstractManager(final JSONArray data){
        this.data=data;
    }

    public abstract void save();
    public abstract void delete();

    public final JSONArray getData(){
        return data;
    }

    public final void attachTable(final TableView table){
        this.table=table;
    }

}
