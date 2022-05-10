import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

@SuppressWarnings("unchecked")

public class BipMax {
    Kattio io;

    // Variables for bip match
    int x, y, e;
    ArrayList<int[]> edges;
    ArrayList<int[]> solutionEdges;

    // Variables for max flow
    int maxFlow;
    int v, s, t, eFlow;
    HashMap<Integer, Neighbor> graph[];
    int paths[];

    // Represents a node's connection with a neighbor for adjacency list
    public class Neighbor {
        public Neighbor(int i) {
            initialFlow = i;
            flow = 0;
            restFlow = 0;
        }

        int flow;
        int restFlow;
        int initialFlow;
    }

    // Parse input of bip graph from kattio
    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        x = io.getInt();
        y = io.getInt();
        e = io.getInt();

        edges = new ArrayList<>();
        ArrayList<int[]> tmp = new ArrayList<>();

        HashSet<Integer> setA = new HashSet<>();
        HashSet<Integer> setB = new HashSet<>();

        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            tmp.add(new int[] { a + 1, b + 1, 1 });
            // kanter[i][0] = a + 1;
            // kanter[i][1] = b + 1;
            // kanter[i][2] = 1;

            setA.add(a + 1);
            setB.add(b + 1);
        }

        // adjust e
        e += 3;

        // Add root node connection to all A nodes in bip graph
        for (int node : setA) {
            edges.add(new int[] { 1, node, 1 });
        }

        // Add bipartite graph nodes
        for (var item : tmp) {
            edges.add(item);
        }

        // Add connection to sink node from all B nodes in bip graph
        for (int node : setB) {
            edges.add(new int[] { node, x + y + 2, 1 });
        }

        System.err.println("Läste klart och konverterade bipartita grafen");
    }

    // Convert flow graph as list to adjacency matrix
    void writeFlowGraph() {
        // int v = 23, e = 0, s = 1, t = 2;

        v = x + y + 2;

        s = 1;
        t = v;
        // t = e;
        eFlow = edges.size();

        graph = new HashMap[v + 1];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new HashMap<Integer, Neighbor>();
        }

        // Create adjacency list
        for (int i = 0; i < eFlow; ++i) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            int f = edges.get(i)[2];

            graph[a].put(b, new Neighbor(f));
            if (!graph[b].containsKey(a)) {
                graph[b].put(a, new Neighbor(0));
            }
        }

        // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
        io.flush();
    }

    // Breadth-first searach to find if there is a path to the sink
    boolean bfs() {

        // Save visisted nodes
        var visited = new BitSet(v + 1);

        // Create queue and start at node s
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(s);
        visited.set(s, true);
        paths[s] = -1;

        while (queue.size() != 0) {
            int a = queue.poll();

            for (var neighbor : graph[a].entrySet()) {
                int b = neighbor.getKey();
                // If not visited and there is a path
                if (!visited.get(b) && neighbor.getValue().restFlow > 0) {
                    // If we reach the sink
                    if (b == t) {
                        paths[b] = a;
                        return true;
                    }

                    // If it is not the sink, continue
                    queue.add(b);
                    paths[b] = a;
                    visited.set(b, true);
                }
            }
        }

        // If no paths to sink were found
        return false;
    }

    void solve() {
        // Givet en flödesgraf G = (V,E) finn ett maximalt flöde. Lös flödesproblemet
        // med Edmonds-Karps algoritm, det vill säga Ford-Fulkersons algoritm där den
        // kortaste stigen hittas med breddenförstsökning.

        // Set the restflow of every edge in the graph to their capacity.
        for (int i = 1; i <= v; i++) {
            for (var neighbor : graph[i].values()) {
                neighbor.restFlow = neighbor.initialFlow;
            }
        }

        paths = new int[v + 1];

        maxFlow = 0;

        // Find a path from source to sink and set the maximal flow from source to sink
        // to r
        while (bfs()) {

            int a, b;
            int r = Integer.MAX_VALUE;
            for (b = t; b != s; b = paths[b]) {
                a = paths[b];
                r = Math.min(r, graph[a].get(b).restFlow);
            }

            // Calculate the restflow graph
            for (b = t; b != s; b = paths[b]) {
                if (paths[b] != 0) {
                    a = paths[b];

                    graph[a].get(b).flow += r;
                    graph[b].get(a).flow = -graph[a].get(b).flow;

                    graph[a].get(b).restFlow = graph[a].get(b).initialFlow - graph[a].get(b).flow;
                    graph[b].get(a).restFlow = graph[b].get(a).initialFlow - graph[b].get(a).flow;
                }
            }

            maxFlow += r;
        }
    }

    // Read the flow graph from solve
    void readMaxFlowSolution() {
        // Den första raden består av ett heltal som anger antalet hörn i V.
        // Den andra raden består av tre heltal s,t, samt flödet från s till t.
        // Den tredje raden består av ett heltal som anger antalet kanter med positivt
        // flöde.
        // Därefter skrivs en rad för varje sådan kant. Kanten beskrivs av tre tal på
        // liknande sätt som i indata, men i stället för kapacitet har vi nu flöde.

        var paths = new ArrayList<int[]>();

        // Find paths
        for (int i = 1; i <= v; i++) {
            for (var neighbor : graph[i].entrySet()) {
                if (neighbor.getValue().flow > 0) {
                    paths.add(new int[] { i, neighbor.getKey(), neighbor.getValue().flow });

                }
            }
        }

        edges = new ArrayList<>();

        // Create the solved flow graph, a is every node to the left. b is every node to
        // the right, f is the flow for each edge
        for (var path : paths) {
            int a = path[0];
            int b = path[1];
            int f = path[2];
            edges.add(new int[] { a, b, f });
        }
    }

    // Write the solved flow graph but as a maximal bipartite match
    void writeBipMatchSolution() {

        solutionEdges = new ArrayList<>();
        int maxMatch = 0;

        // Get the solve flow graph
        for (int i = 0; i < edges.size(); ++i) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            int f = edges.get(i)[2];

            // Create a bipartite graph with only edges from the maximal bipartite match
            if (a != 1 && b != v && f == 1) {
                solutionEdges.add(new int[] { a - 1, b - 1 });
                maxMatch += 1;
            }
        }

        // Skriv ut antal hörn och storleken på matchningen
        io.println(x + " " + y);
        io.println(maxMatch);

        for (int i = 0; i < maxMatch; ++i) {
            // int a = 5, b = 2323;
            int a = solutionEdges.get(i)[0];
            int b = solutionEdges.get(i)[1];
            // Kant mellan a och b ingår i vår matchningslösning
            io.println(a + " " + b);
        }
    }

    BipMax() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();

        solve();

        readMaxFlowSolution();

        writeBipMatchSolution();

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipMax();
    }
}
