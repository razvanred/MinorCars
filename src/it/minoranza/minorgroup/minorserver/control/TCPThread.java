package it.minoranza.minorgroup.minorserver.control;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPThread extends Thread {

    private volatile boolean stop;
    private ServerSocket ss;
    private final Main main;


    public TCPThread(final Main main) throws IOException {
        this.main = main;


        stop=false;

    }

    @Override
    public void run() {
        while (!stop) {

            try {
                ss = new ServerSocket(main.getPortTCP());
                System.out.println("here "+ss.getLocalPort());

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
