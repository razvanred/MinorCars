package it.minoranza.minorgroup.minorclient.control;

import it.minoranza.minorgroup.minorclient.control.threads.StageOne;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    private final StageOne thread;
    private Parent rootIPServer,rootLogin;
    private IPServer ipController;
    private Login loginController;

    @FXML
    private BorderPane master;

    @FXML
    private Label lblDescription;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        master.setCenter(rootIPServer);
        ipController.attachThread(thread);
    }

    public Main(){
        thread = new StageOne(this);
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("../view/ipserver.fxml"));
            rootIPServer=load.load();
            ipController=load.getController();


            load=new FXMLLoader(getClass().getResource("../view/login.fxml"));
            rootLogin=load.load();
            loginController=load.getController();

        }catch(IOException io){
            io.printStackTrace();
        }
    }

    public void itWorked(final Socket socket){
        loginController.attachSocket(socket);
        master.setCenter(rootLogin);
    }
}
