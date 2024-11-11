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
    private List<List<Edge>> revAdjList;
    private int numVertex;
    private int numEdge;

    FastIO obj;
    private static class Edge implements Comparable<Edge>{
        int vertex;
        int weight;

        Edge(){
            this.weight = Integer.MAX_VALUE;
            this.vertex = -1;
        }

        Edge(int vertex, int weight){
            this.vertex = vertex;
            this.weight = weight;
        }

        public int compareTo(Edge e){
            if(this.weight>e.weight){
                return 1;
            }
            else if(this.weight<e.weight){
                return -1;
            }
            else if(this.vertex> e.vertex){
                return 1;
            }
            else if(this.vertex < e.vertex){
                return -1;
            }
            else return 0;
        }

    }

    Graph() { // (String filename)
        // File file = new File(filename);
        obj = new FastIO();
        numVertex = obj.nextInt();
        numEdge = obj.nextInt();
        adjList = new ArrayList<>();
        revAdjList = new ArrayList<>();

        for (int i = 0; i <= numVertex; i++) {
            this.addVertex();
        }

        for (int i = 0; i < numEdge; i++) {
            int u = obj.nextInt();
            int v = obj.nextInt();
            int weight = obj.nextInt();
            this.addEdge(u, v, weight); // 0 based indexing
        }
    }

    private void addEdge(int u, int v, int weight) {
        this.adjList.get(u).add(new Edge(v, weight));
        this.revAdjList.get(v).add(new Edge(u, weight));
    }

    private void addVertex() {
        this.adjList.add(new LinkedList<>());
        this.revAdjList.add(new LinkedList<>());
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

    public void Prim(){
        int total_w = 0;
        List<Edge> minEdge = new ArrayList<Edge>();

        for(int i=0; i<= this.numVertex; i++){
            minEdge.add(new Edge());
        }

        minEdge.get(1).weight = 0;
        PriorityQueue<Edge> q = new PriorityQueue<>();
        q.add(new Edge(1,0));
        
        boolean[] selected = new boolean[numVertex+1];
        Arrays.fill(selected,false);

        for(int i=0; i<=numVertex; i++){
            if(q.isEmpty()){
                obj.print("No MST");
            }

            int v = q.peek().vertex;
            selected[v] = true;
            total_w = q.peek().weight;
            q.remove();

            for(Edge e: adjList.get(v)){
                if(!selected[e.vertex] && e.weight < minEdge.get(e.vertex).weight){
                    Edge r = new Edge(e.vertex, minEdge.get(e.vertex).weight);
                    q.remove(r);

                    minEdge.get(e.vertex).weight = e.weight;
                    minEdge.get(e.vertex).vertex = v;
                    q.add(new Edge(e.vertex, e.weight));
                }
            }
        }

    }
}

public class weighted implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new weighted(), "whatever", 1 << 26).start();
    }

    public void run() {
        Graph g = new Graph();
        
        g.close();
    }
}