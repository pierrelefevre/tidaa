/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift1;

/**
 * @author bfelt
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Stack<String> s = new ArrayStack<>();
        for (int i = 0; i < 10; i++)
            s.push("e" + i);
        while (!s.peek().equals("e5"))
            System.out.println(s.pop());
        System.out.println("del2");
        while (!s.empty())
            System.out.println(s.pop());
        System.out.println(s.pop());//ska kasta ett exception
    }

}
