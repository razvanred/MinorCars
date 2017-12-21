package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.minorclient.control.Main;
import it.minoranza.minorgroup.minordealer.control.GestoreFile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StageThree extends Thread {

    private final Main main;
    private final DatagramSocket socket;
    private final DatagramPacket packet;
    private volatile boolean explosion;

    public StageThree(final Main main, final DatagramSocket socket) {
        this.socket = socket;
        this.main = main;
        byte[] buff = new byte[2048];
        packet = new DatagramPacket(buff, buff.length);

        explosion = false;
    }

    @Override
    public void run() {
        while (!explosion) {

            try {
                socket.receive(packet);

                final JSONObject obj = new JSONObject(new String(packet.getData(), 0, packet.getLength()));
                final JSONArray onSale = obj.getJSONArray(GestoreFile.List.onSale.name());
                final JSONArray sold = obj.getJSONArray(GestoreFile.List.sold.name());
                //main.refreshData(onSale, sold);

            } catch (IOException e) {
                e.printStackTrace();
                explosion = true;
            }

        }
    }


}
