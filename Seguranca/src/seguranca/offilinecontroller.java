package seguranca;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * The type Offilinecontroller.
 */
public class offilinecontroller implements Initializable {

    /**
     * The Txt.
     */
    @FXML
    TextArea txt;
    /**
     * The Soca.
     */
    Socket soca;
    /**
     * The Ois.
     */
    ObjectInputStream ois;
    /**
     * The Oos.
     */
    ObjectOutputStream oos;
    /**
     * The Metodo.
     */
    int metodo;
    /**
     * The Server.
     */
    boolean server;

    /**
     * Gobacky.
     *
     * @param e the e
     * @throws IOException the io exception
     */
    @FXML
    public void gobacky(ActionEvent e) throws IOException{
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) txt.getScene().getWindow();
        stage.close();
        ois.close();
        oos.close();
        soca.close();
    }

    /**
     * Gonexty.
     *
     * @param e the e
     * @throws IOException the io exception
     */
    @FXML
    public void gonexty(ActionEvent e) throws IOException{
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("finalkeyscreen.fxml"));
        Stage stage = (Stage) txt.getScene().getWindow();
        stage.setScene(new Scene(themenu.load()));
        finalkeyscreencontroller canais = themenu.getController();
        if (server) 
            canais.startupserverpbk(soca, ois, oos, metodo, txt.getText());
        
        else
            canais.startupclientpbk(soca, ois, oos, metodo, txt.getText());
            
    }
    

    /**
     * Initializes the controller class.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    /**
     * Startup.
     *
     * @param soca   the soca
     * @param ois    the ois
     * @param oos    the oos
     * @param metodo the metodo
     * @param server the server
     */
    public void startup(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int metodo, boolean server){
        this.soca = soca;
        this.ois = ois;
        this.oos = oos;
        this.metodo = metodo;
        this.server = server;
        
    }

}
