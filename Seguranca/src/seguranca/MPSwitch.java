package seguranca;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * The type Mp switch.
 * Classe que contem todas as funções para gerar e resolver os Puzzles de Merkel.
 */
public class MPSwitch {

    /**
     * Key string.
     * Função de geração de chaves com padding de dez 1's e 6 digitos randomizados.
     * @return String da chave gerada.
     */
    public static String key()  {  // Função de geração de chaves com padding de dez 1's e 6 digitos randomizados
        String key = "1111111111";
        for (int j = 0; j < 6; j++) {
            Random random = new Random();
            int digito = random.nextInt(10);
            key += digito;
        }

        System.out.println(key);

        return key;
    }

    /**
     * Encrypt aes string. Função que encripta uma string.
     *
     * @param mensagem Segredo a ser cifrado.
     * @param key      Chave para cifrar o segredo.
     * @param escolha  Inteiro que indica a função de cifra a ser usada.
     * @return Criptograma gerado.
     * @throws Exception the exception
     */
    public static String encryptAES (String mensagem, byte[] key, int escolha) throws Exception {
        byte[] segredo_cifrado = new byte[0];
        byte[] chave = key;

        switch (escolha){
            case 1:
                SecretKeySpec chaveSec1 = new SecretKeySpec(chave, "AES");
                Cipher cipher1 = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher1.init(ENCRYPT_MODE, chaveSec1);
                byte[] cipherText1 = cipher1.doFinal(mensagem.getBytes());
                String criptograma1 = encode(cipherText1);
                return criptograma1;


            case 2:
                SecretKeySpec chaveSec2 = new SecretKeySpec(chave, "RC4");
                Cipher cipher = Cipher.getInstance("RC4");
                cipher.init(Cipher.ENCRYPT_MODE, chaveSec2);
                byte[] cipherText2 = cipher.doFinal(mensagem.getBytes());
                String criptograma2 = encode(cipherText2);
                return criptograma2;


           /* case 3:
                SecretKeySpec chaveSec3 = new SecretKeySpec(chave, "ChaCha20");
                byte[] nonce = new byte[1];
                IvParameterSpec ivParameterSpec = new IvParameterSpec(nonce);
                Cipher cipher3 = Cipher.getInstance("ChaCha20");
                cipher3.init(ENCRYPT_MODE, chaveSec3, ivParameterSpec);
                byte[] cipherText3 = cipher3.doFinal(mensagem.getBytes());
                String criptograma3 = encode(cipherText3);
                return criptograma3;*/
        }

        return null;
    }

    /**
     * Decrypt aes string. Função de desencriptação de um criptograma de acordo com o tipo de cifra
     *
     * @param criptograma Criptograma que se pertende desencriptar.
     * @param key         Chave de desencriptação
     * @param cifra       Tipo de cifra que vai ser usado
     * @return Criptograma decifrado.
     * @throws Exception the exception
     */
    public static String decryptAES (String criptograma, byte[] key, int cifra) throws Exception{ 
        switch (cifra){
            case 1:
                byte[] criptograma_in_bytes = decode(criptograma);
                byte[] chave = key;
                SecretKeySpec chaveSec = new SecretKeySpec(chave, "AES");
                Cipher cipher1 = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher1.init(DECRYPT_MODE, chaveSec);
                byte[] byte_decifrados = cipher1.doFinal(criptograma_in_bytes);
                String decifrado = new String(byte_decifrados);
                return  decifrado;


            case 2:
                byte[] criptograma_in_bytes2 = decode(criptograma);
                byte[] chave2 = key;
                SecretKeySpec chaveSec2 = new SecretKeySpec(chave2, "RC4");
                Cipher cipher2 = Cipher.getInstance("RC4");
                cipher2.init(DECRYPT_MODE, chaveSec2);
                byte[] byte_decifrados2 = cipher2.doFinal(criptograma_in_bytes2);
                String decifrado2 = new String(byte_decifrados2);
                return  decifrado2;

            /*case 3:
                byte[] criptograma_in_bytes3 = decode(criptograma);
                byte[] chave3 = key;
                SecretKeySpec chaveSec3 = new SecretKeySpec(chave3, "ChaCha20");
                Cipher cipher3 = Cipher.getInstance("ChaCha20");
                byte[] nonce = new byte[1];
                IvParameterSpec ivParameterSpec = new IvParameterSpec(nonce);
                cipher3.init(DECRYPT_MODE, chaveSec3, ivParameterSpec);
                byte[] byte_decifrados3 = cipher3.doFinal(criptograma_in_bytes3);
                String decifrado3 = new String(byte_decifrados3);
                return  decifrado3;*/
        }


        return criptograma;
    }

    /**
     * Encode string. Função que faz o encode de bytes em Base64.
     *
     * @param data Bytes que irão ser encoded.
     * @return String encoded.
     */
    public static String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Decode byte [ ]. Função que faz o decode de uma String em Base64
     *
     * @param data String que irá ser decoded.
     * @return Bytes decoded.
     */
    public static byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }


    /**
     * Generate puzzels array list.  Função que gera N Puzzles de Merkel.
     *
     * @param escolha Inteiro que indica a função de cifra a ser usada.
     * @return Uma lista com todos os Puzzels com os seus hash. 
     */
    public static ArrayList<Puzzel3> generate_puzzels(int escolha) { 
        ArrayList<Puzzel3> Puzzels = new ArrayList<Puzzel3>();
        ArrayList<Secret2> Secrets = new ArrayList<Secret2>();
        ArrayList<String> Keys = new ArrayList<String>();
        Random random = new Random();
        int number_of_puzzel = 5000;

        for (int i = 0; i < number_of_puzzel; i++) {
            String chave = MPSwitch.key();

            String segredo = "";
            for (int j = 0; j < 16; j++) {
                int digito = random.nextInt(10);
                segredo += digito;
            }

            Secret2 s = new Secret2(segredo, segredo.hashCode());
            Secrets.add(s);
            Keys.add(chave);

            try {
		ObjectOutputStream save = new ObjectOutputStream(
		new FileOutputStream("C:/Users/User/Desktop/xiu.txt"));
		save.writeObject(Secrets);
		save.close();
            } catch (Exception e) {
		System.out.print("nao guardou");
		e.printStackTrace();
            }
	
            
            
            
            try {
                String criptograma = MPSwitch.encryptAES(segredo, chave.getBytes(), escolha);
                Puzzel3 p = new Puzzel3(criptograma, segredo.hashCode(), escolha);
                Puzzels.add(p);

            } catch (Exception e) {
                System.out.println(e);
            }

        }

        return Puzzels;
    }


    /**
     * Solv puzzel bob. Função que soluciona um puzzle qualquer.
     *
     * @param Puzzel Puzzel escolhido para ser decifrado 
     * @return Objeto do tipo Bob que contem o segredo limpo juntamente com o puzzel escolhido.
     */
    public static Bob solv_puzzel(ArrayList<Puzzel3> Puzzel){ 
        Random random = new Random();
        int escolha = random.nextInt(Puzzel.size());
        String key_bf = "1111111111";
        String criptograma = Puzzel.get(escolha).getCriptograma();
        int cifra = Puzzel.get(escolha).getCifra();
        for (int i = 0; i < 10; i++){
            key_bf += i;

            for (int j = 0; j < 10; j++) {
                key_bf +=j;

                for (int k = 0; k < 10; k++) {
                    key_bf += k;

                    for (int l = 0; l < 10; l++) {
                        key_bf += l;

                        for (int m = 0; m < 10; m++) {
                            key_bf += m;

                            for (int n = 0; n < 10; n++) {
                                key_bf += n;

                                //System.out.println("key BF: " + key_bf);
                                try {
                                    String limpo = MPSwitch.decryptAES(criptograma, key_bf.getBytes(), cifra);
                                    if (limpo.hashCode() == Puzzel.get(escolha).getHash()) {
                                        System.out.println("Encontrei a chave");
                                        Bob bob = new Bob(limpo, escolha); //Objeto com o texto decifrado e o numero do puzzel escolhido
                                        return bob;
                                    }
                                } catch (Exception e) {

                                }
                                key_bf = "1111111111" + i +j + k + l + m;
                            }
                            key_bf = "1111111111" + i +j + k + l;
                        }
                        key_bf = "1111111111" + i +j + k;
                    }
                    key_bf = "1111111111" + i + j;
                }
                key_bf = "1111111111" + i;
            }
        }

        return null;
    }

    
    public static int menu(){ //Funão de escolha de metodo de cifra
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o método de encriptação: ");
        System.out.println("1 - AES");
        System.out.println("2 - RC4");
        //System.out.println("3 - ChaCha20");

        int metodo = scanner.nextInt();

        return metodo;
    }

}


