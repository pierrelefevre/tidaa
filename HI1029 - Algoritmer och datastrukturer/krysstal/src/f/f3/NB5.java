package f.f3;

public class NB5 {
    public static void main(String[] args) {
        ArrayStack<String> s = new ArrayStack<>();
        s.push("Fizz");
        s.push("Buzz");
        System.out.println(s.push("FizzBuzz"));
        System.out.println(s);
        System.out.println(s.peek());
        System.out.println(s.pop());
        System.out.println(s);
        s.push("FooBar");
        System.out.println(s);
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s);
        System.out.println(s.empty());
        System.out.println(s);
    }
}
