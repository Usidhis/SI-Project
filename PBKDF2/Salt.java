import java.security.SecureRandom;
public class Salt {
    public static byte[] saltGen(){
    int numBytes = 16;
    SecureRandom rndNum = new SecureRandom();
    byte[] salt =  new byte[numBytes];
    for (int i = 0; i < numBytes; i++){
        salt[i] = (byte) rndNum.nextInt(256);
    }
    System.out.println(new String(salt));
    return salt;
 }


}

// Linha 8, cena do 256
//Since we want to generate random bytes,
// which are values between 0 and 255 (inclusive),
// we need to pass 256 to nextInt() method so that it generates
// random integers between 0 (inclusive) and 256 (exclusive)