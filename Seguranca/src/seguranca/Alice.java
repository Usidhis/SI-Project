package seguranca;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The type Alice.
 */
public class Alice implements Serializable{

    /**
     * Get salt byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Sets salt.
     *
     * @param salt the salt
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Gets escolha.
     *
     * @return the escolha
     */
    public int getEscolha() {
        return escolha;
    }

    /**
     * Sets escolha.
     *
     * @param escolha the escolha
     */
    public void setEscolha(int escolha) {
        this.escolha = escolha;
    }

    /**
     * The Salt.
     */
    byte[] salt;
    /**
     * The Escolha.
     */
    int escolha;

    /**
     * Instantiates a new Alice.
     *
     * @param salt    the salt
     * @param escolha the escolha
     */
    public Alice(byte[] salt, int escolha){
        this.salt = salt;
        this.escolha = escolha;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Alice{" +
                "salt=" + Arrays.toString(salt) +
                ", escolha=" + escolha +
                '}';
    }
}
