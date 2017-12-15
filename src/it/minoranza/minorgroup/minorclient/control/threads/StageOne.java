package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.RequestClientServer;
import it.minoranza.minorgroup.minorclient.control.Login;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class StageOne extends Thread {

    private InetAddress ip;
    private int port;
    private final Login login;

    private volatile boolean run;

    public StageOne(final Login login) {
        this.login = login;
        run = true;
    }

    @Override
    public void run() {
        while (run) {
            try {
                JSONObject request=new JSONObject();
                request.put(RequestClientServer.letMeIn.name(),true);
                DatagramSocket ds=new DatagramSocket();
                byte[] message=request.toString().getBytes();

                DatagramPacket packet=new DatagramPacket(message,message.length,ip,port);

                byte[] res=new byte[2048];
                DatagramPacket response=new DatagramPacket(res,res.length);

                ds.send(packet);

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

    public void startOperations(final InetAddress ip, final int port) {
        this.port = port;
        this.ip = ip;
        start();
    }
}
