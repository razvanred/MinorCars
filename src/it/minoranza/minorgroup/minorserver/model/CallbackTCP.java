package it.minoranza.minorgroup.minorserver.model;

import it.minoranza.minorgroup.minorserver.control.Server;

public class CallbackTCP extends Callback {

    public CallbackTCP(final Server server){
        super(server);
    }

    @Override
    public void create() {

    }

    @Override
    public void interrupt() {

    }

    @Override
    public String getType() {
        return "TCP";
    }
}
