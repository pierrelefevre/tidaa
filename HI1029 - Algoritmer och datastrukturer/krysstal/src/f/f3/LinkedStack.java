package f.f3;

import java.util.EmptyStackException;

public class LinkedStack<E> implements StackInt<E> {

    private Node<E> top;

    public LinkedStack() {
        top = null;
    }

    @Override
    public E push(E obj) {
        top = new Node<E>(obj, top);
        return obj;
    }

    @Override
    public E peek() {
        if (empty()) {
            throw new EmptyStackException();
        } else {
            return top.data;
        }
    }

    @Override
    public E pop() {
        if (empty()) {
            throw new EmptyStackException();
        } else {
            E res = top.data;
            top = top.next;
            return res;
        }
    }

    @Override
    public boolean empty() {
        return top == null;
    }

    public int size() {
        Node<E> curr = top;
        int cnt = 0;
        while (curr.next != null) {
            curr = curr.next;
            cnt++;
        }
        return cnt + 1;
    }

    public E peek(int n) {
        Node<E> curr = top;
        int cnt = 0;
        while (curr.next != null) {
            if (cnt == n)
                return curr.data;
            curr = curr.next;
            cnt++;
        }
        throw new IndexOutOfBoundsException("Could not find requested index: " + n + " in stack.");
    }

    public E flush() {
        Node<E> curr = top;
        while (curr.next != null) {
            curr = curr.next;
        }
        top = null;
        return curr.data;
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
