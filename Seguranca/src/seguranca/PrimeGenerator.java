package seguranca;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Geradores de números primos que utiliza teste de primalidade de Miller-Rabin
 */
public class PrimeGenerator {

    /**
     * Lista de primos utilizados no teste
     */
    private static final BigInteger[] FIRST_PRIMES_LIST = {
            BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.valueOf(7),
            BigInteger.valueOf(11), BigInteger.valueOf(13), BigInteger.valueOf(17), BigInteger.valueOf(19),
            BigInteger.valueOf(23), BigInteger.valueOf(29), BigInteger.valueOf(31), BigInteger.valueOf(37),
            BigInteger.valueOf(41), BigInteger.valueOf(43), BigInteger.valueOf(47), BigInteger.valueOf(53),
            BigInteger.valueOf(59), BigInteger.valueOf(61), BigInteger.valueOf(67), BigInteger.valueOf(71),
            BigInteger.valueOf(73), BigInteger.valueOf(79), BigInteger.valueOf(83), BigInteger.valueOf(89),
            BigInteger.valueOf(97), BigInteger.valueOf(101), BigInteger.valueOf(103), BigInteger.valueOf(107),
            BigInteger.valueOf(109), BigInteger.valueOf(113), BigInteger.valueOf(127), BigInteger.valueOf(131),
            BigInteger.valueOf(137), BigInteger.valueOf(139), BigInteger.valueOf(149), BigInteger.valueOf(151),
            BigInteger.valueOf(157), BigInteger.valueOf(163), BigInteger.valueOf(167), BigInteger.valueOf(173),
            BigInteger.valueOf(179), BigInteger.valueOf(181), BigInteger.valueOf(191), BigInteger.valueOf(193),
            BigInteger.valueOf(197), BigInteger.valueOf(199), BigInteger.valueOf(211), BigInteger.valueOf(223),
            BigInteger.valueOf(227), BigInteger.valueOf(229), BigInteger.valueOf(233), BigInteger.valueOf(239),
            BigInteger.valueOf(241), BigInteger.valueOf(251), BigInteger.valueOf(257), BigInteger.valueOf(263),
            BigInteger.valueOf(269), BigInteger.valueOf(271), BigInteger.valueOf(277), BigInteger.valueOf(281),
            BigInteger.valueOf(283), BigInteger.valueOf(293), BigInteger.valueOf(307), BigInteger.valueOf(311),
            BigInteger.valueOf(313), BigInteger.valueOf(317), BigInteger.valueOf(331), BigInteger.valueOf(337),
            BigInteger.valueOf(347), BigInteger.valueOf(349),
    };


    /**
     * Gera um número primo de baixo nível, usando o teste de primalidade simples de divisão por números primos pequenos.
     * Esse número será utilizado como ponto de partida para o teste de primalidade de Miller-Rabin, que é um teste mais rigoroso
     * para determinar se um número é primo. O objetivo é evitar testar diretamente a primalidade de um número grande com o teste de Miller-Rabin,
     * o que seria muito lento. Em vez disso, o teste é realizado primeiro em um número menor e mais fácil de verificar,
     * e apenas depois é aplicado ao número maior e mais complexo.
     *
     * @param n O número de bits que o número primo deve ter.
     * @return Um número primo de baixo nível.
     */
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

    /**
     * Cria um potencial número primo
     * Faz um primeiro despiste para descobrir se o número gerado é um potencial primo
     * @param n número de bits do primos
     * @return potencial primo
     */
    private static BigInteger getLowLevelPrime(int n) {
        while (true) {
            BigInteger pc = nBitRandom(n);

            for (int i = 0; i < FIRST_PRIMES_LIST.length; i++) {
                BigInteger divisor = FIRST_PRIMES_LIST[i];

                // se o número for divisível pelo primo da lista e não for primo,
                // retorna para gerar um novo número
                if (pc.mod(divisor).equals(BigInteger.ZERO) && divisor.pow(2).compareTo(pc) <= 0) {
                    break;
                } else {
                    return pc;
                }
            }
        }
    }
    /**
     * Verifica se um número é composto utilizando o teste de Miller-Rabin.
     *
     * @param roundTester O número a ser testado.
     * @param ec O valor de "e" utilizado no teste.
     * @param mrc O valor de "m" utilizado no teste.
     * @param maxDivisionsByTwo O número máximo de divisões por dois permitido.
     * @return Retorna verdadeiro se o número for provavelmente composto, falso caso contrário.
     */
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

    /**
     * Executa o teste de primalidade de Miller-Rabin no número inteiro positivo mrc.
     * @param mrc O número a ser testado para primalidade.
     * @return true se o número passou no teste de Miller-Rabin, caso contrário, retorna false.
     */
    private static boolean isMillerRabinPassed(BigInteger mrc) {
        SecureRandom rng = new SecureRandom();
        int maxDivisionsByTwo = 0;
        BigInteger ec = mrc.subtract(BigInteger.ONE);

        // Encontra o valor de "e" e "k", onde mrc-1 = 2^k * e
        while (ec.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            ec = ec.shiftRight(1);
            maxDivisionsByTwo++;
        }
        // Executa o teste de Miller-Rabin com 40 rodadas
        for (int i = 0; i < 40; i++) {
            BigInteger roundTester = new BigInteger(mrc.bitLength(), rng);
            if (trialComposite(roundTester, ec, mrc, maxDivisionsByTwo)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 
     * Gera um novo número primo
     *
     * @param bits número de bits 
     * @return número primo
     */
    public static BigInteger get_newPrime(int bits ){
        BigInteger prime_canditate;

        while (true){
            prime_canditate = getLowLevelPrime(bits);
            if (isMillerRabinPassed(prime_canditate) ){
                break;
            }

        }
        return prime_canditate;
    }
}
