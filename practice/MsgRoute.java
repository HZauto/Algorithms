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
    // private List<List<Integer>> revAdjList;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() throws IOException { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();
        adjList = new ArrayList<>();
        // revAdjList = new ArrayList<>();

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

    public void BFS(int source) { // source = 1
        boolean visited[] = new boolean[numVertex + 1];
        int prev[] = new int[numVertex + 1];
        int distance[] = new int[numVertex + 1];

        for (int i = 1; i < numVertex + 1; i++) {
            visited[i] = false;
            prev[i] = -1;
            distance[i] = Integer.MAX_VALUE;
        }

        visited[source] = true;
        distance[source] = 1;

        Queue<Integer> queue = new LinkedList<>(); // ll type er queu
        queue.add(source); // add root

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            for (int adj : getAdjList(vertex)) {
                if (!visited[adj]) {
                    visited[adj] = true;
                    distance[adj] = distance[vertex] + 1;
                    prev[adj] = vertex;
                    queue.add(adj);
                }
            }
        }
        if (prev[numVertex] == -1) {
            obj.println("IMPOSSIBLE");
        } else {
            obj.println(distance[numVertex]);

            Stack<Integer> path = new Stack<>();
            int current = numVertex; // destination vertex
            while (current != -1) {
                path.push(current);
                current = prev[current];
            }
            while (!path.isEmpty()) {
                obj.print(path.pop() + " ");

            }
            obj.println();
        }
    }

    public void close() {
        obj.close();
    }
}

public class MsgRoute {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph();
        g.BFS(1);
        g.close();
    }
}
