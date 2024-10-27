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
    private List<List<Integer>> revAdjList;
    private List<List<Integer>> components;
    private List<List<Integer>> condensed;
    private Map<String, Integer> drinks;
    private boolean cycle;
    private int counter;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    Graph() { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        int test = obj.nextInt();
        for (int j = 1; j <= test; ++j) {
            cycle = false;
            counter = 0;
            numEdge = obj.nextInt();
            adjList = new ArrayList<>();
            drinks = new HashMap<>();

            this.adjList.add(new LinkedList<>());
            for (int i = 0; i < numEdge; i++) {
                String u = obj.next();
                String v = obj.next();

                if (!drinks.containsKey(u)) {
                    this.counter += 1;
                    this.adjList.add(new LinkedList<>());
                    drinks.put(u, counter);
                }
                if (!drinks.containsKey(v)) {
                    this.counter += 1;
                    this.adjList.add(new LinkedList<>());
                    drinks.put(v, counter);
                }

                this.addEdge(drinks.get(u), drinks.get(v)); // 0 based indexing
            }

            int[] visited = new int[this.counter + 1];
            Arrays.fill(visited, 0);

            for (int i = 1; i <= this.counter; ++i) {
                DFS(i, visited, adjList, new LinkedList<Integer>());
            }

            if (cycle) {
                obj.println("Case " + j + ": " + "No");
            } else {
                obj.println("Case " + j + ": " + "Yes");
            }
        }
        // numVertex = obj.nextInt();

    }

    public void solve() {
        int[] visited = new int[this.counter + 1];
        Arrays.fill(visited, 0);

        for (int i = 1; i <= this.counter; ++i) {
            DFS(i, visited, adjList, new LinkedList<Integer>());
        }

        if (cycle) {
            obj.println("No");
        } else {
            obj.println("Yes");
        }
    }

    private void addEdge(int u, int v) {
        this.adjList.get(u).add(v);
        // this.adjList.get(v).add(u);
        // this.revAdjList.get(v).add(u);
    }

    private void addVertex() {
        this.adjList.add(new LinkedList<>());
        this.revAdjList.add(new LinkedList<>());
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
        distance[source] = 0;

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
        for (int i = 1; i < numVertex + 1; i++) {
            if (i == source)
                continue;
            System.out.println("shortest path for vertex " + (i) + " : " + distance[i]);
        }
        // path
        Stack<Integer> path = new Stack<>();
        int current = 5; // destination vertex
        while (prev[current] != -1) {
            path.push(current);
            current = prev[current];
        }
        while (!path.isEmpty()) {
            obj.print(path.pop() + " ");
        }
    }

    public void DFS(int vertex, int[] visited, List<List<Integer>> adjacencyList, List<Integer> output) {
        visited[vertex] = 1;
        for (int u : adjacencyList.get(vertex)) {
            if (visited[u] == 0) {
                DFS(u, visited, adjacencyList, output);
            } else if (visited[u] == 1) {
                cycle = true;
            }
        }
        visited[vertex] = 2;
        output.add(vertex);
    }

    public void close() {
        obj.close();
    }
}

public class Drunk implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Drunk(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.close();
    }
}
