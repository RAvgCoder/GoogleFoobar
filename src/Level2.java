import java.util.Arrays;

public class Level2 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 4}, 15)));
    }

    public static int[] solution(int[] arr, int ans) {
        int curr_ptr = 0;
        int sub_ptr = -1;
        int[] prefixSum = new int[arr.length];

        // Compute prefix sum
        int sum = 0;
        for (int i = 0; i < prefixSum.length; i++) {
            prefixSum[i] = arr[i] + sum;
            sum += arr[i];
        }

        // Check the prefix sum for the sublist
        while (sub_ptr <= curr_ptr && curr_ptr < prefixSum.length) {
            int subSum = (sub_ptr == -1) ? 0 : prefixSum[sub_ptr];
            int currSum = prefixSum[curr_ptr] - subSum;

            if (currSum == ans) break;
            else if (currSum < ans) curr_ptr++;
            else sub_ptr++;
        }


        int[] ansArray;
        // If the `curr_ptr` grew past the bounds of the array
        if (curr_ptr == prefixSum.length)
            ansArray = new int[]{-1, -1};
        /*
         If the sum was found, you add 1 to sub_ptr as sub_ptr stop at the number being
         subtracted, and we only want numbers that would contribute to the sum
         */
        else
            ansArray = new int[]{sub_ptr + 1, curr_ptr};

        return ansArray;
    }
}
