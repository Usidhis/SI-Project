package seguranca;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Secret 2.
 */
public class Secret2 implements Serializable{
    /**
     * Instantiates a new Secret 2.
     *
     * @param text_limpo the text limpo
     * @param hash       the hash
     */
    public Secret2(String text_limpo, int hash) {
        this.text_limpo = text_limpo;
        this.hash = hash;
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
        Secret2 secret2 = (Secret2) o;
        return hash == secret2.hash && Objects.equals(text_limpo, secret2.text_limpo);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(text_limpo, hash);
    }

    /**
     * Gets text limpo.
     *
     * @return the text limpo
     */
    public String getText_limpo() {
        return text_limpo;
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
     * Sets text limpo.
     *
     * @param text_limpo the text limpo
     */
    public void setText_limpo(String text_limpo) {
        this.text_limpo = text_limpo;
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
     * The Text limpo.
     */
    String text_limpo;
    /**
     * The Hash.
     */
    int hash;

}
