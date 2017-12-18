package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.requests.DealerToServer;
import it.minoranza.minorgroup.minorclient.control.ListeningServer;
import it.minoranza.minorgroup.minorclient.control.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StageOne extends Thread {

   // private InetAddress ip;
    private int port;
    private final ListeningServer ls;

    private volatile boolean run;

    public StageOne(final ListeningServer ls) {
        this.ls = ls;
        run = true;
    }

    @Override
    public void run() {
        while (run) {
            try {
                //JSONObject request=new JSONObject();
                //request.put(RequestClientServer.letMeIn.name(),true);
                DatagramSocket ds=new DatagramSocket(port);
                //byte[] message=RequestClientServer.letMeIn.name().getBytes();

                //DatagramPacket packet=new DatagramPacket(message,message.length);

                byte[] res=new byte[2048];
                DatagramPacket response=new DatagramPacket(res,res.length);

                //ds.send(packet);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        ls.setUpDown(true);
                    }
                });

                while(true){
                    ds.receive(response);
                    final String json=new String(response.getData(),0,response.getLength());

                    try{
                        //JSONArray object=new JSONObject(json).getJSONArray(ResponseClientServer.listDealers.name());
                        JSONArray array=new JSONArray();
                        JSONObject object=new JSONObject(json);
                        array.put(object.getString(DealerToServer.nameDealer.name()));
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){

                                ls.changeList(array);
                                Stage stage=new Stage();
                                try {
                                    ls.close();
                                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/it/minoranza/minorgroup/minorclient/view/main.fxml"));
                                    Parent root=loader.load();
                                    stage.setScene(new Scene(root,1240, 850));
                                    Main main=loader.getController();
                                    StageTwo two=new StageTwo(ds,response,main);
                                    main.attachSecond(two);
                                    stage.show();

                                    run=false;
                                    two.start();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }catch(JSONException exc){
                        exc.printStackTrace();
                    }

                    //System.err.println("GOT IT");
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
                run=false;
            }
        }
    }

    public void startOperations(final int port) {
        this.port = port;
        //this.ip = ip;
        start();
    }
}
