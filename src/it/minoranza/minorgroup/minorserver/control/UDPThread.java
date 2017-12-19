package it.minoranza.minorgroup.minorserver.control;

import it.minoranza.minorgroup.commons.model.requests.ServerToClient;
import it.minoranza.minorgroup.minorserver.Principale;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPThread extends Thread {

    private volatile boolean stop;
    private static DatagramSocket socket;
    private final int portClient;
    //private final int portToListen;

    public UDPThread(final Main main) throws IOException{
        socket=new DatagramSocket(main.getPortUDP());
        socket.setBroadcast(true);
        stop=false;
        portClient=main.getPortUDPClient();
       // portToListen=;
    }

    @Override
    public void run(){
        while(!stop){
            System.out.println("virtual");
            try{
                byte buff[]=new byte[1024];
                DatagramPacket packet=new DatagramPacket(buff,buff.length);
                socket.receive(packet);
            }catch(IOException io){
                io.printStackTrace();
            }

        }
    }

    public final void boom(){
        stop=true;
        socket.close();
    }

    public void refresh() {
        try {
            DatagramSocket toClient = new DatagramSocket();

            JSONArray array = new JSONArray();
            for (int i = 0; i < Principale.dealers.size(); i++)
                array.put(Principale.dealers.get(i).getHostname());

            JSONObject object = new JSONObject();
            object.put(ServerToClient.listDealers.name(), array);

            try {
                byte[] broadcast = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(broadcast, broadcast.length, InetAddress.getByName("255.255.255.255"), portClient);
                toClient.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch(IOException io){
            io.printStackTrace();
        }
    }

}
