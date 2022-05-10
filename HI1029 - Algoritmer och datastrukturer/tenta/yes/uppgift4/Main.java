package uppgift4;

import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class Main {

    //i denna implementation h�r ett edge-object till en nod (den vars nods lista den ligger i).
    //Den representerar d� b�gen fr�n denna nod till connectedNode och har vikten weight
    //Se nedan.
    private static class Edge {
        private int connectedNode, weight;

        public Edge(int cN, int w) {
            connectedNode = cN;
            weight = w;
        }
    }

    public static void main(String[] args) {
        int X = Integer.MAX_VALUE;
        LinkedList<Edge>[] adjacencyList = new LinkedList[8];

        //h�r �r grafen fr�n provet h�rdkodad (A=0, B=1, C=2, D=3, E=4, F=5, G=6, H=7)
        //din kod ska naturligtvis klara godtycklig graf representerad p� samma s�tt
        adjacencyList[0] = new LinkedList<>();//lista med A's b�gar
        adjacencyList[0].add(new Edge(1, 2));//b�gen fr�n A till B har vikten 2
        adjacencyList[0].add(new Edge(5, 1));//b�gen fr�n A till F har vikten 1
        adjacencyList[1] = new LinkedList<>();//lista med B's b�gar
        adjacencyList[1].add(new Edge(0, 2));//b�gen fr�n B till A har vikten 2
        adjacencyList[1].add(new Edge(2, 2));//osv
        adjacencyList[1].add(new Edge(3, 2));
        adjacencyList[1].add(new Edge(4, 4));
        adjacencyList[2] = new LinkedList<>();
        adjacencyList[2].add(new Edge(1, 2));
        adjacencyList[2].add(new Edge(4, 3));
        adjacencyList[2].add(new Edge(7, 1));
        adjacencyList[3] = new LinkedList<>();
        adjacencyList[3].add(new Edge(1, 2));
        adjacencyList[3].add(new Edge(4, 3));
        adjacencyList[3].add(new Edge(5, 1));
        adjacencyList[4] = new LinkedList<>();
        adjacencyList[4].add(new Edge(1, 4));
        adjacencyList[4].add(new Edge(2, 3));
        adjacencyList[4].add(new Edge(3, 3));
        adjacencyList[4].add(new Edge(6, 7));
        adjacencyList[5] = new LinkedList<>();
        adjacencyList[5].add(new Edge(0, 1));
        adjacencyList[5].add(new Edge(3, 1));
        adjacencyList[5].add(new Edge(6, 5));
        adjacencyList[6] = new LinkedList<>();
        adjacencyList[6].add(new Edge(4, 7));
        adjacencyList[6].add(new Edge(5, 5));
        adjacencyList[6].add(new Edge(7, 6));
        adjacencyList[7] = new LinkedList<>();
        adjacencyList[7].add(new Edge(2, 1));
        adjacencyList[7].add(new Edge(6, 6));
        System.out.println(prim(adjacencyList));

    }

    public static int prim(List<Edge>[] edges) {
        BitSet bs = new BitSet(edges.length);
        int[] weights = new int[edges.length];
        Arrays.fill(weights, Integer.MAX_VALUE);
        weights[0] = 0;
        int[] connected = new int[edges.length];

        for (int i = 1; i < edges.length; i++) {
            for (int j = 1; j < edges.length; j++) {
                if (!bs.get(j)) {
                    for (Edge e : edges[j]) {
                        if (weights[e.connectedNode] > e.weight) {
                            weights[e.connectedNode] = e.weight;
                            connected[e.connectedNode] = j;
                            bs.set(j);
                        }
                    }
                }
            }
        }
        System.out.println("Wght " + Arrays.toString(weights));
        System.out.println("Via: " + Arrays.toString(connected));
        int total = 0;
        for (int i : weights) total += i;
        return total;
    }
}