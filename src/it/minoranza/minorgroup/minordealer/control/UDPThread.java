package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.SendDealerData;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPThread extends Thread {

    private volatile boolean stop;
    private static DatagramSocket socket;

    private InetAddress address;
    private int port;
    private Main main;
    private String name,passkey;

    public UDPThread(InetAddress address, int port, final Main main) throws IOException {
        socket = new DatagramSocket(4445);

        this.address = address;
        this.port = port;

        this.main = main;
        name=main.getName();
        passkey=main.getPasskey();

        stop = false;
    }

    @Override
    public void run() {
        System.out.println("here");

        while (!stop) {

            try {
                ArrayList<Auto> read = GestoreFile.read(GestoreFile.List.onSale);
                JSONObject object=new JSONObject();
                JSONArray array = new JSONArray();
                for (Auto aRead : read) array.put(aRead.toJSON());
                object.put(SendDealerData.data.name(),array);
                object.put(SendDealerData.nameDealer.name(),name);
                object.put(SendDealerData.passkey.name(),passkey);

                byte[] buff = object.toString().getBytes();

                DatagramPacket packet = new DatagramPacket(buff, buff.length, address, port);
                socket.send(packet);

                stop=true;

            } catch (Exception e) {
                e.printStackTrace();
                stop = true;
            }

        }
    }


}
