package it.minoranza.minorgroup.minorserver.model;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import it.minoranza.minorgroup.minorserver.control.Main;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Socket;


public class RunVirtualCommunication extends Thread {

    private final Socket s;
    private volatile boolean finish;
    private final String hostname, password;
    private final InputStream is;
    private final StringWriter buffer;
    private final int portUDP;


    public RunVirtualCommunication(final Socket s) throws IOException, JSONException {
        this.s = s;

        finish = true;

        is = s.getInputStream();
        buffer = new StringWriter();
        IOUtils.copy(is, buffer);

        final JSONObject object = new JSONObject(buffer.toString());
        hostname = object.getString(DealerToServer.nameDealer.name());
        password = object.getString(DealerToServer.passkey.name());
        portUDP=object.getInt(DealerToServer.portUDP.name());


        // System.out.println(" * CONNESSIONE ACCETTATA da "+s.getInetAddress()+":"+s.getPort()+" * ");
    }

    @Override
    public final void run() {

        //int i=0;

        while (finish) {

            System.out.println("virtual");
            //try {

            Main.udp.refresh();

            finish = false;

            //join();
            //interrupt();

            /*} catch (IOException exc) {
                System.err.println("Something went wrong during InputStream");
                exc.printStackTrace();
                finish=false;
            }*//* catch (InterruptedException exc){
                exc.printStackTrace();
            }*/
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunVirtualCommunication that = (RunVirtualCommunication) o;

        return hostname != null ? hostname.equals(that.hostname) : that.hostname == null;
    }

    @Override
    public int hashCode() {
        return hostname != null ? hostname.hashCode() : 0;
    }

    public void refresh() {

    }

    public final String getHostname() {
        return hostname;
    }


    /*public final void setFinish(final boolean finish) {
    this.finish = finish;
    }*/

}
