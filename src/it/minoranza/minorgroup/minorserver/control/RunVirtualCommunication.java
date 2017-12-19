package it.minoranza.minorgroup.minorserver.control;

import it.minoranza.minorgroup.commons.model.requests.ClientToServer;
import it.minoranza.minorgroup.commons.model.requests.ServerToDealer;
import it.minoranza.minorgroup.minorserver.Principale;
import it.minoranza.minorgroup.minorserver.model.ConnectionSaver;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Collections;

import static it.minoranza.minorgroup.minorserver.Principale.dealers;

public class RunVirtualCommunication extends Thread {

    private final Socket socket;
    private final int portUDP;
    private volatile boolean boom;
    private ConnectionSaver virtualCommunication;

    public RunVirtualCommunication(final Socket socket, final int portUDP) throws IOException, JSONException {
        System.out.println("OOOO");
        boom = false;
        this.socket = socket;
        socket.setSoTimeout(0);
        this.portUDP = portUDP;
        virtualCommunication = new ConnectionSaver(socket);
    }

    @Override
    public void run() {
        while (!boom) {
            try {
                System.err.println("all right");
                if (!dealers.contains(virtualCommunication)) {
                    System.err.println("to insert");
                    dealers.add(virtualCommunication);

                    Main.udp.refresh();

                    DatagramSocket socket = new DatagramSocket();
                    byte[] buff = new byte[2048];
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);

                    socket.receive(packet);
                    JSONObject result = new JSONObject(new String(packet.getData(), 0, packet.getLength()));
                    String which = result.getString(ClientToServer.choosenDealer.name());

                    if (Principale.dealers.get(Collections.binarySearch(Principale.dealers, which)).getPassword().compareTo(result.getString(ClientToServer.passkey.name())) != 0)
                        virtualCommunication.startOperations();

                } else {
                    System.err.println("i have to send message error");
                    OutputStream out = socket.getOutputStream();
                    PrintWriter wr = new PrintWriter(out, true);
                    JSONObject object = new JSONObject();
                    object.put(ServerToDealer.success.name(), false);
                    object.put(ServerToDealer.message.name(), "Hostname già presente nel server");

                    wr.println(object);
                    wr.close();

                    //stop=true;
                    //breaker=true;

                    out.close();

                }
                boom = true;

            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public final void explode() {
        boom = true;
    }
}
