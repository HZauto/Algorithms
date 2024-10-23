import java.util.*;
import java.io.*;

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
    private ArrayList<LinkedList<Integer>> adjList;
    private ArrayList<LinkedList<Integer>> revAdjList;
    private ArrayList<LinkedList<Integer>> components;
    private ArrayList<LinkedList<Integer>> condensed;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() throws IOException { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        adjList = new ArrayList<>();
        revAdjList = new ArrayList<>();
        components = new ArrayList<>();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();

        for (int i = 0; i < numVertex; i++) {
            this.addVertex();
        }

        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();
            this.addEdge(u - 1, v - 1); // 0 based indexing
        }
        obj.close();
    }

    private void addVertex() {
        this.adjList.add(new LinkedList<Integer>());
        this.revAdjList.add(new LinkedList<>());
    }

    private void addEdge(int u, int v) {
        this.adjList.get(u).add(v);
        this.revAdjList.get(v).add(u);
    }

    public int getVertex() {
        return this.numVertex;
    }

    public LinkedList<Integer> getAdjList(int v) {
        return this.adjList.get(v);
    }

    public LinkedList<Integer> getRevAdjList(int v) {
        return revAdjList.get(v);
    }

    public void displayGraph() {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.print((i + 1) + "-> ");
            for (int vertex : adjList.get(i)) { // along with adjacent vertex
                System.out.print((vertex + 1) + " ");
            }
            System.out.println();
        }
    }

    public void DFS(int vertex, boolean[] visited, Stack<Integer> output) {
        visited[vertex] = true;
        for (int u : getAdjList(vertex)) {
            if (!visited[u]) {
                DFS(u, visited, output);
            }
        }
        output.push(vertex);
    }

    public void revDFS(int vertex, boolean[] visited, LinkedList<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int u : getRevAdjList(vertex)) {
            if (!visited[u]) {
                revDFS(u, visited, component);
            }
        }
    }

    public void scc() {
        boolean[] visited = new boolean[numVertex];
        Stack<Integer> order = new Stack<>();

        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                DFS(i, visited, order);
            }
        }

        // Arrays.fill(visited, false);

        while (!order.isEmpty()) {
            int vertex = order.pop();
            if (!visited[vertex]) {
                LinkedList<Integer> c = new LinkedList<>();
                revDFS(vertex, visited, c);
                components.add(c);
                System.out.println("Found component: " + c);
            }
        }
        System.out.println("Strongly Connected Components:");
        for (LinkedList<Integer> c : components) {
            for (int v : c) {
                System.out.print((v + 1) + " "); // Output using 1-based index
            }
            System.out.println();
        }
    }

    public void condensedGraph() {

    }
}

public class Lab3 {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        g.displayGraph();
        g.scc();
    }
}
