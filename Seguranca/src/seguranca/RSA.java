package seguranca;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.*;


/**
 * RSA
 */
public class RSA implements Serializable {
    private BigInteger P1;
    private BigInteger P2;
    private BigInteger N;
    private BigInteger PHI;
    private BigInteger E;
    private BigInteger D;


    /**
     * Construtor para o RSA.
     * Gera dois primos
     * Cria as chaves privadas e públicas
     */
    public RSA() {
        // Generate two large prime numbers
        P1 = PrimeGenerator.get_newPrime(1024);
        P2 = PrimeGenerator.get_newPrime(1024);
        N = P1.multiply(P2);
        PHI = P1.subtract(BigInteger.ONE).multiply(P2.subtract(BigInteger.ONE));
        E = BigInteger.valueOf(65537); // Common value for E
        D = E.modInverse(PHI);

    }

    /**
     * Cria um certificado para o RSA
     *
     * @param issuer  Quem imite o certificado
     * @param subject Para quem é o certificado
     * @return String certificicado assinado
     */
    public String create_certificate(String issuer, String subject){
        Date validityStartDate = new Date();
        Date validityEndDate = new Date(validityStartDate.getTime() + 365 * 24 * 60 * 60 * 1000L);
        StringBuilder certBuilder = new StringBuilder();
        certBuilder.append("Issuer: ").append(issuer).append("\n");
        certBuilder.append("Subject: ").append(subject).append("\n");
        certBuilder.append("Validity Start Date: ").append(validityStartDate).append("\n");
        certBuilder.append("Validity End Date: ").append(validityEndDate).append("\n");
        certBuilder.append("Public Key: ").append(this.PemPublicKey()).append("\n");


        String certificateData = certBuilder.toString();

        MessageDigest sha256Digest = null;
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashedData = sha256Digest.digest(certificateData.getBytes());

        String signature = signHash(hashedData);


        certBuilder.append("Signature: ").append(signature).append("\n");

        return certBuilder.toString();
    }

    /**
     * Usado para assinar um hash de um certificado ou outro hash
     *
     * @param hash hash para ser assinado
     * @return hash assinado
     */
    public String signHash(byte[] hash)  {
        // Convert the hash to a BigInteger
        BigInteger hashAsBigInt = new BigInteger(1, hash);


        // Sign the hash using RSA
        BigInteger signature = hashAsBigInt.modPow(D, N);
        String base64encoded = Base64.getEncoder().encodeToString(signature.toByteArray());


        return base64encoded;
    }


    /**
     * Verifica se o certificado é valido
     *
     * @param certificate  Certificado a ser validado
     * @param publicKeyPem Chave pública do certificado
     * @return boolean true se verifica, false se não verifica
     */
    public boolean verifyCertificate(String certificate, String publicKeyPem) {
        System.out.println(certificate);

        String[] frompem = reversePemPublicKey(publicKeyPem).split("\n");
        BigInteger N = new  BigInteger(frompem[0]);
        BigInteger E = new  BigInteger(frompem[1]);

        ArrayList<String> certificate_split = new ArrayList<>(List.of(certificate.split("\n")));

        String hash = certificate_split.remove(certificate_split.size()-1).replace("Signature: ", "");

        String new_certicificate = "";
        for(int i = 0; i<certificate_split.size();i++){
            new_certicificate += certificate_split.get(i) + "\n";
        }

        MessageDigest sha256Digest = null;
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger hashedData = new BigInteger(1, sha256Digest.digest(new_certicificate.getBytes()));


        BigInteger hash_original_with_rsa = new BigInteger(1, Base64.getDecoder().decode(hash));

        return verifyHash(hash_original_with_rsa,hashedData,E,N);
    }

    /**
     * Verifica se um hash, outro hash asssinado com RSA e chave pública são compativeis
     *
     * @param hash_original_with_rsa Hash assinado com RSA
     * @param hashedData             Hash calculado
     * @param PubE                   Expoente RSA
     * @param PubN                   Modulo RSA
     * @return boolean true se verifica, false se não verifica
     */
    public boolean verifyHash(BigInteger hash_original_with_rsa, BigInteger hashedData, BigInteger PubE, BigInteger PubN)  {

        // Sign the hash using RSA
        BigInteger signature = hash_original_with_rsa.modPow(PubE, PubN);


        return signature.equals(hashedData);
    }

    /**
     * Converte um chave pública em formato PEM em um modulo e expoente público de RSA
     *
     * @param pemPublicKey Chave pública em formato PEM
     * @return String com módulo e expoente público
     */
    public String reversePemPublicKey(String pemPublicKey)  {
        String BEGIN_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----\n";
        String END_PUBLIC_KEY = "\n-----END RSA PUBLIC KEY-----";

        // Remove BEGIN_PUBLIC_KEY and END_PUBLIC_KEY
        String encodedKey = pemPublicKey
                .replace(BEGIN_PUBLIC_KEY, "")
                .replace(END_PUBLIC_KEY, "")
                .replaceAll("\\s", ""); // Remove any whitespace characters

        // Decode the Base64-encoded key
        byte[] encodedBytes = Base64.getDecoder().decode(encodedKey);

        // Generate public key from encoded bytes
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
        PublicKey publicKey = null;
        try {
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        // Get the modulus and exponent values
        RSAPublicKeySpec rsaKeySpec = null;
        try {
            rsaKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        String modulus = rsaKeySpec.getModulus().toString();
        String exponent = rsaKeySpec.getPublicExponent().toString();

        // Create the original RSA public key string
        return modulus + "\n" + exponent;
    }

    /**
     * Cria uma chave pública em formato PEM
     *
     * @return Chave pública em formato PEM
     */
    public String PemPublicKey()  {
        String BEGIN_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----\n";
        String END_PUBLIC_KEY = "\n-----END RSA PUBLIC KEY-----";

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(N,E);
        KeyFactory keyFactory = null;
        PublicKey key = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            key =  keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            System.out.println(e);
        }

        byte[] keyBytes = key.getEncoded();
        byte[] encodedBytes = Base64.getEncoder().encode(keyBytes);

        String encodedKey = new String(encodedBytes);

        return BEGIN_PUBLIC_KEY + encodedKey + END_PUBLIC_KEY;
    }

    /**
     * Chave privada em formato PEM
     *
     * @return Chave privada em formato PEM
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public String PemPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String BEGIN_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n";
        String END_PRIVATE_KEY = "\n-----END RSA PRIVATE KEY-----";

        BigInteger exp1 = D.remainder(P1.subtract(BigInteger.ONE));
        BigInteger exp2 = D.remainder(P2.subtract(BigInteger.ONE));
        BigInteger coef = P2.modInverse(P1);

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                N, E, D, P1, P2, exp1, exp2, coef
        );
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);

        byte[] keyBytes = key.getEncoded();
        byte[] encodedBytes = Base64.getEncoder().encode(keyBytes);

        String encodedKey = new String(encodedBytes);

        return BEGIN_PRIVATE_KEY + encodedKey + END_PRIVATE_KEY;
    }
}