package it.minoranza.minorgroup.minordealer.control;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import it.minoranza.minorgroup.commons.model.requests.ServerToDealer;
import javafx.application.Platform;
import org.controlsfx.control.Notifications;
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

                JSONObject object=new JSONObject();
                object.put(DealerToServer.passkey.name(),password);
                object.put(DealerToServer.nameDealer.name(),name);
                object.put(DealerToServer.portUDP.name(),portUDP);

                wr.println(object);


                System.out.println("looking ok");

                final InputStream in = socket.getInputStream();
                final Scanner scanner = new Scanner(in);

                JSONObject response = new JSONObject(scanner.nextLine());
                if (response.getBoolean(ServerToDealer.success.name())) {
                    System.err.println("Now to client");
                    UDPThread udp = new UDPThread(portUDP, main);
                    udp.start();
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Notifications.create().title("Attenzione").text(response.getString(ServerToDealer.message.name())).showWarning();
                            main.setUpDown(false);
                        }
                    });
                    socket.close();

                    boom = true;
                    if (!socket.isClosed()) socket.close();
                }
                out.close();
                wr.close();

               /* boom=true;
                socket.close();*/

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
