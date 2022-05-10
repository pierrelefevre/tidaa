/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift3;

/**
 *
 * @author bfelt
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

//specialversion utan size
public class SingleLinkedList<E> implements Iterable<E> {

    private static class Node<E> {

        private E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

    }

    private Node<E> head;

    public SingleLinkedList() {
        head = null;
    }
    public int size(){
        return size(head);
    }

    private int size(Node<E> n) {
        if(n==null)
            return 0;
        return 1+size(n.next);
    }
    
    public void addFirst(E item) {
        head = new Node<>(item, head);
    }

    private void addAfter(Node<E> node, E item) {
        node.next = new Node<>(item, node.next);
    }

    private E removeFirst() {
        Node<E> temp = head;
        if (head != null) {
            head = head.next;
        }
        if (temp != null) {
            return temp.data;
        } else {
            return null;
        }
    }

    private E removeAfter(Node<E> node) {
        Node<E> temp = node.next;
        if (temp != null) {
            node.next = temp.next;
            return temp.data;
        } else {
            return null;
        }
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;
        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    public E set(int index, E newValue) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        E result = node.data;
        node.data = newValue;
        return result;
    }

    public void add(int index, E item) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(item);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, item);
        }
    }

    public boolean add(E item) {
        add(size(), item);
        return true;
    }

    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            return removeFirst();
        } else {
            Node<E> node = getNode(index - 1);
            return removeAfter(node);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> p = head;
        if (p != null) {
            while (p.next != null) {
                sb.append(p.data.toString());
                sb.append(" ==> ");
                p = p.next;
            }
            sb.append(p.data.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean remove(E item) {
        if (head == null) {
            return false;
        }
        Node<E> current = head;
        if (item.equals(current.data)) {
            removeFirst();
            return true;
        }
        while (current.next != null) {
            if (item.equals(current.next.data)) {
                removeAfter(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private class Itr implements Iterator<E> {

        Node<E> bbCurrent, beforeCurrent, current;

        public Itr(Node<E> start) {
            current = start;
            bbCurrent = null;
            beforeCurrent = null;
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
            bbCurrent = beforeCurrent;
            beforeCurrent = current;
            current = current.next;
            return returnValue;
        }

        @Override
        public void remove() {
            if (beforeCurrent == null || beforeCurrent == bbCurrent) {
                throw new IllegalStateException();
            }
            if (bbCurrent == null) {//ta bort första noden
                head = current;
                beforeCurrent = null;
            } else {
                bbCurrent.next = current;
                beforeCurrent = bbCurrent;
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(head);
    }

}
