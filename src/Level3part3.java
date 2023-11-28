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
        int[][] m =
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

        Matrix subMatrixR = Matrix.subMatrix(orderMatrix, 0, orderMatrix.IDENTITY_SIZE - 1, orderMatrix.IDENTITY_SIZE, orderMatrix._int.length);
        subMatrixR.print("Sub_R");

        Matrix subMatrixQ = findQ(orderMatrix);
        subMatrixQ.print("Sub_Q");

        // Calculate F {(I-Q)^-1}
        Matrix identity = Matrix.identity(subMatrixQ.lenY());
        identity.print("Identity");

        System.out.println(Frac.simplify(new Frac(4,8)));

        // I - Q
        Matrix I_Q = identity.sub(subMatrixQ);
        I_Q.print("I_Q");


        // {(I-Q)^-1}
        Matrix.inverse(I_Q);

        int[] a = new int[1];
        return a;

    }

    private static Matrix findQ(Matrix orderMatrix) {
        Matrix Q = Matrix.Matrix_float(Matrix.subMatrix(orderMatrix, orderMatrix.IDENTITY_SIZE - 1, orderMatrix.IDENTITY_SIZE - 1, orderMatrix._int.length, orderMatrix._int.length)._int);
        for (int i = 1; i < Q._frac.length; i++) {
            int divisor = Arrays.stream(orderMatrix._int[
                    (orderMatrix._int.length - (Q._frac.length - i))
                    ]).sum() - orderMatrix._int[
                    (orderMatrix._int.length - (Q._frac.length - i))
                    ][0];
            for (int j = 1; j < Q._frac.length; j++) {
                Q._frac[i][j].setDenom(divisor);
            }
        }
        return Q;
    }


}

class Matrix {
    int[][] _int;
    Frac[][] _frac;
    int IDENTITY_SIZE = 0;

    private Matrix() {
    }

    public Matrix(int[][] n) {
        _int = new int[n.length + 1][n[0].length + 1];

        for (int i = 0; i < _int.length; i++) {
            _int[i][0] = ('A' - 1) + i;
        }

        for (int i = 0; i < _int[0].length; i++) {
            _int[0][i] = ('A' - 1) + i;
        }
        _int[0][0] = '$';

        for (int i = 0; i < n.length; i++) {
            System.arraycopy(n[i], 0, _int[i + 1], 1, n[0].length);
        }
    }

    public static Matrix identity(int size) {
        Matrix m = new Matrix();
        m.IDENTITY_SIZE = -1;
        m._int = new int[size][size];
        for (int i = 0; i < size; i++) {
            m._int[i][i] = 1;
        }
        return m;
    }

    public static Matrix Matrix_float(int[][] n) {
        Matrix m = new Matrix();
        m._frac = new Frac[n.length][n[0].length];

        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                m._frac[i][j] = new Frac();
                m._frac[i][j].nom = n[i][j];
            }
        }
        return m;
    }

    public static Matrix subMatrix(Matrix orderMatrix, int startX, int startY, int endX, int endY) {
        int[][] sub = new int[(endY - 1) - startY][(endX - 1) - startX];
        int y = startY;
        for (int i = 0; i < sub.length; i++) {
            int x = startX;
            for (int j = 0; j < sub[0].length; j++) {
                sub[i][j] = orderMatrix.getNum(y, x);
                x++;
            }
            y++;
        }
        Matrix res = new Matrix(sub);
        // Copy over the letters
        // Row
        for (int i = 1; i < res._int[0].length; i++) {
            res.setNum(-1, -1 + i, orderMatrix.getNum(-1, -1 + i));
        }

        // Column
        System.arraycopy(orderMatrix._int[0], 0, res._int[0], 0, res._int.length);
        return res;
    }

    public static Frac[][] inverse(Matrix I_Q) {
        Frac[][] matrix = I_Q._frac;

        Frac[][] res = new Frac[I_Q.lenY()][I_Q.lenY()];
        Frac det = I_Q.determinant(matrix, I_Q.lenY());

        if (det.equals(new Frac())) return res;
        Frac[][] adjoint = new Frac[I_Q.lenY()][I_Q.lenY()];
        adjoint(matrix,adjoint);
        return res;
    }

    private static void adjoint(Frac[][] matrix, Frac[][] adjoint) {
        int N = matrix.length-1;
        if (N == 1) {
            adjoint[0][0] = new Frac(1,1);
            return;
        }

        Frac sign = new Frac(1,1);
        Frac[][] tmp = new Frac[N][N];

    }

    public Frac determinant(Frac[][] matrix, int n) {
        Frac result = new Frac();

        if (n == 1)
            return _frac[0][0];

        Frac sign = new Frac(1,1);

        Frac[][] tmp = new Frac[lenY()][lenY()];
        for (int i = 0; i < lenY(); i++) {
            getCofactors(matrix,tmp,0,i,n);
            result.accumulate(sign.mul(_frac[0][i]).mul(determinant(tmp,n-1)));
            sign.neg();
        }
        return result;
    }

    private void getCofactors(Frac[][] matrix, Frac[][] tmp, int i, int j, int n) {
        int x = 0;
        int y = 0;
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                if (k != i && l != j) {
                    tmp[x][y] = matrix[k][l].copy();

                    if (++y == n - 1) {
                        y = 0;
                        i++;
                    }
                }
            }
        }
    }

    public int lenX() {
        return ((_int != null) ? _int[0].length : _frac[0].length) - ((IDENTITY_SIZE == -1) ? 0 : 1);
    }

    public int lenY() {
        return ((_int != null) ? _int : _frac).length - ((IDENTITY_SIZE == -1) ? 0 : 1);
    }

    public void absorb() {
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

        int i = 0;
        for (int[] e : terminal) {
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
        return _int[i + 1][j + 1];
    }

    private void setNum(int i, int j, int val) {
        _int[i + 1][j + 1] = val;
    }


    private int findIndex(int j) {
        for (int i = _int.length - 1; i >= 0; i--) {
            if (('A' - 1) + j == _int[0][i])
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
        } else {
            for (Frac[] n : _frac) {
                int i = 1;
                for (Frac e : n) {
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
        int j = 1;
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
        } else {
            for (Frac[] n : _frac) {
                int i = 1;
                for (Frac e : n) {
//                    System.out.print((char) e + "\t");
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
        m._frac = new Frac[this.lenY()][this.lenX()];
        m.IDENTITY_SIZE = -1;

        for (int i = 0; i < _int.length; o_i++, t_i++, i++) {
            for (int j = 0; j < _int.length; t_j++, o_j++, j++) {
                m._frac[i][j] = new Frac();
                m._frac[i][j].sub(_int[t_i][t_j], other._frac[o_i][o_j]);
            }
            t_j = (this.IDENTITY_SIZE == -1) ? 0 : 1;
            o_j = (other.IDENTITY_SIZE == -1) ? 0 : 1;
        }
        return m;
    }
}

class Frac {
    int nom = 0;
    private int denom = 1;
    private boolean isSigned = false;

    public Frac() {
    }

    public Frac(int nominator, int denominator) {
        if (denominator == 0) {
            throw new NumberFormatException("Number cannot have 0 denominator");
        }
        this.nom = nominator;
        denom = denominator;
    }

    public void setDenom(int denom) {
        this.denom = (nom == 0) ? 1 : denom;
    }

    @Override
    public String toString() {
        return (nom == 0 || denom == 1) ? nom + "" : nom + "/" + denom;
    }

    public void sub(int _int, Frac frac) {
        nom = _int - frac.nom;
        isSigned = nom < 0;
        denom = frac.denom;
        simplify(frac);
    }

    public Frac add(Frac other){
        return other;
    }

    public Frac accumulate(Frac other) {
        return null;
    }

    public Frac copy() {
        return new Frac(nom, denom);
    }

    public Frac mul(Frac other) {
        return simplify(new Frac(nom*other.nom,denom* other.denom));
    }

    public static Frac simplify(Frac frac) {
        if (frac.nom == frac.denom)
            return new Frac(1,1);
        else {
            while (true) {
                boolean hit = false;
                for (int i = Math.max(frac.nom,frac.denom); i >= 2; i--) {
                    if (frac.nom%i==0 && frac.denom%i==0) {
                        frac.nom = frac.nom/i;
                        frac.denom = frac.denom/i;
                        hit = true;
                    }
                }
                if (!hit) break;
            }
            return frac;
        }
    }

    public boolean equals(Frac other) {
        return nom == other.nom && denom == other.denom && isSigned == other.isSigned;
    }

    public void neg() {
        isSigned = !isSigned;
    }
}