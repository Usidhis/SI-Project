package seguranca;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Timer;

/**
 * The type Segurança.
 */
public class Segurança {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
        long[] elapsedTimes = new long[100];
        for (int i = 0; i < 100; i++) {
            long startTime = System.nanoTime();
            BigInteger p1 = PrimeGenerator.get_newPrime(1024);
            long endTime = System.nanoTime();
            long elapsedTimeInNanos = endTime - startTime;
            elapsedTimes[i] = elapsedTimeInNanos;
            System.out.println("Novo Primo "+ p1.toString());
        }

        Arrays.sort(elapsedTimes);

        long medianElapsedTimeInNanos = elapsedTimes[elapsedTimes.length/2];
        System.out.println("Median elapsed time (ns): " + medianElapsedTimeInNanos);



    }
}