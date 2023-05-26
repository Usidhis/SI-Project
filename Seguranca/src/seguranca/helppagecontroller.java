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
        // TODO
        
    }

}
