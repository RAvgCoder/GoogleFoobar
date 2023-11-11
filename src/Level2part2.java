/**
 * ===========Please Pass the Coded Messages===========
 * <p>
 * You need to pass a message to the bunny workers, but to avoid detection, the code you agreed to use is... obscure, to say the least.
 * The bunnies are given food on standard-issue plates that are stamped with the numbers 0-9 for easier sorting, and you
 * need to combine sets of plates to create the numbers in the code. The signal that a number is part of the code is that it is
 * divisible by 3. You can do smaller numbers like 15 and 45 easily, but bigger numbers like 144 and 414 are a little trickier.
 * Write a program to help yourself quickly create large numbers for use in the code, given a limited number of plates to work with.You have L,
 * a list containing some digits (0 to 9). Write a function solution(L) which finds the largest number that can be made from some
 * or all of these digits and is divisible by 3. If it is not possible to make such a number, return 0 as the solution.
 * L will contain anywhere from 1 to 9 digits.  The same digit may appear multiple times in the list, but each element in
 * the list may only be used once.
 * </p>
 * <p>
 * </p>
 * ==========Test cases==========
 * </p>
 * <p>
 * Your code should pass the following test cases.Note that it may also be run against hidden test cases not shown here.
 * </p>
 * <p>
 * -- Java cases --
 * </p>
 * Input:Solution.solution({3, 1, 4, 1})Output:    4311
 * <p>
 * Input:Solution.solution({3, 1, 4, 1, 5, 9})Output:    94311
 */
public class Level2part2 {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 1, 7, 1, 1, 2})); // 0
    }

    public static int solution(int[] num) {
        int[] hashMap = new int[10];

        int sum = 0;
        for (int n : num) {
            hashMap[n]++;
            sum += n;
        }

        int originMod = sum % 3;
        int changingModVal = originMod;
        int prevModVal = changingModVal;
        // Remove numbers
        while (changingModVal != 0) { // Num isn't yet divisible by 3
            while (changingModVal < 10) {
                if (hashMap[changingModVal] != 0) {
                    hashMap[changingModVal]--;
                    // Recalculate the mod value if a number still needs to be removed
                    changingModVal = (sum -= changingModVal) % 3;
                    break;
                }
                changingModVal += 3;
            }

            // if its ment to end but the mod val initially was 1, then try a mod value of 2
            if (changingModVal != 0 && originMod != 2) {
                changingModVal = 2;
                originMod = 2;
            }

            // If it exhausted the current mod value, try decrementing it to see if you can get 2 numbers you can use to get the true value
            if (changingModVal >= 10) changingModVal = --prevModVal;
        }

        // Build the resulting numbers
        int ans = 0;
        for (int i = 9; i >= 0; i--) {
            while (hashMap[i] != 0) {
                ans = (10 * ans) + i;
                hashMap[i]--;
            }
        }

        return (ans % 3 != 0) ? 0 : ans;
    }
}
