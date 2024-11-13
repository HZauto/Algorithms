import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Graph {
    private ArrayList<LinkedList<Integer>> adjacencyList;
    private int numVertex;
    private int numEdge;
    private int time;

    Graph(String fileName) {
        adjacencyList = new ArrayList<LinkedList<Integer>>();

        try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            this.numVertex = scan.nextInt();
            this.numEdge = scan.nextInt();

            for (int i = 0; i < numVertex; i++) {
                this.addVertex();
            }

            for (int i = 0; i < numEdge; i++) {
                int u = scan.nextInt();
                int v = scan.nextInt();
                this.addEdge(u - 1, v - 1); // fix the index of linked list to 0-based
            }

            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addVertex() {
        this.adjacencyList.add(new LinkedList<Integer>());
    }

    private void addEdge(int u, int v) {
        this.adjacencyList.get(u).add(v);
        this.adjacencyList.get(v).add(u);
    }

    // Get the number of vertices
    public int getVertex() {
        return this.numVertex;
    }

    // Get the adjacency list of a given vertex~index
    public LinkedList<Integer> getAdjList(int vertex) {
        return this.adjacencyList.get(vertex);
    }

    public void printGraph() {
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print((i + 1) + ": "); // print vertex number 1-based
            for (int vertex : adjacencyList.get(i)) { // along with adjacent vertex
                System.out.print((vertex + 1) + " ");
            }
            System.out.println();
        }
    }

    public void BFS(int source) { // source = 1
        boolean visited[] = new boolean[numVertex];
        int prev[] = new int[numVertex];
        int distance[] = new int[numVertex];

        // initialize all vertices as unvisited, no prev, infinite distance
        for (int i = 0; i < numVertex; i++) {
            visited[i] = false; // not visited
            prev[i] = -1; // no parent
            distance[i] = Integer.MAX_VALUE;
        }
        visited[source - 1] = true; // visited the source index
        distance[source - 1] = 0; // distance of source is 0

        // QUEUE
        Queue<Integer> queue = new LinkedList<>(); // queue te ll add thakbe
        queue.add(source - 1); // add source vertex at q for 0-index :1->0, 2->1..

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
        for (int i = 0; i < numVertex; i++) {
            if (i == source - 1)
                continue;
            System.out.println("shortest path for vertex " + (i + 1) + " : " + distance[i]);
            
            // int curVertex = i;
            // while (curVertex != -1) {
            // System.out.print((curVertex + 1) + " ");
            // curVertex = prev[curVertex];
            // }
            // System.out.println();
             
            printPath(i, prev);
            System.out.println();
        }
    }

    private void printPath(int curVertex, int[] prev) {
        if (prev[curVertex] == -1) {
            System.out.print((curVertex + 1) + " "); // base
            return;
        }
        printPath(prev[curVertex], prev);
        System.out.print((curVertex + 1) + " ");
    }

    public void DFS() {
        String color[] = new String[numVertex];
        int[] prev = new int[numVertex];
        int[] discover = new int[numVertex];
        int[] finish = new int[numVertex];

        for (int i = 0; i < numVertex; i++) {
            color[i] = "white";
            prev[i] = -1;
            discover[i] = Integer.MAX_VALUE;
            finish[i] = Integer.MAX_VALUE;
        }

        // global time tracker
        time = 0;

        for (int u = 0; u < numVertex; u++) {
            if (color[u] == "white")
                DFS_visit(u, color, prev, discover, finish);
        }

    }

    private void DFS_visit(int cur, String[] color, int[] prev, int[] d, int[] f) {
        color[cur] = "grey";
        time++;
        d[cur] = time;

        for (int adjacent : getAdjList(cur)) {
            if (color[cur] == "white") {
                prev[adjacent] = cur;
                DFS_visit(adjacent, color, prev, d, f);
            }
        }
        color[cur] = "black";
        time++;
        f[cur] = time;
    }

}

public class Lab1 {
    public static void main(String[] args) {
        Graph graph = new Graph("input.txt");
        graph.printGraph();
        graph.BFS(1);
        graph.DFS();
    }
}
