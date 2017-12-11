package it.minoranza.minorgroup.minorserver.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Socket;

import it.minoranza.minorgroup.commons.model.RequestClientServer;
import it.minoranza.minorgroup.commons.model.RequestDealerServer;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author razva
 */
public class RunVirtualCommunication extends Thread {

    private final Socket s;
    private volatile boolean finish;
    private JSONObject belong;

    public RunVirtualCommunication(final Socket s) {
        this.s = s;
        finish=true;
        System.out.println(" * CONNESSIONE ACCETTATA da "+s.getInetAddress()+":"+s.getPort()+" * ");
    }

    @Override
    public final void run() {

        //int i=0;

        while(finish){
            try {
                InputStream is = s.getInputStream();
                StringWriter buffer = new StringWriter();
                IOUtils.copy(is, buffer);

                System.out.println(buffer.toString());


                buffer.close();
                is.close();

                try{
                    belong=new JSONObject();
                    belong.put(RequestClientServer.ipClient.name(),s.getInetAddress());
                    belong.put(RequestClientServer.ipClient.name(),s.getPort());
                }catch(JSONException exception){
                    System.err.println("Too bad!");
                    //belong.getString(RequestDealerServer.ipAddress.name());
                    belong=new JSONObject(buffer.toString());
                }

                buffer.close();
                is.close();
                s.close();

                //sleep(7000);

                //finish=i++<3;
                finish=false;

                //join();
                //interrupt();

            } catch (IOException exc) {
                System.err.println("Something went wrong during InputStream");
                exc.printStackTrace();
                finish=false;
            }/* catch (InterruptedException exc){
                exc.printStackTrace();
            }*/
        }

    }

    public void refresh(){

    }

    /*public final void setFinish(final boolean finish) {
    this.finish = finish;
    }*/

}
