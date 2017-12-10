package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.RequestServer;
import it.minoranza.minorgroup.minorclient.control.Main;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class StageOne extends Thread {

    private InetAddress ip;
    private int port;
    private final Main main;

    public StageOne(final Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            final Socket socket = new Socket(ip, port);
            final OutputStream output = socket.getOutputStream();
            final PrintWriter pw = new PrintWriter(output, true);

            pw.println(RequestServer.listaAuto.name());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    main.itWorked(socket);
                }
            });
            output.close();
            pw.close();

            // pw.close();
            // output.close();
            // socket.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void startOperations(final InetAddress ip, final int port) {
        this.port = port;
        this.ip = ip;
        start();
    }

}
