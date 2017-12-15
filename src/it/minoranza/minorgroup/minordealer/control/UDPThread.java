package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.Auto;
import org.json.JSONArray;

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

    public UDPThread(InetAddress address, int port, Main main) throws IOException {
        socket = new DatagramSocket(4445);

        this.address = address;
        this.port = port;

        this.main = main;

        stop = false;
    }

    @Override
    public void run() {
        while (!stop) {

            try {
                ArrayList<Auto> read = GestoreFile.read(GestoreFile.List.onSale);
                JSONArray array = new JSONArray();
                for (int i = 0; i < read.size(); i++)
                    array.put(read.get(i).toJSON());

                byte[] buff = array.toString().getBytes();

                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
