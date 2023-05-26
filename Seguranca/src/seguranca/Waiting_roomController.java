package seguranca;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The type Waiting room controller.
 */
public class Waiting_roomController implements Initializable {

    /**
     * The Text.
     */
    @FXML
    public Label text;
    /**
     * The S.
     */
    public Socket s;
    /**
     * The Ois.
     */
    public ObjectInputStream ois;
    /**
     * The Oos.
     */
    public ObjectOutputStream oos;

    /**
     * Startup.
     */
    public void startup() {
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            System.out.println("got oos");
            ois = new ObjectInputStream(s.getInputStream());
            ClientThread ct = new ClientThread(s, text, this, ois, oos);            //criar thread de cliente que espera que seja aceite ou recusado
            System.out.println("bout to start da thread");
            ct.start();
        } catch (Exception e) {
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Change.
     */
    public void change() {
        try {
            Stage stage = (Stage) text.getScene().getWindow();
            FXMLLoader nextscreen = new FXMLLoader(getClass().getResource("finalkeyscreen.fxml"));  //se for aceite passar para a janela final
            Scene methods = new Scene(nextscreen.load());
            stage.setScene(methods);
            finalkeyscreencontroller puerta = nextscreen.getController();
            puerta.startupclient(s, ois, oos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Change.
     *
     * @param bad the bad
     */
    public void change(String bad){
        text.setText(bad);
    }

    /**
     * Gobacky.
     *
     * @param e the e
     * @throws IOException the io exception
     */
    @FXML
    public void gobacky(ActionEvent e) throws IOException{
        try {
            ois.close();
            oos.close();
            s.close();
            
        } catch (Exception ex) {
        }
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) text.getScene().getWindow();
        stage.close();
    }

}
