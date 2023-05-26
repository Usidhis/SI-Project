package seguranca;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
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

    public void startupclient(Socket soca, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            int llave = (int) ois.readObject();
            //do the stuff
            switch (llave) {
                case 1:
                case 2:
                    ArrayList<Puzzel3> puz = (ArrayList<Puzzel3>) ois.readObject();
                    System.out.println(puz);
                    Bob b = MPSwitch.solv_puzzel(puz);
                    System.out.println(b);
                    labeline.setText("Key successfully exchanged\nyourkey:" + b.getTexto_limpo());
                    filesaver.savedafilete(b.getTexto_limpo(), "puzzelclient.txt");
                    oos.writeObject(b.getEscolha());
                    break;
                case 3:
                    BigInteger publickeyalice = (BigInteger) ois.readObject();
                    BigInteger g = (BigInteger) ois.readObject();
                    BigInteger p = (BigInteger) ois.readObject();
                    
                    DiffieHellman bob = new DiffieHellman(g, p);
                    
                    try {
                        oos.writeObject(bob.getPublicKey());
                        String assinatura = (String) ois.readObject();
                        bob.calculateSharedKey(publickeyalice, p);
                        if(bob.verifyHashSharedKey(assinatura)){
                            oos.writeObject(true);
                            labeline.setText("as assinaturas verificam");
                            filesaver.savedafilete(publickeyalice, "publickeyalice do bob.txt");
                            filesaver.savedafilete(bob.getPublicKey(), "publickeybob do bob.txt");
                            filesaver.savedafilete(bob.getprivateKey(), "privatekey do bob.txt");
                            filesaver.savedafilete(bob.getSharedKey(), "sharedkey do bob.txt");
                            
                                                       
                        }
                        else{
                            oos.writeObject(false);
                            labeline.setText("as assinaturas nao correspondem");
                        }
                        
                    } catch (Exception e) {
                        gobacky(new ActionEvent());
                        System.out.println("excecao de baixo");
                    }

                    break;
                case 4:
                    String pubkeyserver = (String) ois.readObject();
                    String certificateserver = (String) ois.readObject();
                    RSA cliente = new RSA();
                    if (cliente.verifyCertificate(certificateserver, pubkeyserver)) {
                        labeline.setText("certificado ta certo");
                        oos.writeObject(cliente.PemPublicKey());
                        oos.writeObject(cliente.create_certificate("clinte", soca.getInetAddress().toString()));

                        filesaver.savedafilete(pubkeyserver, "publickeyserver from client.txt");
                        filesaver.savedafilete(certificateserver, "certificateserver from client.txt");
                        filesaver.savedafilete(cliente.PemPrivateKey(), "private key from client");
                    } else {
                        labeline.setText("certificado nao corresponde");
                    }
                    oos.writeObject(cliente.PemPublicKey());
                    oos.writeObject(cliente.create_certificate("clinte", soca.getInetAddress().toString()));

                    filesaver.savedafilete(pubkeyserver, "publickeyserver from client.txt");
                    filesaver.savedafilete(certificateserver, "certificateserver from client.txt");
                    filesaver.savedafilete(cliente.PemPrivateKey(), "private key from client");
                    break;
                case 5:
                case 6:
                    FXMLLoader themenu = new FXMLLoader(getClass().getResource("offiline.fxml"));
                    Stage stage = (Stage) labeline.getScene().getWindow();
                    stage.setScene(new Scene(themenu.load()));
                    offilinecontroller canais = themenu.getController();
                    canais.startup(soca, ois, oos, llave, false);

                    return;

                default:
            }
            soca.close();
            ois.close();
            oos.close();
        } catch (Exception ex) {
            try {   
                System.out.println("excecao de cima");} catch (Exception e) {
            }
        }

    }

    public void startupclientpbk(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int opc, String pass) {
        try {
            //do the stuff
            switch (opc) {
                case 5:
                case 6:
                    Alice a = (Alice) ois.readObject();
                    String key = Reciever.reciever(a, pass);
                    String hashables = key + pass;
                    int hashclient = hashables.hashCode();
                    oos.writeObject(hashclient);
                    int hashserver = (int) ois.readObject();
                    System.out.println("hash de " + key + pass + " = " + (key + pass).hashCode());
                    if (hashserver == hashclient) {
                        filesaver.savedafilete(key, "pbkdf2client.txt");
                        labeline.setText("password saved to the file");
                    } else {
                        labeline.setText("you typed the wrong password");
                    }

                default:
            }

            soca.close();
            ois.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void startupserver(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int opc) {
        try {
            oos.writeObject(opc);
        } catch (Exception e) {
        }
        String pass = "";

        switch (opc) {                              //troca de mensagens do ponto do servidor

            case 1:
            case 2:
                ArrayList<Puzzel3> puz = MPSwitch.generate_puzzels(opc);
                try {

                    oos.writeObject(puz);
                    int esc = (int) ois.readObject();
                    Puzzel3 puzfinal = puz.get(esc);
                    ArrayList<Secret2> xius = getdafilete();

                    labeline.setText("Key successfully exchanged\nyourkey:" + xius.get(esc).getText_limpo());
                    filesaver.savedafilete(xius.get(esc).getText_limpo(), "puzzelserver.txt");
                    return;
                } catch (Exception e) {
                }
                break;
            case 3:
                DiffieHellman alice = new DiffieHellman();
                try {
                    oos.writeObject(alice.getPublicKey());
                    oos.writeObject(alice.g);
                    oos.writeObject(alice.p);
                    BigInteger publickeyclient = (BigInteger) ois.readObject();
                    String sharedkeyserver = alice.calculateSharedKeyWithVerification(publickeyclient, alice.p);
                    oos.writeObject(sharedkeyserver);
                    
                    if((boolean) ois.readObject()){
                            labeline.setText("as assinaturas verificam");
                            filesaver.savedafilete(publickeyclient, "publickeybob do alice.txt");
                            filesaver.savedafilete(alice.getPublicKey(), "publickeyalice do alice.txt");
                            filesaver.savedafilete(alice.getprivateKey(), "privatekey do alice.txt");
                            filesaver.savedafilete(alice.getSharedKey(), "sharedkey do alice.txt");
                            
                                                       
                        }
                        else
                            labeline.setText("as assinaturas nao correspondem");
                } catch (Exception e) {
                }
                break;
            case 4:
                RSA a = new RSA();
                String pubkeyserver = a.PemPublicKey();
                String certificateservidor = a.create_certificate("Servidor", soca.getInetAddress().toString());
                try {
                    oos.writeObject(pubkeyserver);
                    oos.writeObject(certificateservidor);
                    String pubkeycliente = (String) ois.readObject();
                    String certificateclient = (String) ois.readObject();

                    if (a.verifyCertificate(certificateclient, pubkeycliente)) {
                        labeline.setText("certificado ta certo");
                        filesaver.savedafilete(pubkeycliente, "publickeyserver from server.txt");
                        filesaver.savedafilete(certificateclient, "certificateserver from server.txt");
                        filesaver.savedafilete(a.PemPrivateKey(), "private key from server");
                    } else {
                        labeline.setText("certificado nao corresponde");
                    }
                } catch (Exception e) {
                }

                break;

            default:
                throw new AssertionError();
        }
        try {
            
            soca.close();
            ois.close();
            oos.close();
        } catch (Exception ex) {
        }

    }

    public void startupserverpbk(Socket soca, ObjectInputStream ois, ObjectOutputStream oos, int opc, String pass) {
        try {
            Alice a = Main2.sender(pass, opc);
            oos.writeObject(opc);
            oos.writeObject(a);
            String key = Reciever.reciever(a, pass);
            labeline.setText("Waiting for client to input password");
            int hashclient = (int) ois.readObject();
            String hashables = key + pass;
            int hashserver = hashables.hashCode();
            if (hashserver == hashclient) {
                labeline.setText("password saved to file");
                filesaver.savedafilete(key, "pbkdf2server.txt");
            } else {
                labeline.setText("The client put the wrong password");
            }
            oos.writeObject(hashserver);

        } catch (Exception e) {
        }

    }

    @FXML
    public void gobacky(ActionEvent e) throws IOException {
        FXMLLoader themenu = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Stage stage = (Stage) labeline.getScene().getWindow();
        stage.setScene(new Scene(themenu.load()));

    }

    public ArrayList<Secret2> getdafilete() {
        ObjectInputStream stream;
        ArrayList<Secret2> xius = new ArrayList<Secret2>();
        try {
            stream = new ObjectInputStream(
                    new FileInputStream("C:/Users/User/Desktop/xiu.txt"));
            xius = (ArrayList<Secret2>) stream.readObject();
            stream.close();
            File file = new File("C:/Users/User/Desktop/xiu.txt");
            file.delete();
        } catch (Exception e) {

        }
        return xius;

    }
}
