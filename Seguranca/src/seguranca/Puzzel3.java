package seguranca;

import java.io.Serializable;
import java.util.Objects;

public class Puzzel3 implements Serializable{
    String criptograma;
    int hash;

    int cifra;

    public String getCriptograma() {
        return criptograma;
    }

    public int getHash() {
        return hash;
    }

    public int getCifra() {
        return cifra;
    }

    public void setCriptograma(String criptograma) {
        this.criptograma = criptograma;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public void setCifra(int cifra) {
        this.cifra = cifra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzel3 puzzel3 = (Puzzel3) o;
        return hash == puzzel3.hash && cifra == puzzel3.cifra && Objects.equals(criptograma, puzzel3.criptograma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criptograma, hash);
    }

    public Puzzel3(String criptograma, int hash, int cifra) {
        this.criptograma = criptograma;
        this.hash = hash;
        this.cifra = cifra;
    }
}

