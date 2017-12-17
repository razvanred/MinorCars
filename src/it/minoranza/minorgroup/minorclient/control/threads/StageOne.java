package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.RequestClientServer;
import it.minoranza.minorgroup.minorclient.control.ListeningServer;
import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StageOne extends Thread {

   // private InetAddress ip;
    private int port;
    private final ListeningServer ls;

    private volatile boolean run;

    public StageOne(final ListeningServer ls) {
        this.ls = ls;
        run = true;
    }

    @Override
    public void run() {
        while (run) {
            try {
                //JSONObject request=new JSONObject();
                //request.put(RequestClientServer.letMeIn.name(),true);
                DatagramSocket ds=new DatagramSocket(port);
                //byte[] message=RequestClientServer.letMeIn.name().getBytes();

                //DatagramPacket packet=new DatagramPacket(message,message.length);

                byte[] res=new byte[2048];
                DatagramPacket response=new DatagramPacket(res,res.length);

                //ds.send(packet);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        ls.setUpDown(true);
                    }
                });

                while(true){
                    ds.receive(response);
                    System.err.println("GOT IT");
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
                run=false;
            }
        }
    }

    public void startOperations(final int port) {
        this.port = port;
        //this.ip = ip;
        start();
    }
}
