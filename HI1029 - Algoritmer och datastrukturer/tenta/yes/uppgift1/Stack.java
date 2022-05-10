/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift1;

/**
 * @author bfelt
 */
public interface Stack<E> {
    E push(E obj);

    E peek();

    E pop();

    boolean empty();
}
