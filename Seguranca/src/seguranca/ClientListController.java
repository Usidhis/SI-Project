package seguranca;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.net.*;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientListController implements Initializable {

    @FXML
    ListView lista;
    @FXML
    Label Label;
    ArrayList<String> clientes = new ArrayList();
    ArrayList<String> ipes = new ArrayList();
    ArrayList<Socket> socas = new ArrayList();
    ConnectionThread ct;
    ServerSocket sc;

    public int porta;

    public void startup(ServerSocket sc) {
        this.sc = sc;
        lista.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {              //tratar de clicks nas opcoes
                System.out.println("clicked on " + lista.getSelectionModel().getSelectedItem());
                try {
                    for (int i = 0; i < lista.getItems().size(); i++) {
                        if (lista.getSelectionModel().getSelectedIndex() != i) {
                            Socket soca = socas.get(i);
                            ObjectOutputStream os = new ObjectOutputStream(soca.getOutputStream());
                            os.writeObject("nope");             //avisar os outros clientes que nao foram escolhidos
                            os.close();
                            soca.close();
                        }
                    }
                    sc.close();
                    Socket soca = socas.get(lista.getSelectionModel().getSelectedIndex());
                    System.out.println(soca);
                    ObjectOutputStream oos = new ObjectOutputStream(soca.getOutputStream());
                    oos.flush();
                    System.out.println("got oos");
                    ObjectInputStream ois = new ObjectInputStream(soca.getInputStream());
                    System.out.println("got ois");

                    oos.writeObject("accepted");
                    System.out.println("doneskies");
                    

                    
                    Stage stage = (Stage) lista.getScene().getWindow();
                    FXMLLoader nextscreen = new FXMLLoader(getClass().getResource("keyshare.fxml"));        //estabelecer ligacao e ir para o proximo ecra
                    Scene methods = new Scene(nextscreen.load());
                    stage.setScene(methods);
                    keyshareController puerta = nextscreen.getController();
                    puerta.startup(soca, ois, oos);
                    
                    

                } catch (Exception e) {
                    System.out.println("oops");
                }
            }
        });
        System.out.println("making da socket");
                  
        ct = new ConnectionThread(sc, lista, this);
        ct.start();

        
        Scene activewindow = lista.getScene();

    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void addclient(String ip, Socket soca) {
        lista.getItems().add(ip);               //adicionar cliente a lista, chamado pela thread
        ipes.add(ip);
        socas.add(soca);
    }
    @FXML
    public void gobacky(ActionEvent e) throws IOException{
        sc.close();
        try {
            for (int i = 0; i < socas.size(); i++) {
                socas.get(i).close();
            }
        } catch (Exception ex) {
        }
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) lista.getScene().getWindow();
        stage.close();
    }

}
