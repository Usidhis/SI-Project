package seguranca;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Puzzel 3.
 */
public class Puzzel3 implements Serializable{
    /**
     * The Criptograma.
     */
    String criptograma;
    /**
     * The Hash.
     */
    int hash;

    /**
     * The Cifra.
     */
    int cifra;

    /**
     * Gets criptograma.
     *
     * @return the criptograma
     */
    public String getCriptograma() {
        return criptograma;
    }

    /**
     * Gets hash.
     *
     * @return the hash
     */
    public int getHash() {
        return hash;
    }

    /**
     * Gets cifra.
     *
     * @return the cifra
     */
    public int getCifra() {
        return cifra;
    }

    /**
     * Sets criptograma.
     *
     * @param criptograma the criptograma
     */
    public void setCriptograma(String criptograma) {
        this.criptograma = criptograma;
    }

    /**
     * Sets hash.
     *
     * @param hash the hash
     */
    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * Sets cifra.
     *
     * @param cifra the cifra
     */
    public void setCifra(int cifra) {
        this.cifra = cifra;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzel3 puzzel3 = (Puzzel3) o;
        return hash == puzzel3.hash && cifra == puzzel3.cifra && Objects.equals(criptograma, puzzel3.criptograma);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(criptograma, hash);
    }

    /**
     * Instantiates a new Puzzel 3.
     *
     * @param criptograma the criptograma
     * @param hash        the hash
     * @param cifra       the cifra
     */
    public Puzzel3(String criptograma, int hash, int cifra) {
        this.criptograma = criptograma;
        this.hash = hash;
        this.cifra = cifra;
    }
}

