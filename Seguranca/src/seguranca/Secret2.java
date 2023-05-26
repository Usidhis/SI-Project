package seguranca;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Secret 2.
 * Classe que cria objetos do tipo Secret2, este contem o segredo em texto limpo e o seu hash.
 */
public class Secret2 implements Serializable{
   
    public Secret2(String text_limpo, int hash) {
        this.text_limpo = text_limpo;
        this.hash = hash;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Secret2 secret2 = (Secret2) o;
        return hash == secret2.hash && Objects.equals(text_limpo, secret2.text_limpo);
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(text_limpo, hash);
    }

    
    public String getText_limpo() {
        return text_limpo;
    }

    
    public int getHash() {
        return hash;
    }

    
    public void setText_limpo(String text_limpo) {
        this.text_limpo = text_limpo;
    }

    
    public void setHash(int hash) {
        this.hash = hash;
    }

    
    String text_limpo;
    
    int hash;

}
