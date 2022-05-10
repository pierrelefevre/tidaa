package uppgift1;

public class SingleLinkedList<E> {

    private Node<E> head;

    public SingleLinkedList() {
        head = null;
    }

    private void addFirst(E item) {
        head = new Node<E>(item, head);
    }

    private void addAfter(Node<E> node, E item) {
        node.next = new Node<E>(item, node.next);
    }

    private Node<E> getNode(int index) {
        if (index <= 0)
            return head;

        int curr = 0;
        Node<E> node = head;
        while (curr < index && node.next != null) {
            node = node.next;
            curr++;
        }
        return node;
    }

    public E get(int index) {
        if (index <= 0)
            return head.data;

        int curr = 0;
        Node<E> node = head;
        while (curr < index && node.next != null) {
            node = node.next;
            curr++;
        }
        if (node != null)
            return node.data;
        return null;
    }

    public void add(int index, E item) {
        if (index <= 0 || head == null) {
            addFirst(item);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, item);
        }
    }

    public int size() {
        return size(head);
    }

    private int size(Node<E> curr) {
        return curr == null ? 0 : 1 + size(curr.next);
    }

    private static class Node<E> {

        private E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

    }

}
