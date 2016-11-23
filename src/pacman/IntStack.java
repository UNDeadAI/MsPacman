package pacman;

/**
 * Simple IntStack: faster than using a Java Collection.
 */

public class IntStack {
    private int n; // stack size
    private int i; // current index

    private int[] stack; // the data

    public String toString() {
        return n + " : " + i;
    }

    public final boolean isEmpty() {
        return i < 0;
    }

    public final int size() {
        return i + 1;
    }

    public final void reset() {
        i = -1;
    }

    public final int pop() {
        try {
            int x = stack[i];
            i--;
            return x;
        } catch(Exception e) {
            throw new RuntimeException("IntStack pop error: i = " + i);
        }
    }

    public final int peek(int i) {
        return stack[i];
    }

    public final void push(int x) {
        try {
            stack[i+1] = x;
            i++;
        } catch(Exception e) {
            throw new RuntimeException("IntStack push error: " + i);
        }
    }

    public IntStack(int n) {
        this.n = n;
        i = -1;
        stack = new int [n];
    }
}
