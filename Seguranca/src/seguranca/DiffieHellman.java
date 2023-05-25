package seguranca;

import java.math.BigInteger;
import java.security.*;
import java.util.Base64;


/**
 * Diffie hellman.
 */
public class DiffieHellman {


    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = BigInteger.TWO;

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger sharedKey;
    public BigInteger g;
    public BigInteger p;

    /**
     * Número de bits usados.
     */
    int numberOfBits = 1024;

    /**
     * Instancia um novo Diffie hellman com o intuíto de começar a comunicação,
     * Gera aleatoriamente o gerador e um número primo,
     * Escolhe aleatoriamente a chave privada,
     * Calcula a chave publica.
     */
    public DiffieHellman() {
        g = PrimeGenerator.get_newPrime(numberOfBits);
        p = PrimeGenerator.get_newPrime(numberOfBits);
        SecureRandom random = new SecureRandom();
        privateKey = new BigInteger(p.bitLength() - 1, random);
        publicKey = g.modPow(privateKey, p);
    }

    /**
     * Instancia um novo Diffie hellman.
     * Obtendo a chave privada, criada aleatoriamente;
     * Utiliza o g e o p para:
     * Calcular a chave publica.
     */
    public DiffieHellman(BigInteger g, BigInteger p) {
        this.g = g;
        this.p = p;
        SecureRandom random = new SecureRandom();
        privateKey = new BigInteger(p.bitLength() - 1, random);
        publicKey = g.modPow(privateKey, p);
    }


    /**
     * Calcula a chave partilhada com verificação.
     *
     * @param sharedPublicKey - chave publica enviada,
     * @param p ,
     * Calcula a chave partilhada,
     * Obtem a hash dessa chave,
     * Assina a hash com rsa,
     * @return duma String com a assinatura da hash da chave partilhada juntamente com uma chave publica rsa.
     */
    public String calculateSharedKeyWithVerification(BigInteger sharedPublicKey, BigInteger p) {
        sharedKey = sharedPublicKey.modPow(privateKey, p);
        MessageDigest sha256Digest = null;
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash =  sha256Digest.digest(sharedKey.toByteArray());
        RSA rsa = new RSA();
        String sign_hash  = rsa.signHash(hash);
        return  sign_hash + ";" + rsa.PemPublicKey();
    }

    /**
     * Calcula a chave partilhada.
     *
     * @param otherPublicKey - chave publica enviada,
     * @param p
     */
    public void calculateSharedKey(BigInteger otherPublicKey, BigInteger p) {
        sharedKey = otherPublicKey.modPow(privateKey, p);
    }

    /**
     * Verifica a hash da chave partilhada.
     *
     * @param recive_signature - Assinatura da hash da chave partilhada com chave publica rsa recebida,
     * Separa a assinatura e a chave publica,
     * Calcula a hash da chave partilhada,
     * Faz varificação por rsa,
     * @return de um booleano.
     */
    public boolean verifyHashSharedKey(String recive_signature){
        String[] recive_signature_array = recive_signature.split(";");
        String sign_hash = recive_signature_array[0];
        String pubKey = recive_signature_array[1];
        RSA rsa = new RSA();

        byte[] sign_hash_decode = Base64.getDecoder().decode(sign_hash.getBytes());
        String[] frompem = rsa.reversePemPublicKey(pubKey).split("\n");
        BigInteger N = new  BigInteger(frompem[0]);
        BigInteger E = new  BigInteger(frompem[1]);

        MessageDigest sha256Digest = null;
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash =  sha256Digest.digest(sharedKey.toByteArray());

        BigInteger hashed_Data = new BigInteger(1, hash);

        BigInteger sing_Hash_Int = new BigInteger(1,sign_hash_decode);

        return rsa.verifyHash(sing_Hash_Int,hashed_Data,E, N);

    }

    /**
     * Obtem a chave publica.
     *
     * @return da chave publica.
     */
    public BigInteger getPublicKey() {
        return publicKey;
    }

    /**
     * Obtem a chave partilhada.
     *
     * @return da chave partilhada.
     */
    public BigInteger getSharedKey() {
        return sharedKey;
    }
    public BigInteger getprivateKey(){
        return privateKey;
    }

}