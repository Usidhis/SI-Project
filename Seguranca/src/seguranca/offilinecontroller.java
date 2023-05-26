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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class offilinecontroller implements Initializable {

    @FXML
    TextArea txt;
    Socket soca;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    int metodo;
    boolean server;
    /**
     * Regressar ao menu principal
     * @param e
     * evento do click
     * @throws IOException 
     * se não encontrar o fxml
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
    public void startup(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int metodo, boolean server){
        this.soca = soca;
        this.ois = ois;
        this.oos = oos;
        this.metodo = metodo;
        this.server = server;
        
    }

}
