package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPThread extends Thread {

    private final Socket socket;
    private Main main;
    private volatile boolean boom;
    private final String name,password;

    public TCPThread(final Main main) throws IOException {
        socket=new Socket(main.getAddress(),main.getPort());
        socket.setSoTimeout(0);
        boom=false;
        name=main.getName().trim();
        password=main.getPasskey();
    }


    @Override
    public void run(){
        while(!boom) {
            try {
                final OutputStream out = socket.getOutputStream();
                final PrintWriter wr = new PrintWriter(out,true);

                final InputStream in=socket.getInputStream();
                final Scanner scanner=new Scanner(in);

                JSONObject object=new JSONObject();
                object.put(DealerToServer.passkey.name(),password);
                object.put(DealerToServer.nameDealer.name(),name);

                wr.println(object);


                System.out.println("looking ok");

                //System.out.println(scanner.nextLine());
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