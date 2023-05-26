package seguranca;

import java.io.ObjectInputStream;
import javafx.application.Platform;


public class ClientThread extends Thread{
    ObjectInputStream is;
    Waiting_roomController wrc;
    /**
     * Inicializa a thread que agurda resposta do servidor
     * @param wrc
     * controlador da sala de espera para poder alterar a cena conforme necessário
     * @param is 
     * input stream que aguarda resposta do servidor
     */
    public ClientThread(Waiting_roomController wrc, ObjectInputStream is){
        this.is = is;
        this.wrc = wrc;
    }
    /**
     * thread que aguarda resposta do servidor
     */
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
