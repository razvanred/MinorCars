package it.minoranza.minorgroup.minorclient.control;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class UDPListener extends Thread {

    private DatagramSocket datagramSocket;
    private volatile boolean run;


    public UDPListener(){

        run=true;
    }

    @Override
    public void run(){
        while(run){

            try{

                byte list[]=new byte[1024];
                DatagramPacket datagramPacket=new DatagramPacket(list,list.length);
                datagramSocket.receive(datagramPacket);

                System.out.println(new String(datagramPacket.getData(),0,datagramPacket.getLength()));


            }catch(IOException io){

            }

        }
        datagramSocket.close();
    }

    public void boom(){
        run=false;
    }

    public void startOperations(final int port) throws SocketException {
        datagramSocket=new DatagramSocket(port);
        start();
    }
}
