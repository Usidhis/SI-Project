package seguranca;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeGenerator {

    private static final int[] FIRST_PRIMES_LIST = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
            89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
            181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277,
            281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
    };

    private static BigInteger nBitRandom(int n) {
        SecureRandom rnd = new SecureRandom();
        BigInteger minVal = BigInteger.valueOf(2).pow(n - 1);
        BigInteger maxVal = BigInteger.valueOf(2).pow(n + 1);
        BigInteger randomNum = new BigInteger(maxVal.bitLength(), rnd);
        while (randomNum.compareTo(minVal) < 0 || randomNum.compareTo(maxVal) > 0) {
            randomNum = new BigInteger(maxVal.bitLength(), rnd);
        }
        return randomNum;
    }

    private static BigInteger getLowLevelPrime(int n) {
        while (true) {
            BigInteger pc = nBitRandom(n);

            for (int i = 0; i < FIRST_PRIMES_LIST.length; i++) {
                BigInteger divisor = BigInteger.valueOf(FIRST_PRIMES_LIST[i]);

                if (pc.mod(divisor).equals(BigInteger.ZERO) && divisor.pow(2).compareTo(pc) <= 0) {
                    break;
                } else {
                    return pc;
                }
            }
        }
    }

    private static boolean trialComposite(BigInteger roundTester, BigInteger ec, BigInteger mrc, int maxDivisionsByTwo) {
        if (roundTester.modPow(ec, mrc).equals(BigInteger.ONE)) {
            return false;
        }
        for (int i = 0; i < maxDivisionsByTwo; i++) {
            if (roundTester.modPow(BigInteger.valueOf(2).pow(i).multiply(ec), mrc).equals(mrc.subtract(BigInteger.ONE))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMillerRabinPassed(BigInteger mrc) {
        SecureRandom rnd = new SecureRandom();
        int maxDivisionsByTwo = 0;
        BigInteger ec = mrc.subtract(BigInteger.ONE);
        while (ec.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            ec = ec.divide(BigInteger.valueOf(2));
            maxDivisionsByTwo++;
        }


        BigInteger roundTester = new BigInteger(mrc.bitLength(), rnd);
        while (roundTester.compareTo(BigInteger.TWO) < 0 || roundTester.compareTo(mrc) >= 0) {
            roundTester = new BigInteger(mrc.bitLength(), rnd);
        }
        for (int i = 0; i < 20; i++) {
            if (trialComposite(roundTester, ec, mrc, maxDivisionsByTwo)) {
                return false;
            }
        }
        return true;
    }


    public static BigInteger get_newPrime(int bits ){
        BigInteger prime_canditate = BigInteger.ZERO;
        while (true){
            prime_canditate = getLowLevelPrime(bits);
            if (isMillerRabinPassed(prime_canditate) ){
                break;
            }

        }
        return prime_canditate;
    }

}