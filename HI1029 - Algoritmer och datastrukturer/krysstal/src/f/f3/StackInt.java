package f.f3;

public interface StackInt<E> {
    E push(E obj);

    E peek();

    E pop();

    boolean empty();
}