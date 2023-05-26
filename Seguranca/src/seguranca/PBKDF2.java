package seguranca;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

/**
 * The type Pbkdf 2.
 */
public class PBKDF2 {
    /**
     * Pbkdf 2 sha 1 byte [ ].
     * Função que gera a middle key para a SHA1
     * @param password   the password
     * @param salt       the salt
     * @param iterations the iterations
     * @param keyLength  the key length
     * @return the byte [ ]
     */
    public static byte[] pbkdf2SHA1(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = factory.generateSecret(spec);
            return key.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Final key sha 1 byte [ ].
     * Função que gera a Final Key para a SHA1 
     * @param intermediateKey the intermediate key
     * @param salt            the salt
     * @return the byte [ ]
     */
    public static byte[] finalKeySHA1 (char[] intermediateKey, byte[] salt) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(intermediateKey, salt,  1024, 256);
             return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Pbkdf 2 sha 256 byte [ ].
     * Função que gera a middle key para a SHA256
     * @param password   the password
     * @param salt       the salt
     * @param iterations the iterations
     * @param keyLength  the key length
     * @return the byte [ ]
     */
    public static byte[] pbkdf2SHA256(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = factory.generateSecret(spec);
            return key.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Final key sha 256 byte [ ].
     * Função que gera a Final key para a SHA256
     * @param intermediateKey the intermediate key
     * @param salt            the salt
     * @return the byte [ ]
     */
    public static byte[] finalKeySHA256 (char[] intermediateKey, byte[] salt) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(intermediateKey, salt,  1024, 256);
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
