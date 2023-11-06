import java.util.LinkedHashSet;


public class Level1 {
    /**
     * Keeps track of the generated prime numbers.
     */
    static LinkedHashSet<Integer> primes = new LinkedHashSet<>();

    public static void main(String[] args) {
        System.out.println(solution(3));
    }

    /**
     * Generates a string of prime numbers up to the specified index plus 5.
     *
     * @param i The index to generate prime numbers up to.
     * @return A string containing the specified number of prime numbers.
     */
    public static String solution(int i) {
        StringBuilder generatedPrimeString = new StringBuilder();

        // Generates the prime string up to the index specified + 5
        for (int j = 1; j <= i + 5; j++) {
            if (generatedPrimeString.length() > i + 5)
                break;
            generatedPrimeString.append(nthPrime(j));
        }

        // Extract the required numbers
        return generatedPrimeString.substring(i, i + 5);
    }


    /**
     * Generates the nth prime number.
     *
     * @param p The index of the prime number to generate.
     * @return The nth prime number.
     */
    public static int nthPrime(int p) {
        int num = 2;
        while (p != 0) {
            if (isPrime(num)) {
                primes.add(num);
                p--;
            }
            num++;
        }
        return --num;
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