package f.f6;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NB17 {
    public static void main(String[] args) {
        System.out.println("NB17");

        SingleLinkedList<String> list = new SingleLinkedList<>();

        for (int i = 0; i < 4; i++) {
            list.add("e" + i);
        }
        System.out.println(list.size() + " " + list);

        list.remove(3);
        System.out.println(list.size() + " " + list);


        list.add(0, "first");
        list.add("last");
        System.out.println(list.size() + " " + list);


        System.out.println(list.getNode(0));
        System.out.println(list.getNode(1));
        System.out.println(list.getNode(2));
        System.out.println(list.getNode(3));
        System.out.println(list.getNode(4));

    }

    private static class SingleLinkedList<E> implements Iterable<E> {
        private Node<E> head;
        private Node<E> tail;
        private int size;

        public SingleLinkedList() {
            head = null;
            tail = null;
            size = 0;
        }

        public Iterator<E> iterator() {
            return new LLIterator(head);
        }

        private Node<E> getNode(int index) {
            if (index == size - 1) {
                return tail;
            }
            return getNodeRec(head, index, 0);
        }

        private Node<E> getNodeRec(Node<E> node, int dest, int curr) {
            if (dest == curr)
                return node;

            if (node.next == null)
                return node;

            return getNodeRec(node.next, dest, curr + 1);
        }

        private int size() {
            return sizeRec(head);
        }

        private int sizeRec(Node<E> node) {
            if (node.next == null)
                return 1;

            return 1 + sizeRec(node.next);
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
            return getNode(index).data;
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

        private static class Node<E> {
            private E data;
            private Node<E> next;

            public Node(E data, Node<E> next) {
                this.data = data;
                this.next = next;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "data=" + data +
                        ", next=" + next +
                        '}';
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

}
