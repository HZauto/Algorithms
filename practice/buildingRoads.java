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
    private List<List<Integer>> adjList;
    private List<List<Integer>> components;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() throws IOException { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();
        adjList = new ArrayList<>();
        components = new ArrayList<>();

        for (int i = 0; i <= numVertex; i++) {
            this.addVertex();
        }

        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();
            this.addEdge(u, v); // 0 based indexing
        }
    }

    private void addEdge(int u, int v) {
        this.adjList.get(u).add(v);
        this.adjList.get(v).add(u);
        // this.revAdjList.get(v).add(u);
    }

    private void addVertex() {
        this.adjList.add(new LinkedList<>());
        // this.revAdjList.add(new LinkedList<>());
    }

    public int getVertex() {
        return this.numVertex;
    }

    public List<Integer> getAdjList(int v) {
        return this.adjList.get(v);
    }

    public void DFS(int vertex, boolean[] visited, List<List<Integer>> adjacencyList, List<Integer> output) {
        visited[vertex] = true;
        for (int u : adjacencyList.get(vertex)) {
            if (!visited[u]) {
                DFS(u, visited, adjacencyList, output);
            }
        }
        output.add(vertex);
    }

    public void solve() {
        boolean[] visited = new boolean[numVertex + 1];
        for (int i = 1; i <= numVertex; i++) {
            if (!visited[i]) {
                LinkedList<Integer> c = new LinkedList<>();
                DFS(i, visited, adjList, c);
                components.add(c);
            }
        }
        obj.println(components.size() - 1);

        for (int i = 0; i < components.size() - 1; i++) {
            obj.println(components.get(i).get(0) + " " + components.get(i + 1).get(0));
        }
    }

    public void close() {
        obj.close();
    }
}

public class buildingRoads {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        g.solve();
        g.close();
    }
}
