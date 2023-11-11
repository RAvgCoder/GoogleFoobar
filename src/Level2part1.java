import java.util.Arrays;

/**
 * You are given an array of integers arr and a target sum ans.
 * Find a contiguous sublist within the array such that the sum of its elements is equal to the target sum.
 * The function should return an array representing the starting and ending indices of the sublist.
 * If not found return -1,-1
 * <p>
 * -- Test cases --
 * <p>
 * The array = {1,2,3,4}, Sum = 15 => {-1,-1}
 * <p>
 * The array = {4,3,10,2,8}, Sum = 12 => {2,3}
 */
public class Level2part1 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 4}, 15)));
    }

    public static int[] solution(int[] arr, int ans) {
        int currPtr = 0; // Starts at the beginning of the list
        int subPtr = -1; // Starts before the list as you don't want to subtract anything yet
        int[] prefixSum = new int[arr.length];

        // Compute prefix sum
        int prefix_sum = 0;
        for (int i = 0; i < prefixSum.length; i++) {
            prefixSum[i] = arr[i] + prefix_sum;
            prefix_sum += arr[i];
        }

        // Check the prefix sum for the sublist
        while (subPtr <= currPtr && currPtr < prefixSum.length) {
            int subSum = (subPtr == -1) ? 0 : prefixSum[subPtr];
            int currSum = prefixSum[currPtr] - subSum;

            if (currSum == ans) break;
            else if (currSum < ans) currPtr++;
            else subPtr++;
        }

        int[] ansArray;
        // If the `curr_ptr` grew past the bounds of the array
        if (currPtr == prefixSum.length)
            ansArray = new int[]{-1, -1};
        /*
         If the sum was found, you add 1 to sub_ptr as sub_ptr stop at the number being
         subtracted, and we only want numbers that would contribute to the sum
         */
        else
            ansArray = new int[]{subPtr + 1, currPtr};

        return ansArray;
    }
}
