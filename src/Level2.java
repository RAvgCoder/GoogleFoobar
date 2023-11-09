import java.util.Arrays;

public class Level2 {
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
