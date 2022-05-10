package f.f4;


public class LinkedQ<E> {
    private Node<E> head;
    private int size;
    private Node<E> tail;

    public LinkedQ() {
        size = 0;
    }

    public void offer(E data) {
        if (size == 0) {
            tail = new Node<E>(data, null);
            head = tail;
        } else {
            tail.next = new Node<E>(data, null);
            tail = tail.next;
        }
        size++;
    }

    public E poll() {
        if (size == 0) {
            return null;
        }
        E returnValue = head.data;
        head = head.next;
        size--;
        return returnValue;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> next = head;
        while (next != null) {
            sb.append(next.data);
            next = next.next;
        }
        return sb.toString();
    }

    public class Node<E> {
        public E data;
        public Node next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
