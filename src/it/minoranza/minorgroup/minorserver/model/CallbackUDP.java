package it.minoranza.minorgroup.minorserver.model;

import it.minoranza.minorgroup.minorserver.control.Server;
import it.minoranza.minorgroup.minorserver.control.UDPThread;

public class CallbackUDP extends Callback {

    private UDPThread udp;

    public CallbackUDP(final Server server){
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
        return "UDP";
    }
}
