package f.f8;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleLinkedList<E> implements Iterable<E> {

    private Node<E> head;

    public SingleLinkedList() {
        head = null;
    }

    public Iterator<E> iterator() {
        return new LLIterator(head);
    }


    public void add(E item) {
        head = new Node<E>(item, head);
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
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
        }
    }

}