package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.requests.ServerToClient;
import it.minoranza.minorgroup.minorclient.control.ListeningServer;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class StageOne extends Thread {

    // private InetAddress ip;
    private int port;
    private final ListeningServer ls;
    private Thread thread;
    private DatagramPacket response;
    private DatagramSocket ds;

    private volatile boolean run;

    public StageOne(final ListeningServer ls) {
        this.ls = ls;
        run = true;
        //ds = new DatagramSocket(port);
    }

    @Override
    public void run() {
        while (run) {
            try {
                System.out.println("PORTA USATA " + port);

                byte[] res = new byte[2048];
                response = new DatagramPacket(res, res.length);

                ds.receive(response);

                //ds.send(packet);
                /*Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ls.setUpDown(true);
                    }
                });*/

                final JSONArray array = new JSONObject(new String(response.getData(), 0, response.getLength())).getJSONArray(ServerToClient.listDealers.name());
                Platform.runLater(() -> ls.changeList(array));


                //JSONObject object=new JSONObject(new String(response.getData(),0,response.getLength()));
                //String title=object.getString(DealerToServer.nameDealer.name());

                /*Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        //ls.changeList(array);
                        Stage stage = new Stage();
                        try {
                            ls.close();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/minoranza/minorgroup/minorclient/view/main.fxml"));
                            Parent root = loader.load();
                            stage.setScene(new Scene(root, 1240, 850));
                            Main main = loader.getController();
                            StageTwo two = new StageTwo(ds, response, main);
                            main.attachSecond(two);
                            stage.show();

                            run = false;
                            two.start();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });*

                /*thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                ds.receive(response);
                                final String json = new String(response.getData(), 0, response.getLength());

                                try {
                                    //JSONArray object=new JSONObject(json).getJSONArray(ServerToClient.listDealers.name());
                                    JSONArray array = new JSONObject(json).getJSONArray(ServerToClient.listDealers.name());/*new JSONArray();
                                    JSONObject object = new JSONObject(json);

                                    array.put(object.getString(DealerToServer.nameDealer.name()));

                                    /*Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            ls.changeList(array);
                                        }
                                    });

                        /*
                                } catch (JSONException exc) {
                                    exc.printStackTrace();
                                }

                                //System.err.println("GOT IT");


                            }
                        } catch (IOException io) {
                            io.printStackTrace();
                        }


                    }
                });

                thread.start();*/
            } catch (IOException ioException) {
                ioException.printStackTrace();
                run = false;
            }
        }
    }

    public final DatagramPacket getPacket() {
        return response;
    }

    public final void explode() {
        if (thread != null && thread.isAlive())
            thread.interrupt();
        run = false;
    }

    public final DatagramSocket getDatagramSocket() {
        return ds;
    }

    public void startOperations(final int port) throws SocketException {
        this.port = port;
        ds = new DatagramSocket(port);
        start();
    }
}
