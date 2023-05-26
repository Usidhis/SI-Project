package seguranca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Reciever {
    public static String reciever(Alice salt_recebido, String passe) throws NoSuchAlgorithmException {

        //valores para a criação da chave intermediária
        int numDeCodigos = 1024;
        int tamanhoChave = 256;


        //salt
        byte[] salt = new byte[0];
        salt = salt_recebido.getSalt();

        System.out.println("O salt gerado pelo emissor da mensagem é: " +  Arrays.toString(salt_recebido.getSalt()));

        //Palavra passe fornecida pelo utilizador
        //Input da passe

        //Escolha da Cifra

        int escolha = salt_recebido.getEscolha();
        while (true) {
            if (escolha == 5 || escolha == 6) {
                break; // Break the loop if the input is valid (1 or 2)
            } else {
                System.out.println("Escolha inválida. Por favor, insira 1 ou 2.");
            }
        }

        //Separação da palavra
        char[] passeSeparada = passe.toCharArray();

        switch(escolha){
            case 5:
                MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

                //passar a palavra-passe para bytes
                byte[] passeSeparadaBytes = new byte[passeSeparada.length]; // CRiação do array que vai guardar a passe SEparada em Bytes
                for(int i = 0; i < passeSeparadaBytes.length; i++){ // ciclo que corre a passeSEparada um a um e um a um
                    passeSeparadaBytes[i] = (byte) passeSeparada[i];  // vai mete-los na passeSeparadaBytes na mesma ordem mas a versão byte
                }                                                     //faz um cast pata o tipo Byte
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
                    return Arrays.toString(chaveFinal);
                }
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
                    return Arrays.toString(chaveFinal3);
                }
                break;

        }
        return null;
    }

}