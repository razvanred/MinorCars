package it.minoranza.minorgroup.minorclient.control.threads;

import it.minoranza.minorgroup.commons.model.requests.ClientToServer;
import it.minoranza.minorgroup.commons.model.requests.ServerToClient;
import it.minoranza.minorgroup.minorclient.control.ListeningServer;
import it.minoranza.minorgroup.minorclient.control.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class StageTwo extends Thread {

    private volatile boolean explosion;
    private final ListeningServer ls;
    private final DatagramPacket packet;
    private final DatagramSocket ss;
    private String dealer, password;

    public StageTwo(final DatagramSocket ss, final DatagramPacket packet, final ListeningServer ls) {
        explosion = false;
        this.ls = ls;
        this.packet = packet;
        this.ss = ss;
    }

    @Override
    public void run() {
        while (!explosion) {
            try {
                final JSONObject object = new JSONObject();
                object.put(ClientToServer.choosenDealer.name(), dealer);
                object.put(ClientToServer.passkey.name(), password);
                byte[] to_send = object.toString().getBytes();

                packet.setData(to_send, 0, to_send.length);

                ss.send(packet);

                byte[] buff = new byte[2048];
                DatagramPacket receive = new DatagramPacket(buff, buff.length);
                ss.receive(receive);

                final JSONObject result = new JSONObject(new String(receive.getData(), 0, receive.getLength()));

                if (result.getBoolean(ServerToClient.ok.name())) {

                    final DatagramSocket r = new DatagramSocket(result.getInt(ServerToClient.message.name()));
                    //new StageThree().start();

                    Platform.runLater(new Runnable() {
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

                                StageThree stageThree = new StageThree(main, r);
                                main.attachThird(stageThree);
                                //StageTwo two = new StageTwo(ds, response, main);
                                //main.attachSecond(two);
                                stage.show();

                                //run = false;
                                //two.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }



            /*System.out.println(new String(packet.getData(),0,packet.getLength()));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                }
            });

            ss.close();
            explosion=true;*/

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                explosion = true;
            }
        }
    }

    public final void startOperations(final String dealer, final String password) {
        this.password = password;
        this.dealer = dealer;
        start();
    }

    public final void boom() {
        if (!ss.isClosed())
            ss.close();
        explosion = true;
    }

}
