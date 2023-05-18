import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    private static Object salt;

    public static void main(String[] args) throws NoSuchAlgorithmException {

        //valores para a criação da chave intermediária
        int numDeCodigos = 10000;
        int tamanhoChave = 256;


        //Palavra passe fornecida pelo utilizador
        //Input da passe
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza a palavra-passe: ");
        String passe = scanner.nextLine();

        //Separação da palavra
        char[] passeSeparada = passe.toCharArray();
        for (int i = 0; i < passeSeparada.length; i++) {
            System.out.println(passeSeparada[i]);
        }

        //salt
        byte[] salt = Salt.saltGen();

        //Chave Intermediária
            // Criação do MessageDigest em SHA-256, chamar o algoritmo do sha256 para o trabalho
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            //passar a palavra-passe para bytes
            byte[] passeSeparadaBytes = new byte[passeSeparada.length]; // CRiação do array que vai guardar a passeSEparada em Bytes
            for(int i = 0; i < passeSeparadaBytes.length; i++){ // ciclo que corre a passeSEparada um a um e um a um
                passeSeparadaBytes[i] = (byte) passeSeparada[i];  // vai mete-los na passeSeparadaBytes na mesma ordem mas a versão byte
            }                                                     //faz um cast pata o tipo Byte
            System.out.println(Arrays.toString(passeSeparadaBytes));

            byte[] chaveIntermediaria = PBKDF2.pbkdf2(passeSeparada, salt, numDeCodigos, tamanhoChave);

            // Imprimir a chave intermediária (para fins de demonstração)
            if (chaveIntermediaria != null) {
                System.out.println("Chave Intermediária: " + Arrays.toString(chaveIntermediaria));
        }

            //Converter a chave intermediária de Byte[] para Char[]
            char[] chaveIntermediariaChars = new String(chaveIntermediaria).toCharArray();


        // CHAVE FINAL, FINALMENTE
        // Each element in the array represents a signed byte value ranging from -128 to 127.
            byte[] chaveFinal = PBKDF2.finalKey(chaveIntermediariaChars, salt);
                if(chaveFinal != null){
                    System.out.println("A chave Final é: " + Arrays.toString(chaveFinal));
                }
    }
}