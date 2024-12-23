import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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

    List<List<Integer>> matrix;

    int numVertex;
    int numEdge;

    Graph() {

        obj = new FastIO();
        numVertex = obj.nextInt();
        matrix = new ArrayList<>();
        for (int i = 0; i < numVertex; i++) {
            matrix.add(new ArrayList<>());
        }

        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                String weight = obj.next();
                if (weight.equals("INF")) {
                    matrix.get(i).add(Integer.MAX_VALUE);
                } else {
                    matrix.get(i).add(Integer.valueOf(weight));
                }
            }
        }
    }

    public void floydWarshall() {
        List<List<Long>> d = new ArrayList<>();

        for (int i = 0; i < numVertex; i++) {
            d.add(new ArrayList<>());
        }
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                d.get(i).add((long) matrix.get(i).get(j));
            }
        }
        // for (int i = 0; i < numVertex; i++) {
        // for (int j = 0; j < numVertex; j++) {
        // obj.print(d.get(i).get(j));
        // obj.print(" ");
        // }
        // obj.println();
        // }
        for (int k = 0; k < numVertex; k++) {
            for (int i = 0; i < numVertex; i++) {
                for (int j = 0; j < numVertex; j++) {
                    if (d.get(i).get(k) + d.get(k).get(j) < d.get(i).get(j)) {
                        d.get(i).set(j, d.get(i).get(k) + d.get(k).get(j));
                    }
                }
            }
        }
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                obj.print(d.get(i).get(j));
                obj.print(" ");
            }
            obj.println();
        }
    }

    public void close() {
        obj.close();
    }
}

public class AllPairShortestPath implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new AllPairShortestPath(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.floydWarshall();
        g.close();
    }
}