package it.minoranza.minorgroup.minorserver.control;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPThread extends Thread {

    private volatile boolean stop;
    private final ServerSocket ss;
    private final Main main;


    public TCPThread(final Main main) throws IOException {
        this.main = main;
        ss = new ServerSocket(main.getPortTCP());

    }

    @Override
    public void run() {
        while (!stop) {
            try {
                System.out.println("here");
                new RunVirtualCommunication(ss.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
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
