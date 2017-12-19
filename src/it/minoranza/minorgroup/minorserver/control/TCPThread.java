package it.minoranza.minorgroup.minorserver.control;

import it.minoranza.minorgroup.minorserver.Principale;
import it.minoranza.minorgroup.minorserver.model.RunVirtualCommunication;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;

import static it.minoranza.minorgroup.minorserver.Principale.dealers;

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
                while(true){
                    try {
                        final Socket accepted=ss.accept();
                        RunVirtualCommunication virtualCommunication = new RunVirtualCommunication(accepted);
                        System.err.println("all right");
                        if (!dealers.contains(virtualCommunication)) {
                            System.err.println("to insert");
                            dealers.add(virtualCommunication);
                            virtualCommunication.start();
                        }else{
                            System.err.println("i have to send message error");
                            OutputStream out=accepted.getOutputStream();
                            PrintWriter wr=new PrintWriter(out,true);

                            //wr.
                        }
                    }catch(JSONException exc){
                        exc.printStackTrace();
                    }
                }


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
