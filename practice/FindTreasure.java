import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
    private ArrayList<LinkedList<Integer>> adjacencyList;
    private int numVertex;

    FastIO obj;

    Graph() throws IOException {

        obj = new FastIO();
        int test;
        test = obj.nextInt();
        for (int i = 0; i < test; i++) {
            adjacencyList = new ArrayList<>();
            numVertex = obj.nextInt();
            for (int j = 0; j < numVertex + 1; j++) {
                this.addVertex();
            }
            for (int j = 0; j < numVertex - 1; j++) {
                int m = obj.nextInt();
                for (int k = 0; k < m; k++) {
                    int u = obj.nextInt();
                    this.addEdge(j + 1, u);
                }
            }
            for (int j = 1; j <= numVertex; j++) {
                adjacencyList.get(j).sort(null);
            }
            BFS(1);
        }
        obj.close();
    }

    private void addVertex() {
        this.adjacencyList.add(new LinkedList<Integer>());
    }

    private void addEdge(int u, int v) {
        this.adjacencyList.get(u).add(v);
    }

    public LinkedList<Integer> getAdjList(int vertex) {
        return this.adjacencyList.get(vertex);
    }

    private void BFS(int source) { // source = 1
        boolean visited[] = new boolean[numVertex + 1];
        int prev[] = new int[numVertex + 1];
        int distance[] = new int[numVertex + 1];

        // initialize all vertices as unvisited, no prev, infinite distance
        for (int i = 1; i <= numVertex; i++) {
            visited[i] = false; // not visited
            prev[i] = -1; // no parent
            distance[i] = Integer.MAX_VALUE;
        }
        visited[source] = true; // visited the source index
        distance[source] = 0; // distance of source is 0

        // QUEUE
        Queue<Integer> queue = new LinkedList<>(); // queue te ll add thakbe
        queue.add(source);
        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            for (int adjacent : getAdjList(vertex)) {
                if (!visited[adjacent]) {
                    visited[adjacent] = true; // mark as visited
                    distance[adjacent] = distance[vertex] + 1;
                    prev[adjacent] = vertex;
                    queue.add(adjacent);
                }
            }
        }
        // shortest path
        Stack<Integer> path = new Stack<>();
        if (prev[numVertex] != -1) {
            obj.println(distance[numVertex]);
            int current = prev[numVertex];
            while (current != -1) {
                path.push(current);
                current = prev[current];
            }
            while (!path.empty()) {
                obj.print(path.pop() + " ");
            }
            obj.println();
        } else {
            obj.println(-1);
        }
        obj.println();
    }

}

public class FindTreasure {
    public static void main(String[] args) throws IOException {
        new Graph();
    }
}
