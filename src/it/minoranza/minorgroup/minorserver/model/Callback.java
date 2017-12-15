package it.minoranza.minorgroup.minorserver.model;

import it.minoranza.minorgroup.minorserver.control.Server;

public abstract class Callback {

    protected Server server;

    protected Callback(final Server server){
        this.server=server;
    }

    public abstract void create();
    public abstract void interrupt();
    public abstract String getType();

}
