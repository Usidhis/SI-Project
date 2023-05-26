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

public class helppagecontroller implements Initializable {

    @FXML
    Label labelita;
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
        Stage stage = (Stage) labelita.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelita.setText("Método de operação da aplicação:\n"
                + "1-O servidor escolhe uma porta e inicia a server socket\n"
                + "2-O cliente insere o ip do servidor e a porta escolhida\n"
                + "3-O servidor escolhe o cliente a que se quer ligar com base no ip\n"
                + "4-O servidor escolhe o método que quer usar para trocar as chaves"
                + "5-Se o método escolhido for PBKDF2 ambos os utilizadores devem escrever a palavra passe correspondente"
                + "6-Os ficheiros criados são guardados");
    }

}
