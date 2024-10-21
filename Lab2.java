import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.*;

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
                this.addEdge(u, v); // fix the index of linked list to 0-based
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

    }

    // Get the number of vertices
    public int getVertex() {
        return this.numVertex;
    }

    public LinkedList<Integer> getAdjList(int vertex) {
        return this.adjacencyList.get(vertex);
    }

    public void displayGraph() {
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print((i) + "-> ");
            for (int vertex : adjacencyList.get(i)) { // along with adjacent vertex
                System.out.print((vertex) + " ");
            }
            System.out.println();
        }
    }

    public void DFS(int vertex) {
        boolean color[] = new boolean[numVertex];
        // int[] prev = new int[numVertex];
        int[] discover = new int[numVertex];
        int[] finish = new int[numVertex];

        for (int i = 0; i < numVertex; i++) {
            color[i] = false; // white
            discover[i] = Integer.MAX_VALUE;
            finish[i] = Integer.MAX_VALUE;
        }

        // global time tracker
        time = 0;
        System.out.println("DFS Traversal starting from vertex " + vertex + ": ");
        DFS_visit(vertex, color, discover, finish);
    }

    private void DFS_visit(int cur, boolean[] color, int[] d, int[] f) {
        System.out.print(cur + " ");
        color[cur] = true; // grey
        time++;
        d[cur] = time;

        for (int adjacent : getAdjList(cur)) {
            if (!color[adjacent]) {
                DFS_visit(adjacent, color, d, f);
            }
        }
        time++;
        f[cur] = time;

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

        boolean[] visited = new boolean[numVertex];

        for (int i = 0; i < numVertex; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < numVertex; i++) {
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

public class Lab2 {
    public static void main(String[] args) throws IOException {

        Graph graph = new Graph("input.txt");

        System.out.println("Graph adjacency list:");
        graph.displayGraph();

        System.out.println("\nPerforming DFS starting from vertex 5:");
        graph.DFS(5);

        System.out.println("\nPerforming Topological Sort:");
        List<Integer> topoOrder = graph.topologicalSort();
        System.out.println("Topological Sort order: " + topoOrder);
    }
}