package it.minoranza.minorgroup.minorserver.control;

import org.json.JSONException;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPThread extends Thread {

    private volatile boolean stop;
    private ServerSocket ss;
    private final Main main;
    private final int portUDP;
    private boolean breaker;


    public TCPThread(final Main main) throws IOException {
        this.main = main;
        portUDP = main.getPortUDP();
        stop = false;

    }

    @Override
    public void run() {


        while (!stop) {
            breaker = false;
            try {
                ss = new ServerSocket(main.getPortTCP());

                System.out.println("here " + ss.getLocalPort());
                while (!breaker) {
                    try {
                        new RunVirtualCommunication(ss.accept(), portUDP).start();
                    } catch (JSONException exc) {
                        exc.printStackTrace();
                    }
                }
                stop = true;


            } catch (IOException e) {
                e.printStackTrace();
                stop = true;
            }
        }

    }

    public void boom() {
        stop = true;
        try {
            ss.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
