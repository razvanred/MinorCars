package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.ResponseClientServer;
import it.minoranza.minorgroup.minorclient.control.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class StageTwo extends Thread {

    private final Socket socket;
    private final List login;
    private volatile boolean run;

    public StageTwo(final Socket socket, final List login) {
        this.login = login;
        this.socket = socket;
        run = true;
    }

    @Override
    public void run() {
        while (run) {
            try {
                while (true) {
                    final InputStream is = socket.getInputStream();
                    System.out.println("see what happens");
                    final Scanner scanner = new Scanner(is);
                    final JSONArray array = new JSONObject(scanner.nextLine()).getJSONArray(ResponseClientServer.listDealers.name());

                    is.close();
                    scanner.close();
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void boomThread(){
        run=false;
    }

}
