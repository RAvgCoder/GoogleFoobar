import java.util.LinkedHashSet;

/**
 * =====Re-ID=====
 * <p>
 * There's some unrest in the minion ranks: minions with id numbers like: "1", "42" and other "good" numbers have been
 * lording it over the poor minions who are stuck with more boring IDs. To quell the unrest, Commander Lambda has tasked
 * you with reassigning everyone new random IDs based on a completely fullproof scheme.
 * </p>
 * Commander Lambda has concatenated the prime numbers in a single long string: "2357111317192329..."
 * Now every minion must draw a number from a hat. That number is the starting index in that string of primes,
 * and the minions new ID number will be the next five digits in the string. So if a minion draw "3", their id will be "71113".
 * <p>
 * Help the Commander assign these IDs by writing a function solution(n) which takes in the starting index n of
 * Lambda's string of all primes, and returns the next five digits in the string. Commander Lambda has a lot of minions,
 * so the value of n will be between 0 and 10000.
 * </p>
 * <p>
 * </p>
 * <p>
 * -- Java cases --
 * </p>
 * Input:
 * Solution.solution(0)
 * Output:
 * 23571
 * <p>
 * Input:
 * Solution.solution(3)
 * Output:
 * 71113
 */
public class Level1 {
    /**
     * Keeps track of the generated prime numbers.
     */
    static LinkedHashSet<Integer> primes = new LinkedHashSet<>();
    static StringBuilder generatedPrimeString = new StringBuilder();

    public static void main(String[] args) {
        System.out.println(solution(999999));
    }

    /**
     * Generates a string of prime numbers up to the specified index plus 5.
     *
     * @param primeIndex The index to generate prime numbers up to.
     * @return A string containing the specified number of prime numbers.
     */
    public static String solution(int primeIndex) {
        // Generates the prime string starting at the index specified + 5
        nthPrime(primeIndex+5,primeIndex);

        // Extract the required numbers
        return generatedPrimeString.substring(primeIndex, primeIndex + 5);
    }


    /**
     * Generates the nth prime number.
     *
     * @param nthPrime The index of the prime number to generate.
     * @param primeIndex The index of the prime number you are looking for
     * been reached or false if it hasn't
     */
    public static void nthPrime(int nthPrime, int primeIndex) {
        int num = 2;
        while (nthPrime != 0) {
            if (isPrime(num)) {
                if (!primes.contains(num)) {
                    generatedPrimeString.append(num);
                    // Checks if value searching for has been reached
                    if (generatedPrimeString.length() > primeIndex + 5)
                        return;
                }
                primes.add(num);
                nthPrime--;
            }
            num++;
        }
        --num;
    }

    /**
     * Checks if a given number is prime.
     *
     * @param num The number to check for primality.
     * @return True if the number is prime, false otherwise.
     */
    private static boolean isPrime(int num) {
        int primeRange = (int) Math.ceil(Math.sqrt(num));
        if (primes.isEmpty()) return true;
        int stop = 0;
        for (int prime : primes) {
            stop = prime;
            if (prime > primeRange || num == prime) return true;
            if (num % prime == 0) return false;
        }
        for (int i = stop; i < primeRange; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}