package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.RequestClientServer;
import it.minoranza.minorgroup.minorclient.control.Login;
import javafx.application.Platform;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
                final Socket socket = new Socket(ip, port);
                final OutputStream output = socket.getOutputStream();
                final PrintWriter pw = new PrintWriter(output, true);

                pw.println(RequestClientServer.listaAuto.name());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        login.itWorked(socket);
                    }
                });
                output.close();
                pw.close();
                run=false;
                // pw.close();
                // output.close();
                // socket.close();

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
