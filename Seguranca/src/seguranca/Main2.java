package seguranca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * The type Main 2.
 */
public class Main2 {

    /**
     * Sender alice.
     * Função que recebe o número de iterações, tamanho da chave, a função criptográfica de hash e a palavra passe dada pelo user, chama a função de salt, a que gera a middle key
     * e a que gera a final key para criar a chave, no final mostra a chave ao user e retorna um obejto do tipo alice que contem o salt e um int que representa a chave criptográfica de hash a 
     * enviar para o outro user
     * @param passe   the passe
     * @param escolha the escolha
     * @return the alice
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static Alice sender(String passe, int escolha) throws NoSuchAlgorithmException {

        //valores para a criação da chave intermediária
        int numDeCodigos = 1024;
        int tamanhoChave = 256;


        //Escolha da Cifra

        while (true) {
 
            if (escolha == 5 || escolha == 6) {
                break; // Break the loop if the input is valid (1 or 2)
            } else {
                System.out.println("Escolha inválida. Por favor, insira 1 ou 2.");
            }
        }

        //Separação da palavra
        char[] passeSeparada = passe.toCharArray();

        //salt
        byte[] salt = Salt.saltGen();

        //Chave Intermediária
            // Criação do MessageDigest em SHA-256, chamar o algoritmo do sha256 para o trabalho

            switch(escolha){
                case 5:
                    MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

                    //passar a palavra-passe para bytes
                    byte[] passeSeparadaBytes = new byte[passeSeparada.length]; // CRiação do array que vai guardar a passeSEparada em Bytes
                    for(int i = 0; i < passeSeparadaBytes.length; i++){         // ciclo que corre a passeSEparada um a um e um a um
                        passeSeparadaBytes[i] = (byte) passeSeparada[i];        // vai mete-los na passeSeparadaBytes na mesma ordem mas a versão byte
                    }                                                           //faz um cast pata o tipo Byte
                    System.out.println(Arrays.toString(passeSeparadaBytes));

                    byte[] chaveIntermediaria = PBKDF2.pbkdf2SHA1(passeSeparada, salt, numDeCodigos, tamanhoChave);

                    // Imprimir a chave intermediária (para fins de demonstração)
                    if (chaveIntermediaria != null) {
                        System.out.println("Chave Intermediária: " + Arrays.toString(chaveIntermediaria));
                    }

                    //Converter a chave intermediária de Byte[] para Char[]
                    assert chaveIntermediaria != null;
                    char[] chaveIntermediariaChars = new String(chaveIntermediaria).toCharArray();


                    // CHAVE FINAL, FINALMENTE
                    // Each element in the array represents a signed byte value ranging from -128 to 127.
                    byte[] chaveFinal = PBKDF2.finalKeySHA1(chaveIntermediariaChars, salt);
                    if(chaveFinal != null){
                        System.out.println("A chave Final é: " + Arrays.toString(chaveFinal));
                        Alice A = new Alice(salt, escolha);
                        return A;
                    };
                    break;


                case 6:
                    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

                    //passar a palavra-passe para bytes
                    byte[] passeSeparadaBytes3 = new byte[passeSeparada.length]; // CRiação do array que vai guardar a passeSEparada em Bytes
                    for(int i = 0; i < passeSeparadaBytes3.length; i++){ // ciclo que corre a passeSEparada um a um e um a um
                        passeSeparadaBytes3[i] = (byte) passeSeparada[i];  // vai mete-los na passeSeparadaBytes na mesma ordem mas a versão byte
                    }                                                     //faz um cast pata o tipo Byte
                    System.out.println(Arrays.toString(passeSeparadaBytes3));

                    byte[] chaveIntermediaria3 = PBKDF2.pbkdf2SHA256(passeSeparada, salt, numDeCodigos, tamanhoChave);

                    // Imprimir a chave intermediária (para fins de demonstração)
                    if (chaveIntermediaria3 != null) {
                        System.out.println("Chave Intermediária: " + Arrays.toString(chaveIntermediaria3));
                    }

                    //Converter a chave intermediária de Byte[] para Char[]
                    char[] chaveIntermediariaChars3 = new String(chaveIntermediaria3).toCharArray();


                    // CHAVE FINAL, FINALMENTE
                    // Each element in the array represents a signed byte value ranging from -128 to 127.
                    byte[] chaveFinal3 = PBKDF2.finalKeySHA256(chaveIntermediariaChars3, salt);
                    if(chaveFinal3 != null){
                        System.out.println("A chave Final é: " + Arrays.toString(chaveFinal3));
                        Alice A = new Alice(salt,escolha);
                        return A;
                    }
                    break;
            }
            Alice A = new Alice(salt, escolha);
            return A;
    }
}
