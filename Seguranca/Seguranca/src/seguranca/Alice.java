package seguranca;

import java.io.Serializable;
import java.util.Arrays;

public class Alice implements Serializable{

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
