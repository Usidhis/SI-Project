package seguranca;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

/**
 * The type Connection thread.
 */
public class ConnectionThread extends Thread {

    /**
     * The Sc.
     */
    ServerSocket sc;
    /**
     * The S.
     */
    Socket s;
    /**
     * The Porta.
     */
    int porta;
    /**
     * The Lista.
     */
    @FXML
    ListView lista;
    /**
     * The Clc.
     */
    ClientListController clc;

    /**
     * Instantiates a new Connection thread.
     *
     * @param sc    the sc
     * @param lista the lista
     * @param clc   the clc
     */
    public ConnectionThread(ServerSocket sc, ListView lista, ClientListController clc) {
        super();
        this.sc = sc;
        this.lista = lista;
        this.clc = clc;
    }

    /**
     * Run.
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
