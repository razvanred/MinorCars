package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.Auto;
import it.minoranza.minorgroup.commons.model.requests.DealerToClient;
import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import it.minoranza.minorgroup.minordealer.Principale;
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
    private final int port, portClient;
    private Main main;

    public UDPThread(final int port, final int portClient, final Main main) throws IOException {
        socket = new DatagramSocket(port);
        socket.setBroadcast(true);

        this.address = address;
        this.port = port;
        this.portClient = portClient;
        this.main = main;
       /* name=main.getName();
        passkey=main.getPasskey();*/

        stop = false;
    }

    @Override
    public void run() {

        while (!stop) {
            System.out.println("here UDP");
            try {
                ArrayList<Auto> read = GestoreFile.read(GestoreFile.List.onSale);
                JSONObject object=new JSONObject();
                JSONArray array = new JSONArray();
                for (Auto aRead : read) array.put(aRead.toJSON());
                object.put(DealerToClient.onSale.name(), array);

                read = GestoreFile.read(GestoreFile.List.sold);
                array = new JSONArray();

                for (Auto aRead : read) array.put(aRead.toJSON());
                object.put(DealerToClient.sold.name(), array);

                object.put(DealerToServer.nameDealer.name(), Principale.dealerName);
                //object.put(DealerToServer.passkey.name(),passkey)

                byte[] buff = object.toString().getBytes();

                DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("255.255.255.255"), port);
                socket.send(packet);

                stop=true;

            } catch (Exception e) {
                e.printStackTrace();
                stop = true;
            }

        }
    }


}
