package seguranca;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class ClientThread extends Thread{
    Socket s;
    ObjectInputStream is;
    ObjectOutputStream os;
    Label text;
    Waiting_roomController wrc;
    public ClientThread(Socket s, Label text, Waiting_roomController wrc, ObjectInputStream is, ObjectOutputStream os){
        this.s = s;
        this.text = text;
        this.is = is;
        this.os = os;
        this.wrc = wrc;
    }
    @Override
    public void run(){
        try {

            String resposta = (String) is.readObject();
            System.out.println("got answer" + resposta);
            if (resposta.equals("accepted")) {                      //se a a resposta do servidor for accepted passar para a janela final, senao é recusado
                Platform.runLater(() -> wrc.change());
            }  
            else{
                Platform.runLater(() -> wrc.change("you didnt get picked :("));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
