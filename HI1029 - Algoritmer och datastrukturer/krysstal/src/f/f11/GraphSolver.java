package f.f11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphSolver {
    private HashMap<String, Node> nodes;
    private String filepath;

    public GraphSolver(String filepath) {
        this.filepath = filepath;
        nodes = readFile(filepath);
    }

    public GraphSolver(String filepath, boolean print) {
        this.filepath = filepath;
        nodes = readFile(filepath);
        if (print) printNodes();
    }

    private void reset() {
        nodes = readFile(filepath);
    }

    public String findPath(String start, String finish) {
        HashMap<String, Node> availablePaths = new HashMap<>(nodes);
        Node next = nodes.get(start);
        next.setCost(0);
        while (!availablePaths.isEmpty()) {

            //Find a node with least cost
            double min = Double.POSITIVE_INFINITY;
            for (var node : availablePaths.entrySet()) {
                if (node.getValue().getCost() < min) {
                    min = node.getValue().getCost();
                    Node minNode = node.getValue();

                    if (!minNode.getName().equals(next.getName())) {
                        next = node.getValue();
                        break;
                    }
                }
            }

            availablePaths.remove(next.getName());

            //Find a node worth travelling to
            for (var neighbor : next.getConnections().entrySet()) {
                if (neighbor.getKey().getCost() > next.getCost() + neighbor.getValue()) {
                    neighbor.getKey().setCost(next.getCost() + neighbor.getValue());
                    neighbor.getKey().setVia(next);
                }
            }
        }

        //Start at the destination to trace route back
        var prev = nodes.get(finish);
        double total = prev.getCost();
        var visited = new ArrayList<Node>();

        //Untin start is found
        while (!prev.getName().equals(start)) {
            visited.add(prev);
            prev = prev.getVia();
        }

        var sb = new StringBuilder();
        sb.append("Shortest path between ").append(start).append(" and ").append(finish).append(" is ").append(String.format("%.02f", total)).append(" hours.\n");
        for (int i = 0; i < visited.size(); i++) {
            Node current = visited.get(visited.size() - 1 - i);
            sb.append(i + 1).append(":\t").append(current.getVia().getName()).append(" to ").append(current.getName()).append("\t");
            sb.append(String.format("%.02f", current.getConnections().get(current.getVia()))).append(" hours\t");
            sb.append("Total ").append(String.format("%.02f", current.getCost())).append(" hours\n");
        }
        reset();
        return sb.toString();
    }

    private HashMap<String, Node> readFile(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String buffer = "";
            HashMap<String, Node> nodes = new HashMap<>();

            boolean firstRow = true;
            while ((buffer = br.readLine()) != null) {
                if (firstRow) {
                    for (String name : buffer.split(" ")) {
                        nodes.put(name, new Node(name));
                    }
                    firstRow = false;
                    continue;
                }

                String[] strings = buffer.split(" ");
                nodes.get(strings[0]).addConnection(nodes.get(strings[1]), Double.parseDouble(strings[2]));
                nodes.get(strings[1]).addConnection(nodes.get(strings[0]), Double.parseDouble(strings[2]));
            }
            return nodes;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void printNodes() {
        System.out.println("Nodes: ");
        for (var n : nodes.entrySet()) {
            System.out.println("\n" + n.getKey() + " has connections:");
            for (var e : n.getValue().getConnections().entrySet()) {
                System.out.println(e.getKey() + " " + e.getValue() + " hours");
            }
        }
        System.out.println("---------------------------------");
    }

    private class Node {
        private final String name;
        private final HashMap<Node, Double> connections;
        private Node via;
        private double cost;

        public Node(String name) {
            this.name = name;
            via = null;
            cost = Double.POSITIVE_INFINITY;
            connections = new HashMap<>();
        }

        public Node getVia() {
            return via;
        }

        public void setVia(Node via) {
            this.via = via;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public void addConnection(Node neighbor, double cost) {
            connections.put(neighbor, cost);
        }

        public String getName() {
            return name;
        }

        public HashMap<Node, Double> getConnections() {
            return connections;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(cost == Double.POSITIVE_INFINITY ? "" : cost);
            return sb.toString();
        }
    }
}
