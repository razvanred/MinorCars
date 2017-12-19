package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPThread extends Thread {

    private Socket socket;
    private Main main;
    private volatile boolean boom;
    private final String name,password;
    private final InetAddress address;
    private final int portTCP,portUDP;

    public TCPThread(final Main main) throws IOException {

        address=main.getAddress();
        portTCP=main.getPortTCP();
        portUDP=main.getPortUDP();

        boom=false;
        name=main.getName().trim();
        password=main.getPasskey();
    }


    @Override
    public void run(){
        while(!boom) {
            try {
                socket=new Socket(address,portTCP);
                socket.setSoTimeout(0);

                final OutputStream out = socket.getOutputStream();
                final PrintWriter wr = new PrintWriter(out,true);

                final InputStream in=socket.getInputStream();
                final Scanner scanner=new Scanner(in);

                JSONObject object=new JSONObject();
                object.put(DealerToServer.passkey.name(),password);
                object.put(DealerToServer.nameDealer.name(),name);
                object.put(DealerToServer.portUDP.name(),portUDP);

                wr.println(object);


                System.out.println("looking ok");

                System.out.println(scanner.nextLine());
                boom=true;
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                boom=true;
            }
        }
    }

    public final void explode() throws IOException{
        boom=true;
        socket.close();
    }

}
