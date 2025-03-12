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

    public long nextLong() {
        int c;
        do {
            c = nextByte();
        } while (c <= ' ');
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = nextByte();
        }
        long res = 0;
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
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();

    }

    private int find(int[] root, int u) {
        if (root[u] != u)
            root[u] = find(root, root[u]); // path compression
        return root[u];
    }

    // private void unionSets(int a, int b, int[] parent, int[] size) {
    // a = findSet(a, parent);
    // b = findSet(b, parent);
    // if (a != b) {
    // if (size[a] < size[b]) {
    // a = a ^ b;
    // b = a ^ b;
    // a = a ^ b;
    // }
    // parent[b] = a;
    // size[a] += size[b];
    // }
    // }

    private void union(int[] root, int[] size, int u, int v) {
        int set_u = find(root, u);
        int set_v = find(root, v);

        if (set_u != set_v) {
            if (size[set_u] < size[set_v]) {
                root[set_u] = set_v;
                size[set_v] += size[set_u];
            } else if (size[set_u] > size[set_v]) {
                root[set_v] = set_u;
                size[set_u] += size[set_v];
            } else {
                root[set_v] = set_u;
                size[set_u] += size[set_v];
            }
        }
    }

    public void solve() {
        int[] root = new int[numVertex + 1];
        int[] size = new int[numVertex + 1];
        int components = numVertex;
        int largestSize = 1;

        // dsu initialize
        for (int i = 0; i <= numVertex; i++) {
            root[i] = i;
            size[i] = 1;
        }

        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();

            if (find(root, u) != find(root, v)) {
                union(root, size, u, v);
                components--;
                if (largestSize < size[find(root, u)]) {
                    largestSize = size[find(root, u)];
                }
            }
            obj.println(components + " " + largestSize);
        }

    }

    public void close() {
        obj.close();
    }
}

public class dsu implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new dsu(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.solve();
        g.close();
    }
}
