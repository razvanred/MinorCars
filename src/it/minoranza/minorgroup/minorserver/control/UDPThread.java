package it.minoranza.minorgroup.minorserver.control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPThread extends Thread {

    private volatile boolean stop;
    private static DatagramSocket socket;

    public UDPThread() throws IOException{
        socket=new DatagramSocket(4445);
        stop=false;
    }

    @Override
    public void run(){
        while(!stop){

            try{
                byte buff[]=new byte[1024];
                DatagramPacket packet=new DatagramPacket(buff,buff.length);
                socket.receive(packet);
            }catch(IOException io){

            }

        }
    }

    public void refresh(){

    }

}
