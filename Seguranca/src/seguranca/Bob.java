package seguranca;

/**
 * The type Bob.
 */
public class Bob {
    /**
     * The Texto limpo.
     */
    String texto_limpo;
    /**
     * The Escolha.
     */
    int escolha;

    /**
     * Instantiates a new Bob.
     *
     * @param texto_limpo the texto limpo
     * @param escolha     the escolha
     */
    public Bob(String texto_limpo, int escolha) {
        this.texto_limpo = texto_limpo;
        this.escolha = escolha;
    }

    /**
     * Gets texto limpo.
     *
     * @return the texto limpo
     */
    public String getTexto_limpo() {
        return texto_limpo;
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
     * Sets texto limpo.
     *
     * @param texto_limpo the texto limpo
     */
    public void setTexto_limpo(String texto_limpo) {
        this.texto_limpo = texto_limpo;
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
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Bob{" +
                "texto_limpo='" + texto_limpo + '\'' +
                ", escolha=" + escolha +
                '}';
    }
}
