package f.f4;

public class Deq<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public Deq() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public E pollFirst() {
        if (empty())
            return null;
        E val = head.data;
        head = head.next;
        if (head != null)
            head.before = null;
        size--;
        return val;
    }

    public E pollLast() {
        if (empty())
            return null;
        E val = tail.data;
        tail = tail.before;
        if (tail != null)
            tail.next = null;
        size--;
        return val;
    }

    public boolean offerFirst(E element) {
        if (empty()) {
            tail = new Node<E>(element, null, null);
            head = tail;
        } else {
            head.before = new Node<E>(element, head, null);
            head = head.before;
        }
        size++;
        return true;
    }

    public boolean offerLast(E element) {
        if (empty()) {
            tail = new Node<E>(element, null, null);
            head = tail;
        } else {
            tail.next = new Node<E>(element, null, tail);
            tail = tail.next;
        }
        size++;
        return true;
    }

    public boolean empty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> next = head;
        while (next != null) {
            sb.append(next.data + ", ");
            next = next.next;
        }
        sb.append(" SIZE : " + size);
        return sb.toString();
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> before;

        public Node(E data, Node<E> next, Node<E> before) {
            this.data = data;
            this.next = next;
            this.before = before;
        }
    }
}
