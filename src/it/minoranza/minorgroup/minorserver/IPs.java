package it.minoranza.minorgroup.minorserver;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;

public class IPs {

    private final InetAddress address;
    private final int port;
    private final String hostname, passkey;

    public IPs(final JSONObject object, final InetAddress address,final int port) throws JSONException {
        this.address = address;
        hostname=object.getString(DealerToServer.nameDealer.name());
        passkey=object.getString(DealerToServer.passkey.name());
        this.port=port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPs)) return false;

        IPs iPs = (IPs) o;

        if (port != iPs.port) return false;
        if (address != null ? !address.equals(iPs.address) : iPs.address != null) return false;
        if (hostname != null ? !hostname.equals(iPs.hostname) : iPs.hostname != null) return false;
        return passkey != null ? passkey.equals(iPs.passkey) : iPs.passkey == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        result = 31 * result + (passkey != null ? passkey.hashCode() : 0);
        return result;
    }

    public final String getHostname(){
        return hostname;
    }
}
