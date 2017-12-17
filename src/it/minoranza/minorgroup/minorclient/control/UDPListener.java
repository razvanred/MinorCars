package it.minoranza.minorgroup.minorclient.control;

import it.minoranza.minorgroup.minordealer.Principale;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPListener extends Thread {

    private DatagramSocket datagramSocket;
    private volatile boolean run;


    public UDPListener() {

        run = true;
    }

    @Override
    public void run() {
        while (run) {

            try {

                byte list[] = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(list, list.length);
                datagramSocket.receive(datagramPacket);

                System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));

                Platform.runLater(() -> {
                    Principale.stage.close();

                    FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/"));

                });

                run = false;

            } catch (IOException io) {
                io.printStackTrace();
                run = false;
            }

        }
        System.err.println("closing");

    }

    public void boom() {
        datagramSocket.close();
        run = false;
    }

    public void startOperations(final int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
        start();
    }
}
