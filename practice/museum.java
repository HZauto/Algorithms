import java.io.*;
import java.util.*;

class FastIO extends PrintWriter {
    private InputStream stream;
    private byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    // standard input
    public FastIO() {
        this(System.in, System.out);
    }

    public FastIO(InputStream i, OutputStream o) {
        super(o);
        stream = i;
    }

    // file input
    public FastIO(String i, String o) throws IOException {
        super(new FileWriter(o));
        stream = new FileInputStream(i);
    }

    // throws InputMismatchException() if previously detected end of file
    private int nextByte() {
        if (numChars == -1) {
            throw new InputMismatchException();
        }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars == -1) {
                return -1; // end of file
            }
        }
        return buf[curChar++];
    }
    // to read in entire lines, replace c <= ' '

    // with a function that checks whether c is a line break
    public String next() {
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = nextByte();
        } while (c > ' ');
        return res.toString();
    }

    public int nextInt() { // nextLong() would be implemented similarly
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = nextByte();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res = 10 * res + c - '0';
            c = nextByte();
        } while (c > ' ');
        return res * sgn;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
}

class Graph {

    FastIO obj;
    private int parentI;
    private int parentJ;
    Pair[][] parent;
    int sum;

    private static class Pair {
        int start;
        int end;

        Pair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    Graph() {
        obj = new FastIO();
        int n = obj.nextInt();
        int m = obj.nextInt();
        int k = obj.nextInt();
        parent = new Pair[n][m];
        int[][] result = new int[n][m];
        char[][] grid = new char[n][m];
        boolean visited[][] = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            String str = obj.next();
            for (int j = 0; j < m; j++) {
                grid[i][j] = str.charAt(j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '.' && !visited[i][j]) {
                    parentI = i;
                    parentJ = j;
                    sum = 0;
                    DFS(grid, visited, i, j);
                    result[i][j] = sum;
                }
            }
        }

        for (int i = 0; i < k; i++) {
            int x = obj.nextInt();
            int y = obj.nextInt();

            obj.println(result[parent[x - 1][y - 1].start][parent[x - 1][y - 1].end]);
        }

    }

    private void DFS(char[][] grid, boolean[][] visited, int i, int j) {
        visited[i][j] = true;
        parent[i][j] = new Pair(parentI, parentJ);

        if (grid[i - 1][j] == '*')
            sum++;
        if (grid[i + 1][j] == '*')
            sum++;
        if (grid[i][j + 1] == '*')
            sum++;
        if (grid[i][j - 1] == '*')
            sum++;

        // right
        if (grid[i][j + 1] == '.') {
            if (!visited[i][j + 1])
                DFS(grid, visited, i, j + 1);
        }
        // down
        if (grid[i + 1][j] == '.') {
            if (!visited[i + 1][j])
                DFS(grid, visited, i + 1, j);
        }
    }

    public void close() {
        obj.close();
    }

}

public class museum implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new museum(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.close();
    }
}
