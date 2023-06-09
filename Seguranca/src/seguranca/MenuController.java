package seguranca;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    Label warning;
    @FXML
    TextField ip;
    @FXML
    TextField porta;
    @FXML
    Button next;
    @FXML
    RadioButton server;
    @FXML
    RadioButton client;
    
    /**
     * Desativa caixas de texto com base no método de operação
     * 
     * @param e 
     * click event
     */
    @FXML
    public void be_a_server(ActionEvent e) {
        ip.setDisable(true);
        porta.setDisable(false);
        System.out.println("im a server");
    }
    /**
     * Desativa caixas de texto com base no tipo de operação
     * 
     * @param e 
     * click event
     */
    @FXML
    public void be_a_client(ActionEvent e) {            //desativa os campos desnecessarios
        ip.setDisable(false);
        porta.setDisable(false);
        System.out.println("im a client");
        
    }

    /**
     * procede para a proxima cena com base no método de operação
     * 
     * @param e 
     * click event
     */
    @FXML
    public void next(ActionEvent e) throws IOException {
        try {
            warning.setText("Select the operation method");
            if (!porta.getText().equals("") && (server.isSelected() || !ip.getText().equals(""))) {

                if (Integer.parseInt(porta.getText()) > 65536 || Integer.parseInt(porta.getText()) < 1024) {    //verificação de input para porta
                    System.out.println("porta mal");
                    Exception ex = new Exception();
                    throw ex;
                }
                if (!server.isSelected()) {                                                     //verificação de input para IP (apenas se for cliente)
                    String[] campos = ip.getText().split("\\.");
                    if (campos.length != 4) {
                        System.out.println("campos ta mal" + String.valueOf(campos.length));
                        Exception ex = new Exception();
                        throw ex;
                    }
                    for (int i = 0; i < 4; i++) {
                        if (Integer.parseInt(campos[i]) < 0 || Integer.parseInt(campos[i]) > 255) {
                            System.out.println("ip ta mal");
                            Exception ex = new Exception();
                            throw ex;
                        }
                    }

                }
                FXMLLoader nextscreen;
                if (server.isSelected()) {
                    try {
                        ServerSocket sc = new ServerSocket(Integer.parseInt(porta.getText()));
                        Stage stage = (Stage) next.getScene().getWindow();
                        nextscreen = new FXMLLoader(getClass().getResource("Client_list.fxml"));    //mudar para a janela de servidor
                        Scene declients = new Scene(nextscreen.load());
                        stage.setScene(declients);
                        ClientListController puerta = nextscreen.getController();
                        puerta.porta = Integer.parseInt(porta.getText());
                        puerta.startup(sc);
                        } catch (Exception ex) {
                            warning.setText("Erro ao criar server socket");
                    }
                } else {
                    Socket s = null;
                    try {
                        System.out.println("omw to connect to " + ip.getText() + ":" + porta.getText());
                        s = new Socket();
                        s.connect(new InetSocketAddress(ip.getText(), Integer.parseInt(porta.getText())), 1000);    //tentar estabelecer ligacao e
                        System.out.println("connected");

                    } catch (IOException ex) {
                        warning.setText("conection failed");
                        System.out.println("womp womp " + ex.toString());
                        return;
                    }

                    Stage stage = (Stage) next.getScene().getWindow();
                    nextscreen = new FXMLLoader(getClass().getResource("Waiting_room.fxml"));
                    Scene declients = new Scene(nextscreen.load());
                    stage.setScene(declients);
                    Waiting_roomController puerta = nextscreen.getController();
                    puerta.s = s;
                    puerta.startup();
                }

            } else {
                warning.setText("Escreve as cenas");        //avisos para erros de input
            }
        } catch (Exception ex) {
            warning.setText("Escreve em condições");
            ex.printStackTrace();
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    /**
     * altera a cena para a página de ajuda
     * 
     * @param e 
     * click event
     */
    @FXML
    public void helppage(ActionEvent e) throws IOException{
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("helppage.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) client.getScene().getWindow();
        stage.close();
    }
}
