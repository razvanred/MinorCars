package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.RequestClientServer;
import it.minoranza.minorgroup.commons.model.ResponseClientServer;
import it.minoranza.minorgroup.minorclient.control.List;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class StageThree extends Thread {

    private final Socket socket;
    private final List login;
    private String password;

    public StageThree(final Socket socket, final List login){
        this.socket=socket;
        this.login=login;
    }

    @Override
    public void run(){
        try{
            final OutputStream output = socket.getOutputStream();
            final PrintWriter pw = new PrintWriter(output,true);
            JSONObject object=new JSONObject();
            object.put(RequestClientServer.checkPassword.name(),password);

            pw.println(object);
            pw.close();
            output.close();

            final Scanner scanner=new Scanner(socket.getInputStream());
            object=new JSONObject(scanner.nextLine());
            scanner.close();

            final Socket communication=new Socket(InetAddress.getByName(object.getString(ResponseClientServer.ipDealer.name())),object.getInt(ResponseClientServer.portDealer.name()));
            socket.close();
        }catch(IOException exc){

        }
    }

    public void start(final String password){
        this.password=password;
        start();
    }

}
