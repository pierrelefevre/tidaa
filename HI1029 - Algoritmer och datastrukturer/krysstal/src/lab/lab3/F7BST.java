package lab.lab3;

import java.util.ArrayList;

public class F7BST<E extends Comparable<E>> {

    private Node<E> root;
    private E deletedData;

    public F7BST() {
        root = null;
    }

    public void printTree() {
        for (String s : toStringRec(root, new ArrayList<String>(), 0)) {
            System.out.println(s);
        }
    }

    public int numberOfLeaves() {
        return numberOfLeaves(root);
    }

    public int numberOfLeaves(Node<E> node) {
        if (node.left == null && node.right == null)
            return 1;

        int left = 0;
        if (node.left != null)
            left = numberOfLeaves(node.left);

        int right = 0;
        if (node.right != null)
            right = numberOfLeaves(node.right);

        return left + right;
    }

    public int numberOfNodes() {
        return numberOfNodes(root);
    }

    public E getNextLarger(E search) {
        long start = System.nanoTime();

        if (search == null) return null;
        Node<E> current = root;
        Node<E> highest = null;

        while (current != null) {
            if (current.data.compareTo(search) <= 0) {
                current = current.right;
            } else if (current.data.compareTo(search) > 0) {
                highest = current;
                current = current.left;
            }
        }

        long stop = System.nanoTime();
        System.out.println("Time: " + ((stop - start)) + "ns");

        if (highest != null) return highest.data;
        return null;
    }

    public int numberOfNodes(Node<E> node) {
        if (node == null)
            return 0;

        return 1 + numberOfNodes(node.left) + numberOfNodes(node.right);
    }

    public int height() {
        return height(root);
    }

    public int height(Node<E> node) {
        return toStringRec(root, new ArrayList<String>(), 0).size() - 1;
    }

    public ArrayList<String> toStringRec(Node<E> node, ArrayList<String> strings, int depth) {

        if (strings.size() <= depth) {
            String s = node == null ? "n" : node.data.toString();
            strings.add(s + " ");
        } else {
            String s = strings.get(depth);
            s += node == null ? "n" : node.data.toString();
            strings.set(depth, s + " ");
        }

        if (node == null) {
            //Tree is finished
            return strings;
        }

        strings = toStringRec(node.left, strings, depth + 1);
        strings = toStringRec(node.right, strings, depth + 1);

        return strings;
    }

    private void inOrder(Node<E> node, StringBuilder sb) {
        if (node != null) {
            inOrder(node.left, sb);
            sb.append(": " + node.toString());
            inOrder(node.right, sb);
        }
    }

    private E findRec(E target, Node<E> node) {
        if (node == null)
            return null;
        if (target.compareTo(node.data) == 0)
            return node.data;
        if (target.compareTo(node.data) < 0)
            return findRec(target, node.left);
        return findRec(target, node.right);
    }

    public E find(E target) {
        Node<E> current = root;
        for (int diff = target.compareTo(current.data); diff != 0; diff = target.compareTo(current.data)) {
            if (diff < 0)
                current = current.left;
            else
                current = current.right;

            if (current == null)
                break;
        }

        return current == null ? null : current.data;
    }

    public E findRec(E target) {
        return findRec(target, root);
    }

    public E maxRec(Node<E> node) {
        return node.right == null ? node.data : maxRec(node.right);
    }

    public E maxRec() {
        if (root == null)
            return null;
        return maxRec(root);
    }

    public E maxIt() {
        var current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current == null ? null : current.data;
    }

    private boolean add(E data, Node<E> node) {
        if (data.compareTo(node.data) == 0)
            return false;
        else if (data.compareTo(node.data) < 0)
            if (node.left == null) {
                node.left = new Node<E>(data);
                return true;
            } else
                return add(data, node.left);
        else if (node.right == null) {
            node.right = new Node<E>(data);
            return true;
        } else
            return add(data, node.right);
    }

    public boolean add(E data) {
        if (root == null) {
            root = new Node<E>(data);
            return true;
        } else
            return add(data, root);
    }

    public E delete(E target) {
        root = delete(target, root);
        return deletedData;
    }

    private Node<E> delete(E target, Node<E> node) {
        if (node == null) {
            //target finns ej i trädet
            deletedData = null;
            return null;
        } else {
            if (target.compareTo(node.data) < 0) {
                //target finns i vänstra trädet
                node.left = delete(target, node.left); //om det finns
                return node;
            } else if (target.compareTo(node.data) > 0) {
                //target i högra trädet
                node.right = delete(target, node.right);
                return node;
            } else {
                //target finns i node!
                deletedData = node.data; //lagrar data att returnera
                //nu ska vi bygga om trädet
                if (node.left == null) //noden som ska bort saknar vänster träd
                    return node.right;
                else if (node.right == null)//noden som ska bort saknar högerträd
                    return node.left;
                else {//noden vi ska ta bort har två barn
                    Node<E> nodeToMove = node.right, parentNodeToMove = node;
                    if (nodeToMove.left == null) {
                        //högra barnet har inget vänsterbarn
                        nodeToMove.left = node.left;
                        return nodeToMove;
                    }
                    //högra barnet har vänsterbarn
                    while (nodeToMove.left != null) {
                        parentNodeToMove = nodeToMove;
                        nodeToMove = nodeToMove.left;
                    }
                    parentNodeToMove.left = nodeToMove.right;
                    node.data = nodeToMove.data;
                    return node;
                }
            }
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    private static class Node<E> {
        private E data;
        private Node<E> left, right;

        public Node(E data) {
            this.data = data;
            left = right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
