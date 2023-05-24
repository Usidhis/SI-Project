package seguranca;

import java.io.*;
import java.net.Socket;

import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author jpc
 */
public class finalkeyscreencontroller implements Initializable {

    @FXML
    Label labeline;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void startup(Socket soca, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            int llave = (int) ois.readObject();
            labeline.setText("waiting for da bich");
            //do the stuff
            switch (llave) {
                case 1:
                case 2:
                    ArrayList<Puzzel3> puz = (ArrayList<Puzzel3>) ois.readObject();
                    System.out.println(puz);
                    Bob b = MPSwitch.solv_puzzel(puz);
                    System.out.println(b);
                    labeline.setText(b.getTexto_limpo());
                    oos.writeObject(b.getEscolha());
                    break;
                default:
            }

            oos.writeObject(llave);
            MessageDigest dgst = MessageDigest.getInstance("SHA-256");
            oos.writeObject(dgst.digest(String.valueOf(llave).getBytes()));
            soca.close();
            ois.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void startup(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int opc) {
        switch (opc) {                              //troca de mensagens do ponto do servidor
                //enfiar as cenas
                
//                    PythonInterpreter pi = new PythonInterpreter();
//                    pi.execfile("python.py");
//                    PyObject llaveobject = pi.get("llave");
//                    llave = llaveobject.asString();
//                    os.writeObject(llave);
//                    String answer = (String) is.readObject();
//                    System.out.println("done");
                
            case 1:
            case 2:
                ArrayList<Puzzel3> puz = MPSwitch.generate_puzzels(opc);
                try {
                    oos.writeObject(opc);
                    oos.writeObject(puz);
                    int esc = (int) ois.readObject();
                    Puzzel3 puzfinal = puz.get(esc);
                    ArrayList<Secret2> xius = getdafilete();
                    
                    labeline.setText(xius.get(esc).getText_limpo());
                    return;
                }catch (Exception e) {
                }
                break;
            case 3:

                break;
            case 4:
                
                break;

            default:
                throw new AssertionError();
        }
        try {
            int answer = (int) ois.readObject();
            MessageDigest dgst = MessageDigest.getInstance("SHA-256");
            if (ois.readObject().equals(dgst.digest(String.valueOf(answer).getBytes()))) {
                labeline.setText("fini: " + answer);
            }
            else{
                labeline.setText("the keys dont match");
            }
            
            soca.close();
            ois.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    @FXML
    public void gobacky(ActionEvent e) throws IOException{
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage menu = new Stage();
        menu.setScene(new Scene(themenu.load()));
        menu.show();
        Stage stage = (Stage) labeline.getScene().getWindow();
        menu.close();
    }
    
    public ArrayList<Secret2> getdafilete() {
		ObjectInputStream stream;
		ArrayList<Secret2> xius = new ArrayList<Secret2>();
		try {
			stream = new ObjectInputStream(
					new FileInputStream("C:/Users/User/Desktop/xiu.txt"));
			xius = (ArrayList<Secret2>) stream.readObject();
			stream.close();
		} catch (Exception e) {

		}
		return xius;

    }
}
