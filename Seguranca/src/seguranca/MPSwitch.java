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

public class MPSwitch {

    public static String key()  {  // Função de geração de chaves com padding de dez 1's e 6 digitos randomizados
        //String key = "111111111111";
        String key = "1111111111";
        for (int j = 0; j < 6; j++) {
            Random random = new Random();
            int digito = random.nextInt(10);
            key += digito;
        }

        //String key = "1111111111111112";
        System.out.println(key);

        return key;
    }

    public static String encryptAES (String mensagem, byte[] key, int escolha) throws Exception { // Função de encriptação de um segredo de acordo com o tipo de cifra
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

    public static String decryptAES (String criptograma, byte[] key, int cifra) throws Exception{ // Função de desencriptação de um criptograma de acordo com o tipo de cifra
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
    public static String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    public static byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }


    public static ArrayList<Puzzel3> generate_puzzels(int escolha) { // Função que gera N puzzles
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


    public static Bob solv_puzzel(ArrayList<Puzzel3> Puzzel){ // Função que soluciona um puzzle
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


