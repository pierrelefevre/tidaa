import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.BitSet;
import java.util.HashMap;

@SuppressWarnings("unchecked")

public class MaxFlow {
    Kattio io;
    int v;
    int s;
    int t;
    int e;
    int maxFlow;

    HashMap<Integer, Neighbor> graph[];

    int paths[];

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

    void readFlowGraph() {
        // Den första raden består av ett heltal som anger antalet hörn i V.
        // Den andra raden består av två heltal s och t som anger vilka hörn som är
        // källa respektive utlopp.
        // Den tredje raden består av ett tal som anger |E|, det vill säga antalet
        // kanter i grafen.
        // De följande |E| raderna består var och en av tre heltal som svarar mot en
        // kant.

        v = io.getInt();
        s = io.getInt();
        t = io.getInt();
        e = io.getInt();
        graph = new HashMap[v + 1];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new HashMap<Integer, Neighbor>();
        }

        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();

            graph[a].put(b, new Neighbor(f));
            if (!graph[b].containsKey(a)) {
                graph[b].put(a, new Neighbor(0));
            }
        }
    }

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

        // If no paths were found
        return false;
    }

    void solve() {
        // Givet en flödesgraf G = (V,E) finn ett maximalt flöde. Lös flödesproblemet
        // med Edmonds-Karps algoritm, det vill säga Ford-Fulkersons algoritm där den
        // kortaste stigen hittas med breddenförstsökning.

        for (int i = 1; i <= v; i++) {
            for (var neighbor : graph[i].values()) {
                neighbor.restFlow = neighbor.initialFlow;
            }
        }

        paths = new int[v + 1];

        maxFlow = 0;

        while (bfs()) {

            int a, b;
            int r = Integer.MAX_VALUE;
            for (b = t; b != s; b = paths[b]) {
                a = paths[b];
                r = Math.min(r, graph[a].get(b).restFlow);
            }

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

    void writeFlowGraph() {
        // Den första raden består av ett heltal som anger antalet hörn i V.
        // Den andra raden består av tre heltal s,t, samt flödet från s till t.
        // Den tredje raden består av ett heltal som anger antalet kanter med positivt
        // flöde.
        // Därefter skrivs en rad för varje sådan kant. Kanten beskrivs av tre tal på
        // liknande sätt som i indata, men i stället för kapacitet har vi nu flöde.

        var paths = new ArrayList<int[]>();

        // Find paths in matrix
        for (int i = 1; i <= v; i++) {
            for (var neighbor : graph[i].entrySet()) {
                if (neighbor.getValue().flow > 0) {
                    paths.add(new int[] { i, neighbor.getKey(), neighbor.getValue().flow });

                }
            }
        }

        io.println(v);
        io.println(s + " " + t + " " + maxFlow);
        io.println(paths.size());

        for (var path : paths) {
            int a = path[0];
            int b = path[1];
            int f = path[2];

            io.println(a + " " + b + " " + f);
        }

    }

    MaxFlow() {

        io = new Kattio(System.in, System.out);

        readFlowGraph();
        solve();
        writeFlowGraph();

        io.close();
    }

    public static void main(String args[]) {
        new MaxFlow();
    }
}
