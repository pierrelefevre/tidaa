package lab.lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class U3_SingleLinkedList<E> implements Iterable<E> {

    private Node<E> head;
    private int size;

    public U3_SingleLinkedList() {
        head = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new LLIterator(head);
    }

    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(item);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, item);
        }
    }

    public void addFirst(E item) {
        head = new Node<E>(item, head);
        size++;
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    private void addAfter(Node<E> node, E item) {
        node.next = new Node<E>(item, node.next);
        size++;
    }

    public boolean add(E item) {
        add(size, item);
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> p = head;
        if (p != null) {
            while (p.next != null) {
                sb.append(p.data.toString());
                sb.append(" => ");
                p = p.next;
            }
            sb.append(p.data.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* PRIVATE CLASSES */

    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private class LLIterator implements Iterator<E> {
        Node<E> current;
        Node<E> beforeCurrent;
        Node<E> beforeBeforeCurrent;

        public LLIterator(Node<E> start) {
            current = start;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            E returnValue = current.data;
            beforeBeforeCurrent = beforeCurrent;
            beforeCurrent = current;
            current = current.next;
            return returnValue;
        }

        @Override
        public void remove() {
            if (beforeCurrent == null || beforeCurrent == beforeBeforeCurrent) {
                throw new IllegalStateException();
            }

            if (beforeBeforeCurrent != null) {
                beforeBeforeCurrent.next = current;
            } else {
                head = current;
            }

            beforeCurrent = beforeBeforeCurrent;

            size--;
        }
    }

}