
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

    private List<List<Edge>> adjList;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    private static class Edge implements Comparable<Edge> {
        int vertex;
        long weight;
        int u, v; // kruskal

        Edge() {
            this.weight = Long.MAX_VALUE;
            this.vertex = -1;
        }

        Edge(int vertex, long weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        // kruskal contructor
        Edge(int u, int v, long weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge e) {
            if (this.weight > e.weight) {
                return 1;
            } else if (this.weight < e.weight) {
                return -1;
            } else if (this.vertex > e.vertex) {
                return 1;
            } else if (this.vertex < e.vertex) {
                return -1;
            } else
                return 0;
        }
    }

    Graph() {
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();
        adjList = new ArrayList<>();

        for (int i = 0; i <= numVertex; i++) {
            this.addVertex();
        }

        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();
            long weight = obj.nextLong();
            this.addEdge(u, v, weight);
        }
    }

    // prim
    // private void addEdge(int u, int v, long w) {
    // this.adjList.get(u).add(new Edge(v, w));
    // this.adjList.get(v).add(new Edge(u, w)); // undirected
    // }

    // kruskal
    private void addEdge(int u, int v, long w) {
        this.adjList.get(u).add(new Edge(u, v, w)); // u is root of u
        this.adjList.get(v).add(new Edge(v, u, w));
    }

    private void addVertex() {
        this.adjList.add(new LinkedList<>());
    }

    public int getVertex() {
        return this.numVertex;
    }

    public List<Edge> getAdjList(int v) {
        return this.adjList.get(v);
    }

    public void close() {
        obj.close();
    }

    public void Prim() {
        int total_w = 0;
        List<Edge> minEdge = new ArrayList<Edge>();

        for (int i = 0; i <= this.numVertex; i++) {
            minEdge.add(new Edge());
        }

        minEdge.get(1).weight = 0; // idx 1 er weight 0, source vertex
        PriorityQueue<Edge> q = new PriorityQueue<>();
        q.add(new Edge(1, 0));

        boolean selected[] = new boolean[numVertex + 1];
        Arrays.fill(selected, false);

        while (!q.isEmpty()) {
            Edge min = q.poll();
            int v = min.vertex;

            // Skip if vertex v has already been included in the MST
            if (selected[v])
                continue;

            selected[v] = true;
            total_w += min.weight;

            // Print edge if it's part of the MST and has a valid previous vertex
            if (minEdge.get(v).vertex != -1 && min.weight != 0) {
                obj.println(minEdge.get(v).vertex + " " + v);
            }

            for (Edge e : adjList.get(v)) {
                if (!selected[e.vertex] && e.weight < minEdge.get(e.vertex).weight) {
                    minEdge.get(e.vertex).weight = e.weight;
                    minEdge.get(e.vertex).vertex = v;
                    q.add(new Edge(e.vertex, e.weight));
                }
            }
        }
        obj.println(total_w);
    }

    public void Kruskal() {
        int[] root = new int[numVertex + 1];
        int[] size = new int[numVertex + 1];

        // dsu initialize
        for (int i = 0; i <= numVertex; i++) {
            root[i] = i;
            size[i] = 0;
        }

        // collect edges from adjList
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i <= numVertex; i++) {
            for (Edge e : adjList.get(i)) {
                // avoid duplicate, ensure u<v
                if (i < e.v) {
                    edges.add(e); // adding(u,v,w)
                }
            }
        }

        // sort
        Collections.sort(edges);

        long cost = 0;
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {

            int set_u = find(root, e.u);
            int set_v = find(root, e.v);

            // check cycles
            if (set_u != set_v) {
                cost += e.weight;
                mst.add(e);
                union(root, size, set_u, set_v);
            }
        }
        if (mst.size() == numVertex - 1)
            // print
            obj.println(cost);
        else
            obj.println("IMPOSSIBLE");
    }

    private int find(int[] root, int u) {
        if (root[u] != u)
            root[u] = find(root, root[u]); // path compression
        return root[u];
    }

    private void union(int[] root, int[] size, int u, int v) {
        int set_u = find(root, u);
        int set_v = find(root, v);

        if (size[set_u] < size[set_v])
            root[set_u] = set_v;
        else if (size[set_u] > size[set_v])
            root[set_v] = set_u;
        else {
            root[set_v] = set_u;
            size[set_u]++;
        }
    }
}

public class road_reparation implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new road_reparation(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.Kruskal();
        g.close();
    }
}
