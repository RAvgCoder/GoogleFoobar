import java.util.HashMap;
// For proper visualization, render the java doc preferably using IntelliJ

/**
 * The Grandest Staircase Of Them All
 * <p>
 * ==================================
 * <p>
 * With the LAMBCHOP doomsday device finished, Commander Lambda is preparing to debut on the galactic stage -- but
 * in order to make a grand entrance, Lambda needs a grand staircase! As the Commander's personal assistant, you've been
 * tasked with figuring out how to build the best staircase EVER. Lambda has given you an overview of the types of bricks available,
 * plus a budget. You can buy different amounts of the different types of bricks (for example, 3 little pink bricks, or 5 blue lace bricks).
 * Commander Lambda wants to know how many different types of staircases can be built with each amount of bricks, so they can pick the one with the most options.
 * Each type of staircase should consist of 2 or more steps.  No two steps are allowed to be at the same height - each step
 * must be lower than the previous one. All steps must contain at least one brick.
 * <p>
 * A step's height is classified as the total amount of bricks that make up that step.
 * <p>
 * For example, when N = 3, you have only 1 choice of how to build the staircase, with the first step having a height of
 * 2 and the second step having a height of 1:
 * <p>
 * (# indicates a brick)
 * <p>
 * #
 * <p>
 * ##
 * <p>
 * 21
 * <p>
 * When N = 4, you still only have 1 staircase choice:
 * <p>
 * #
 * <p>
 * #
 * <p>
 * ##
 * <p>
 * 31
 * <p>
 * But when N = 5, there are two ways you can build a staircase from the given bricks. The two staircases can have heights (4, 1) or (3, 2), as shown below:
 * <p>
 * #
 * <p>
 * #
 * <p>
 * #
 * <p>
 * ##
 * <p>
 * 41
 * <p>
 * <p>
 * #
 * <p>
 * ##
 * <p>
 * ##
 * <p>
 * 32
 * <p>
 * Write a function called solution(n) that takes a positive integer n and returns the number of different staircases
 * that can be built from exactly n bricks. n will always be at least 3 (so you can have a staircase at all), but no more
 * than 200, because Commander Lambda's not made of money!
 * <p>
 * Test cases
 * <p>
 * ==========
 * <p>
 * Input:Solution.solution(200)Output:    487067745
 * <p>
 * Input:Solution.solution(3)Output:    1
 */
public class Level3part1 {
    // HashMap to store already calculated results for specific subproblems
    private static final HashMap<String, Integer> memoizationMap = new HashMap<>();
    // The total number of different staircases that can be built
    private static int ans = -1;

    public static void main(String[] args) {
        // Test case: Calculate staircases for 200 bricks
        System.out.println("Number of staircases for 200 bricks: " + solution(200));
    }

    // Function to calculate the number of different staircases
    public static int solution(int n) {
        generateDecisionTree(0, n);
        return ans;
    }

    // Recursive function to generate the decision tree and calculate staircases
    private static void generateDecisionTree(int lastStepNum, int remainingAfterPreviousStep) {
        // Iterate through possible step heights
        for (int currentStep = lastStepNum + 1; currentStep <= remainingAfterPreviousStep; currentStep++) {
            int remainingAfterCurrentStep = (remainingAfterPreviousStep - currentStep);

            // If no bricks remain, we have found a valid staircase
            if (remainingAfterCurrentStep == 0) ans++;
                // If the current step height is less than remaining bricks, continue generating the tree
            else if (currentStep < remainingAfterCurrentStep) {

                // Create a unique key to represent the subproblem
                String subproblemKey = currentStep + "," + remainingAfterCurrentStep;

                // Check if the result for this subproblem is already calculated
                if (memoizationMap.containsKey(subproblemKey)) {
                    ans += memoizationMap.get(subproblemKey);
                } else {
                    // Takes a snapshot of the current answer at current level before entering a new one
                    int nodeAnswerSnapShot = ans;

                    // Generate decision trees for the current step
                    generateDecisionTree(currentStep, remainingAfterCurrentStep);

                    // Store the result for this subproblem in the memoization map
                    memoizationMap.put(subproblemKey, ans - nodeAnswerSnapShot);
                }
            }
        }
    }
}
