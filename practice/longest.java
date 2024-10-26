import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
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
    private ArrayList<LinkedList<Integer>> in;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() throws IOException {
        adjacencyList = new ArrayList<>();
        in = new ArrayList<>();
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();

        for (int i = 0; i < numVertex + 1; i++) {
            this.addVertex();
        }
        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();
            addEdge(u, v);
        }
        List<Integer> topList = this.topologicalSort();
        int[] distance = new int[numVertex + 1];
        int[] parent = new int[numVertex + 1];

        for (int i = 1; i <= numVertex; i++) {
            distance[i] = Integer.MIN_VALUE;
            parent[i] = -1;
        }

        distance[1] = 1;

        for (int t : topList) {
            for (int prev : in.get(t)) {
                if (distance[prev] + 1 > distance[t]) {
                    distance[t] = distance[prev] + 1;
                    parent[t] = prev;
                }
            }
        }
        if (distance[numVertex] < 0) {
            obj.println("IMPOSSIBLE");
        } else {
            obj.println(distance[numVertex]);
            Stack<Integer> path = new Stack<>();
            int current = numVertex;
            while (current != -1) {
                path.push(current);
                current = parent[current];
            }
            while (!path.empty()) {
                obj.print(path.pop() + " ");
            }
            obj.println();
        }
        obj.close();
    }

    private void addVertex() {
        this.adjacencyList.add(new LinkedList<Integer>());
        this.in.add(new LinkedList<Integer>());
    }

    private void addEdge(int u, int v) {
        this.adjacencyList.get(u).add(v);
        this.in.get(v).add(u);

    }

    public int getVertex() {
        return this.numVertex;
    }

    public LinkedList<Integer> getAdjList(int vertex) {
        return this.adjacencyList.get(vertex);
    }

    public void simpleDFS(int v, boolean[] visited, Stack<Integer> result) {
        visited[v] = true;
        for (int u : getAdjList(v)) {
            if (!visited[u]) {
                simpleDFS(u, visited, result);
            }
        }
        result.push(v);
    }

    public List<Integer> topologicalSort() {
        Stack<Integer> result = new Stack<Integer>();

        boolean[] visited = new boolean[numVertex + 1];

        for (int i = 1; i <= numVertex; i++) {
            visited[i] = false;
        }
        for (int i = 1; i <= numVertex; i++) {
            if (!visited[i]) {
                simpleDFS(i, visited, result);
            }
        }
        ArrayList<Integer> topOrder = new ArrayList<Integer>();
        while (!result.empty()) {
            topOrder.add(result.pop());
        }
        return topOrder;

    }
}

public class longest {
    public static void main(String[] args) throws IOException {
        new Graph();
    }
}
