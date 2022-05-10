package lab.lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class U2_SingleLinkedList<E> implements Iterable<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public U2_SingleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new LLIterator(head);
    }

    public void printDataHeadTailSize() {
        printDataHeadTailSize("Blank test");
    }

    public void printDataHeadTailSize(String test) {
        System.out.println("\n\n");
        System.out.println(test);
        if (head == null) {
            System.out.println("Head: null");
        } else {
            System.out.println("Head: " + head.data);

            for (E string :
                    this) {
                System.out.println(string.toString());
            }
        }
        if (tail == null) {
            System.out.println("Tail: null");
        } else {
            System.out.println("Tail: " + tail.data);
        }
        System.out.println("Size: " + size);

    }

    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(item);
        } else {
            if (index == size) {
                addAfter(tail, item);
            } else {
                Node<E> node = getNode(index - 1);
                addAfter(node, item);
            }
        }
        size++;
    }

    public void addFirst(E item) {
        head = new Node<E>(item, head);
        if (size == 0) {
            tail = head;
        }
    }

    private Node<E> getNode(int index) {
        if (index == size - 1) {
            return tail;
        }
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    private void addAfter(Node<E> node, E item) {
        if (node.next == null) {
            node.next = new Node<E>(item, null);
            tail = node.next;
        } else {
            node.next = new Node<E>(item, node.next);
        }
    }

    public boolean add(E item) {
        add(size, item);
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == size - 1) {
            return tail.data;
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    public E remove(int index) {
        if (index == 0) {
            E returnValue = head.data;
            head = head.next;
            if (size == 1)
                tail = null;
            size--;
            return returnValue;
        }
        Node<E> beforeCurrent = getNode(index - 1);
        E returnValue;
        if (index == size - 1) {
            returnValue = tail.data;
            tail = beforeCurrent;
            tail.next = null;
        } else {
            returnValue = beforeCurrent.next.data;
            beforeCurrent.next = beforeCurrent.next.next;
        }

        size--;
        return returnValue;
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
            current = current.next;
            return returnValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
