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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class keyshareController implements Initializable {
    @FXML
    RadioButton PBKDF2;
    @FXML
    RadioButton Puzzles;
    @FXML
    RadioButton Puzzles1;
    
    @FXML
    RadioButton Hellman;
    @FXML
    RadioButton RSA;
    Socket soca;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    @FXML
    Label lab;
    @FXML
    public void gobacky(ActionEvent e) throws IOException{
        try {
            ois.close();
            oos.close();
            soca.close();
        } catch (Exception ex) {
        }
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) RSA.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void gonexty(ActionEvent e) throws IOException{          //selecao de um metodo de troca de chaves
        int metodo;
        if (Puzzles.isSelected()) {
            metodo = 1;
        }
        else if (Puzzles1.isSelected()) {
            metodo = 2;
        }
        else if (Hellman.isSelected()) {
            metodo = 3;
        }
        else if (RSA.isSelected()) {
            metodo = 4;
        }
        else{
            lab.setText("escolhe uma");
            return;
        }
        
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("finalkeyscreen.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) RSA.getScene().getWindow();
        stage.close();
        finalkeyscreencontroller canais = themenu.getController();
        canais.startup(soca, ois, oos, metodo);
            
    }
    

    /**
     * Initializes the controller class.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    public void startup(Socket soca, ObjectInputStream ois, ObjectOutputStream oos){
        this.soca = soca;
        this.ois = ois;
        this.oos = oos;
        
        
    }

}
