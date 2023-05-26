package seguranca;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The type Alice.
 */
public class Alice implements Serializable{

    /**
     * Get salt byte [ ].
     * Classe que cria objetos do tipo Alice, contém o valor do salt num array de bytes e um int que indica qual a função criptográfica de hash escolhida pelo sender
     * @return the byte [ ]
     */
    public byte[] getSalt() {
        return salt;
    }
    
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    
    public int getEscolha() {
        return escolha;
    }

    public void setEscolha(int escolha) {
        this.escolha = escolha;
    }
    
    byte[] salt;
    int escolha;

    public Alice(byte[] salt, int escolha){
        this.salt = salt;
        this.escolha = escolha;
    }

    @Override
    public String toString() {
        return "Alice{" +
                "salt=" + Arrays.toString(salt) +
                ", escolha=" + escolha +
                '}';
    }
}
