package it.minoranza.minorgroup.minorserver;

import it.minoranza.minorgroup.minorserver.control.RunVirtualCommunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Principale {

    private final static int PORT=2000;

    public static void main(String[] args) {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    final ServerSocket ss = new ServerSocket(PORT);
                    while(true)
                        new RunVirtualCommunication(ss.accept()).start();
                }catch(IOException io){
                    io.printStackTrace();
                }
            }
        }).start();

    }
}
