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
import javafx.stage.Stage;

public class Waiting_roomController implements Initializable {

    @FXML
    public Label text;
    public Socket s;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    /**
     * inicializa a waiting room que começa a thread que aguarda que o servidor se responda
     */
    public void startup() {
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            System.out.println("got oos");
            ois = new ObjectInputStream(s.getInputStream());
            ClientThread ct = new ClientThread(this, ois);            //criar thread de cliente que espera que seja aceite ou recusado
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
     * se for aceite avança para o ecrã final e aguarda pela informação do servidor 
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
     * se não for aceite altera o texto do label para informar o cliente que não foi escolhido
     * @param bad 
     * texto para por no label
     */
    public void change(String bad){
        text.setText(bad);
    }
    /**
     * Regressar ao menu principal
     * @param e
     * evento do click
     * @throws IOException 
     * se não encontrar o fxml
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
