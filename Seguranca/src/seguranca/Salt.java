package seguranca;

import java.security.SecureRandom;

/**
 * The type Salt.
 */
public class Salt {
    /**
     * Salt gen byte [ ].
     * Função que gera o salt com valores aleatórios posição a posiç
     * @return the byte [ ]
     */
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
