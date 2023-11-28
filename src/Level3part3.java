import java.util.ArrayList;
import java.util.Arrays;

/**
 * Doomsday Fuel=============
 * <p>
 * Making fuel for the LAMBCHOP's reactor core is a tricky process because of the exotic matter involved. It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel. Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state of a given ore sample. You have carefully studied the different structures that the ore can take and which transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed. That is, each time the ore is in 1 state, it has the same probabilities of entering the next state (which might be the same state).  You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms that the ore can become, but you haven't seen all of them.Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state has gone to the next state and return an array of ints for each terminal state giving the exact probabilities of each terminal state, represented as the numerator for each state, then the denominator for all of them at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during the calculation, as long as the fraction is simplified regularly. For example, consider the matrix m:[  [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability  [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities  [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)  [0,0,0,0,0,0],  # s3 is terminal  [0,0,0,0,0,0],  # s4 is terminal  [0,0,0,0,0,0],  # s5 is terminal]So, we can consider different paths to terminal states, such as:s0 -> s1 -> s3s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4s0 -> s1 -> s0 -> s5Tracing the probabilities of each, we find thats2 has probability 0s3 has probability 3/14s4 has probability 1/7s5 has probability 9/14So, putting that together, and making a common denominator, gives an answer in the form of[s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is[0, 3, 2, 9, 14].
 * <p>
 * Languages=========
 * To provide a Java solution, edit Solution.java
 * To provide a Python solution, edit solution.py
 * <p>
 * Test cases==========
 * Your code should pass the following test cases.Note that it may also be run against hidden test cases not shown here.
 * <p>
 * -- Java cases --
 * Input:Solution.solution({{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}})Output:    [0, 3, 2, 9, 14]
 * <p>
 * Input:Solution.solution({{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0,0}, {0, 0, 0, 0, 0}})Output:    [7, 6, 8, 21]
 */
public class Level3part3 {
    public static void main(String[] args) {
        int m[][]=
                {
                        {0, 1, 0, 0, 0, 1},
                        {4, 0, 0, 3, 2, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0}
                };

        System.out.println(solution(m));
    }

    public static int[] solution(int[][] m) {
        // Your code here
        Matrix orderMatrix = new Matrix(m);
        orderMatrix.absorb();
        orderMatrix.print("Absorb");

        // now separate into R,Q

        Matrix subMatrixR = Matrix.subMatrix(orderMatrix, 0, orderMatrix.IDENTITY_SIZE-1, orderMatrix.IDENTITY_SIZE, orderMatrix._int.length);
        subMatrixR.print("Sub_R");

        Matrix subMatrixQ = findQ(orderMatrix);
        subMatrixQ.print("Sub_Q");

        // Calculate F {(I-Q)^-1}
        Matrix identity = Matrix.identity(subMatrixQ.lenY());
        identity.print("Identity");

        Matrix I_Q = identity.sub(subMatrixQ);
        I_Q.print("I_Q");

         int[] a = new int[1];
        return a;

    }

    private static Matrix findQ(Matrix orderMatrix) {
        Matrix Q = Matrix.Matrix_float(Matrix.subMatrix(orderMatrix,orderMatrix.IDENTITY_SIZE-1,orderMatrix.IDENTITY_SIZE-1,orderMatrix._int.length,orderMatrix._int.length)._int);
        int divisor = Arrays.stream(orderMatrix._int[orderMatrix._int.length-1]).sum();
        for(int i = 1; i < Q._float.length; i++){
            for (int j = 1; j < Q._float.length; j++) {
                Q._float[i][j] /= divisor;
            }
        }
        return Q;
    }


}

class Matrix{
    int[][] _int;
    float[][] _float;
    int IDENTITY_SIZE = 0;
    public int lenX(){
        return ((_int != null) ?_int[0].length : _float[0].length) - ((IDENTITY_SIZE == -1) ? 0: 1);
    }
    public int lenY(){
        return ((_int != null) ?_int : _float).length - ((IDENTITY_SIZE == -1) ? 0: 1);
    }

    public static Matrix identity(int size){
        Matrix m = new Matrix();
        m.IDENTITY_SIZE = -1;
        m._int = new int[size][size];
        for (int i = 0; i < size; i++) {
            m._int[i][i] = 1;
        }
        return m;
    }

    private Matrix(){}

    public static Matrix Matrix_float(int[][] n){
        Matrix m = new Matrix();
        m._float = new float[n.length][n[0].length];

        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                m._float[i][j] = n[i][j];
            }
        }
        return m;
    }

    public Matrix(int[][] n){
        _int = new int[n.length+1][n[0].length+1];

        for (int i = 0; i < _int.length; i++){
            _int[i][0] = ('A'-1)+i;
        }

        for (int i = 0; i < _int[0].length; i++) {
            _int[0][i] = ('A'-1)+i;
        }
        _int[0][0] = '$';

        for (int i = 0; i < n.length; i++) {
            System.arraycopy(n[i], 0, _int[i + 1], 1, n[0].length);
        }
    }

    public static Matrix subMatrix(Matrix orderMatrix, int startX, int startY, int endX, int endY) {
        int[][] sub = new int[(endY-1)-startY][(endX-1)-startX];
        int y = startY;
        for (int i = 0; i < sub.length; i++) {
            int x = startX;
            for (int j = 0; j < sub[0].length; j++) {
                sub[i][j] = orderMatrix.getNum(y,x);
                x++;
            }
            y++;
        }
        Matrix res = new Matrix(sub);
        // Copy over the letters
        // Row
        for (int i = 1; i < res._int[0].length; i++) {
            res.setNum(-1,-1+i, orderMatrix.getNum(-1,-1+i));
        }

        // Column
        System.arraycopy(orderMatrix._int[0], 0, res._int[0], 0, res._int.length);
        return res;
    }


    public void absorb(){
        ArrayList<int[]> terminal = new ArrayList<>();
        ArrayList<int[]> non_terminal = new ArrayList<>();

        // Make terminal and non-terminal
        terminal.add(_int[0]);
        for (int i = 1; i < _int.length; i++) {
            int[] ints = _int[i];
            int sum = 0;
            for (int j = 1; j < _int.length; j++) {
                sum += _int[i][j];
            }
            if (sum == 0 || sum == 1) terminal.add(ints);
            else non_terminal.add(ints);
        }

        IDENTITY_SIZE = terminal.size();

        int i =0;
        for (int[] e : terminal){
            _int[i] = e;
            i++;
        }

        int snap = i;
        for (int[] ints : non_terminal) {
            _int[i++] = ints;
        }

        // Fix naming
        for (i = 0; i < _int.length; i++) {
            _int[0][i] = _int[i][0];
        }

        for (i = snap; i < _int.length; i++) {
            int[] clone = _int[i].clone();
            for (int j = 1; j < _int[0].length; j++, snap++) {
                _int[i][findIndex(j)] = clone[j];
            }
        }

        // TODO Fix if already has identity and is not aligned
        // Create identity for terminal states
        i = 0;
        for (int[] row : terminal) {
            row[i++] = 1;
        }
        _int[0][0] = '$';
    }

    private int getNum(int i, int j) {
        return _int[i+1][j+1];
    }
    private void setNum(int i, int j, int val) {
        _int[i+1][j+1] = val;
    }


    private int findIndex(int j) {
        for (int i = _int.length-1; i >= 0; i--) {
            if (('A'-1)+j == _int[0][i])
                return i;
        }
        throw new IllegalStateException("Shouldn't reach this point, index should be found");
    }

    public void print(String name) {
        int j = 1;
        System.out.println(name);
        if (_int != null) {
            for (int[] n : _int) {
                int i = 1;
                for (int e : n) {
                    System.out.print(e + "\t");
                    if (i++ == IDENTITY_SIZE) System.out.print("|\t");
                }
                if (j++ == IDENTITY_SIZE) {
                    System.out.println();
                    System.out.print("-\t".repeat(n.length + 1));
                }
                System.out.println();
            }
        }else {
            for (float[] n : _float) {
                int i = 1;
                for (float e : n) {
                    System.out.print(e + "\t");
                    if (i++ == IDENTITY_SIZE) System.out.print("|\t");
                }
                if (j++ == IDENTITY_SIZE) {
                    System.out.println();
                    System.out.print("-\t".repeat(n.length + 1));
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    public void printC(String name) {
        int j =1;
        System.out.println(name);
       if (_int != null) {
            for (int[] n : _int) {
                int i = 1;
                for (int e : n) {
                    System.out.print((char) e + "\t");
                    if (i++ == IDENTITY_SIZE) System.out.print("|\t");
                }
                if (j++ == IDENTITY_SIZE) {
                    System.out.println();
                    System.out.print("-\t".repeat(n.length + 1));
                }
                System.out.println();
            }
        }else {
            for (float[] n : _float) {
                int i = 1;
                for (float e : n) {
                    System.out.print((char) e + "\t");
                    if (i++ == IDENTITY_SIZE) System.out.print("|\t");
                }
                if (j++ == IDENTITY_SIZE) {
                    System.out.println();
                    System.out.print("-\t".repeat(n.length + 1));
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public Matrix sub(Matrix other) {
        int t_i = (this.IDENTITY_SIZE == -1) ? 0 : 1;
        int t_j = (this.IDENTITY_SIZE == -1) ? 0 : 1;
        int o_i = (other.IDENTITY_SIZE == -1) ? 0 : 1;
        int o_j = (other.IDENTITY_SIZE == -1) ? 0 : 1;

        Matrix m = new Matrix();
        m._float = new float[this.lenY()][this.lenX()];
        m.IDENTITY_SIZE = -1;

        for (int i = 0; i < _int.length; o_i++, t_i++, i++) {
            for (int j = 0; j < _int.length; t_j++, o_j++, j++) {
                m._float[i][j] = _int[t_i][t_j] - other._float[o_i][o_j];
            }
            t_j = (this.IDENTITY_SIZE == -1) ? 0 : 1;
            o_j = (other.IDENTITY_SIZE == -1) ? 0 : 1;;
        }
        return m;
    }
}


