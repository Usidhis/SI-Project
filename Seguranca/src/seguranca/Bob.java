package seguranca;

/**
 * The type Bob.
 * Classe que cria objetos do tipo Bob, este contem o segredo decifrado e o numero do puzzle resolvido.
 */
public class Bob {
 
    String texto_limpo;
  
    int escolha;

    
    public Bob(String texto_limpo, int escolha) {
        this.texto_limpo = texto_limpo;
        this.escolha = escolha;
    }

   
    public String getTexto_limpo() {
        return texto_limpo;
    }

    
    public int getEscolha() {
        return escolha;
    }

   
    public void setTexto_limpo(String texto_limpo) {
        this.texto_limpo = texto_limpo;
    }

   
    public void setEscolha(int escolha) {
        this.escolha = escolha;
    }

   
    @Override
    public String toString() {
        return "Bob{" +
                "texto_limpo='" + texto_limpo + '\'' +
                ", escolha=" + escolha +
                '}';
    }
}
