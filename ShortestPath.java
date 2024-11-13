
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

    private List<List<Edge>> adjList;
    private int numVertex;
    private int numEdge;

    FastIO obj;

    private static class Edge implements Comparable<Edge> {
        int vertex;
        int weight;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
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
            int weight = obj.nextInt();
            this.addEdge(u, v, weight);
        }
    }

    private void addEdge(int u, int v, int w) {
        this.adjList.get(u).add(new Edge(v, w));
        // this.adjList.get(v).add(new Edge(u, w));
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

    public void dijkstra(int s) {
        int[] distance = new int[numVertex + 1];
        int[] parent = new int[numVertex + 1];
        boolean[] visited = new boolean[numVertex + 1];

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        Arrays.fill(visited, false);

        distance[s] = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(s, 0));

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int u = edge.vertex;

            if (visited[u])
                continue;
            visited[u] = true;

            for (Edge e : adjList.get(u)) {
                if (!visited[e.vertex] && (distance[u] + e.weight < distance[e.vertex])) {
                    distance[e.vertex] = distance[u] + e.weight;
                    parent[e.vertex] = u;
                    queue.add(new Edge(e.vertex, distance[e.vertex]));
                }
            }
        }

        // print path
        for (int i = 1; i <= numVertex; i++) {

            if (distance[i] != Integer.MAX_VALUE) {
                obj.println("Cost: " + distance[i]);

                Stack<Integer> path = new Stack<>();
                int cur = i;

                while (cur != -1) {
                    path.push(cur);
                    cur = parent[cur];
                }

                obj.print("Path: ");
                while (!path.isEmpty()) {
                    obj.print(path.pop());
                    if (!path.isEmpty()) {
                        obj.print(" -> ");
                    }
                }
                obj.println();
            }
        }
    }

}

public class ShortestPath implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new ShortestPath(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        g.dijkstra(1);
        g.close();
    }
}
