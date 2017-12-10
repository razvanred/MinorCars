package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.RequestServer;
import it.minoranza.minorgroup.commons.ResponseServer;
import it.minoranza.minorgroup.minorclient.control.Login;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class StageTwo extends Thread {

    private final Socket socket;
    private final Login login;
    private String password;

    public StageTwo(final Socket socket,final Login login){
        this.socket=socket;
        this.login=login;
    }

    @Override
    public void run(){
        try{
            final OutputStream output = socket.getOutputStream();
            final PrintWriter pw = new PrintWriter(output,true);
            JSONObject object=new JSONObject();
            object.put(RequestServer.checkPassword.name(),password);

            pw.println(object);
            pw.close();
            output.close();

            final Scanner scanner=new Scanner(socket.getInputStream());
            object=new JSONObject(scanner.nextLine());
            scanner.close();

            final Socket communication=new Socket(InetAddress.getByName(object.getString(ResponseServer.ipDealer.name())),object.getInt(ResponseServer.portDealer.name()));
            socket.close();
        }catch(IOException exc){

        }
    }

    public void start(final String password){
        this.password=password;
        start();
    }

}
