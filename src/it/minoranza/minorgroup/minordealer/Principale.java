package it.minoranza.minorgroup.minordealer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principale  extends Application{

    public static void main(String[] args) {
        launch(args);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Socket socket = new Socket("192.168.1.100", 2000);
                    final OutputStream output=socket.getOutputStream();
                    final PrintWriter pw=new PrintWriter(output);
                    final JSONObject object=new JSONObject();
                    object.put(RequestClientServer.passwordValidator.name(),"password");

                    pw.println(object);

                    output.close();
                    pw.close();

                    final InputStream in=socket.getInputStream();
                    final Scanner scanner=new Scanner(in);

                    if(scanner.nextBoolean()){
                        final ServerSocket ss=new ServerSocket(2000);
                        try {
                            while (true) {

                            }
                        }catch(IOException io){
                            ss.close();
                        }
                    }

                    in.close();
                    scanner.close();

                }catch(IOException io){
                    io.printStackTrace();
                }
            }
        })*/
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setScene(new Scene(root,500,400));
    }
}
