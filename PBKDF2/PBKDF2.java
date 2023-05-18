import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

public class PBKDF2 {
    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) {
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


    public static byte[] finalKey (char[] intermediateKey, byte[] salt) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(intermediateKey, salt,  100000, 256);
             return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
