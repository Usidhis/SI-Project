package seguranca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The type Main.
 *
 * @author jpc
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("key dealer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args designed without arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
