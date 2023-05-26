package seguranca;

import java.net.*;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class ConnectionThread extends Thread {

    ServerSocket sc;
    Socket s;
    @FXML
    ClientListController clc;
    /**
     * Inicializar a thread com os argumentos necessários
     * @param sc
     * server socket criada ao início     * 
     * @param clc
     * controlador da view anterior para adicionar os clientes que se ligam
     */
    public ConnectionThread(ServerSocket sc, ClientListController clc) {
        super();
        this.sc = sc;
        this.clc = clc;
    }
    /**
     * aceita clientes a ligarem se á server socket
     */
    @FXML
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("waiting");
                s = sc.accept();
                System.out.println("accepted");             //aceitar clientes e escreve los na lista
                InetSocketAddress ip = (InetSocketAddress) s.getRemoteSocketAddress();
                Platform.runLater(() -> clc.addclient(ip.toString(), s));
            }

        } catch (Exception e) {
            System.out.println("a tomada fichou");
        }
    }
}
