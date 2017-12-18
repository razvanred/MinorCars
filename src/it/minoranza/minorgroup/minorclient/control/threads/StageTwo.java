package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.minorclient.control.Main;
import javafx.application.Platform;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StageTwo extends Thread {

    private volatile boolean explosion;
    private final Main main;
    private final DatagramPacket packet;
    private final DatagramSocket ss;

    public StageTwo(final DatagramSocket ss, final DatagramPacket packet,final Main main){
        explosion=false;
        this.main=main;
        this.packet=packet;
        this.ss=ss;
    }

    @Override
    public void run(){
        while(!explosion){

            System.out.println(new String(packet.getData(),0,packet.getLength()));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                }
            });

            ss.close();
            explosion=true;

        }
    }

    public final void boom(){
        if(!ss.isClosed())
            ss.close();
        explosion=true;
    }

}
